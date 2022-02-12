package application.infrastructure.orm.service;

import application.entity.Types;
import application.infrastructure.core.Context;
import application.infrastructure.core.annotations.AutoWired;
import application.infrastructure.core.annotations.InitMethod;
import application.infrastructure.orm.ConnectionFactory;
import application.infrastructure.orm.annotations.Column;
import application.infrastructure.orm.annotations.ID;
import application.infrastructure.orm.annotations.Table;
import application.infrastructure.orm.enums.SqlFieldType;
import application.service.TypesService;
import lombok.SneakyThrows;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import java.util.stream.Collectors;

public class PostgreDataBaseService {
    @AutoWired
    private ConnectionFactory connectionFactory;
    private Map<String, String> classToSql;
    private Map<String, String> insertPatternByClass;
    @AutoWired
    private Context context;
    private static final String SEQ_NAME = "id_seq";
    private static final String CHECK_SEQ_SQL_PATTERN =
            "SELECT EXISTS (\n" +
                    "   SELECT FROM information_schema.sequences \n" +
                    "   WHERE  sequence_schema = 'public'\n" +
                    "   AND    sequence_name   = '%s'\n" +
                    ");";
    private static final String CREATE_ID_SEQ_PATTERN =
            "CREATE SEQUENCE %S\n" +
                    "    INCREMENT 1\n" +
                    "    START 1;";
    private static final String CHECK_TABLE_SQL_PATTERN =
            "SELECT EXISTS (\n" +
                    "    SELECT FROM information_schema.tables \n" +
                    "    WHERE  table_schema = 'public'\n" +
                    "    AND    table_name   = '%s'\n" +
                    ");";
    private static final String CREATE_TABLE_SQL_PATTERN =
            "CREATE TABLE IF NOT EXISTS %s (\n" +
                    "    %s integer PRIMARY KEY DEFAULT nextval('%s')" +
                    "    %S\n);";
    private static final String INSERT_SQL_PATTERN =
            "INSERT INTO %s(%s)\n" +
                    "    VALUES (%s)\n" +
                    "    ON CONFLICT DO NOTHING\n" +
                    "    RETURNING %S ;";
    private Map<String, String> insertByClassPattern;

    public PostgreDataBaseService() {
    }

    public <T> Optional<T> get(Long id, Class<T> clazz) throws Exception {
        if (!clazz.isAnnotationPresent(Table.class)) throw new Exception("This clas is not a Table.");
        Field[] fields = clazz.getDeclaredFields();
        Field idField = null;
        for (Field f : fields) {
            if (f.isAnnotationPresent(ID.class)) idField = f;
            break;
        }
        assert idField != null;
        String sql = "SELECT * FROM " + clazz.getSimpleName() + " WHERE " +
                idField.getName() + " = " + id;
        Statement statement = connectionFactory.getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        resultSet.next();
        T object = makeObject(resultSet, clazz);
        statement.close();
        resultSet.close();
        return (Optional<T>) object;
    }

    public <T> List<T> getAll(Class<T> clazz) throws Exception {
        if (!clazz.isAnnotationPresent(Table.class)) throw new Exception("This clas is not a Table.");
        List<T> tables = new ArrayList<>();
        String sql = "SELECT * FROM " + clazz.getSimpleName();
        Statement statement = connectionFactory.getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        while (resultSet.next()) {
            tables.add(makeObject(resultSet, clazz));
        }

        statement.close();
        resultSet.close();
        return tables;
    }

    @SneakyThrows
    private <T> T makeObject(ResultSet resultSet, Class<T> clazz) {
        T object = clazz.newInstance();
        Field[] fields = clazz.getDeclaredFields();
        for (Field f : fields) {
            f.setAccessible(true);
            if (f.isAnnotationPresent(ID.class)) f.set(object, resultSet.getLong(f.getName()));
            if (f.isAnnotationPresent(Column.class)) {
                Class<?> type = f.getType();
                if (type.getSimpleName().equals("String")) {
                    f.set(object, resultSet.getObject(f.getName()));
                }
                if (type.getSimpleName().equals("Long")) {
                    f.set(object, resultSet.getLong(f.getName()));
                }
                if (type.getSimpleName().equals("Integer")) {
                    f.set(object, resultSet.getObject(f.getName()));
                }
                if (type.getSimpleName().equals("Double")) {
                    f.set(object, resultSet.getDouble(f.getName()));
                }
                if (type.getSimpleName().equals("Date")) {
                    f.set(object, resultSet.getObject(f.getName()));
                }
            }
        }
        return object;
    }

    public Long save(Object object) {
        Object[] values = fillObjectValues(object);
        StringBuilder sb = new StringBuilder();
        int counter = 0;
        for (Object o : values) {
            if (counter > 0) sb.append(",");
            sb.append(o);
            counter++;
        }
        String sql = String.format(insertByClassPattern.get(object.getClass().getSimpleName()), values);
        try {
            Statement statement = connectionFactory.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            String idFieldName = "";
            for (Field f : object.getClass().getDeclaredFields()) {
                f.setAccessible(true);
                if (f.isAnnotationPresent(ID.class)) {
                    idFieldName = f.getName();
                }
            }
            long id = 0L;
            while (resultSet.next())
                id = resultSet.getLong(idFieldName);
            Field idField = object.getClass().getDeclaredField(idFieldName);
            idField.setAccessible(true);
            idField.set(object, id);
            statement.close();
            resultSet.close();
        } catch (SQLException | NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    @SneakyThrows
    private Object[] fillObjectValues(Object object) {
        Field[] fields = object.getClass().getDeclaredFields();
        List<Object> values = new ArrayList<>();
        for (Field f : fields) {
            f.setAccessible(true);
            if (f.isAnnotationPresent(Column.class)) values.add(f.get(object));
        }
        return values.toArray();
    }

    @InitMethod
    public void init() {
        classToSql = Arrays.stream(SqlFieldType.values()).collect(Collectors.toMap(f -> f.getType().getName(), SqlFieldType::getSqlType));
        insertPatternByClass = Arrays.stream(SqlFieldType.values()).collect(Collectors.toMap(e -> e.getType().getName(), SqlFieldType::getInsertPattern));
        insertByClassPattern = new HashMap<>();
        if (!checkIdSeq()) createIdSeq();
        Set<Class<?>> entities = getEntities();
        fillInsertByPatternClass(entities);
    }

    private void fillInsertByPatternClass(Set<Class<?>> entities) {
        for (Class<?> c : entities) {
            validateEntity(c);
            if (!isTableExists(c.getName())) createTable(c);
            if (c.isAnnotationPresent(Table.class))
                insertByClassPattern.put(c.getSimpleName(), getStringToInsert(c));
        }
    }

    private Set<Class<?>> getEntities() {
        return new HashSet<>(context.getConfig().getScanner().getReflections().getTypesAnnotatedWith(Table.class));
    }

    @SneakyThrows
    private void validateEntity(Class<?> entity) {
        Field[] fields = entity.getDeclaredFields();
        List<String> fieldNames = new ArrayList<>();
        for (Field f : fields) {
            if (f.isAnnotationPresent(ID.class) && !(f.getType().getSimpleName().equals("Long")))
                throw new Exception("No 'ID' field in class.");
            if (f.isAnnotationPresent(Column.class) && fieldNames.contains(f.getClass().getSimpleName()))
                throw new Exception("Class has Repeated columns: " + f.getName());
            fieldNames.add(f.getName());
        }
    }

    private boolean checkIdSeq() {
        String sql = String.format(CHECK_SEQ_SQL_PATTERN, SEQ_NAME);
        boolean isExist = false;
        try {
            Statement statement = connectionFactory.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next())
                isExist = resultSet.getBoolean("exists");
            resultSet.close();
            statement.close();
            return isExist;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private void createIdSeq() {
        String sql = String.format(CREATE_ID_SEQ_PATTERN, SEQ_NAME);
        try {
            Statement statement = connectionFactory.getConnection().createStatement();
            statement.execute(sql);
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private String getStringToInsert(Class<?> clazz) {
        String idFieldName = "";
        String tableName = clazz.getSimpleName();
        StringBuilder insertFields = new StringBuilder();
        StringBuilder values = new StringBuilder();
        Field[] fields = clazz.getDeclaredFields();
        int count = 0;
        for (Field f : fields) {
            if (f.isAnnotationPresent(ID.class)) {
                idFieldName = f.getName();
            }
            if (f.isAnnotationPresent(Column.class)) {
                if (count > 0) {
                    insertFields.append(",");
                    values.append(",");
                }
                insertFields.append(f.getName());
                values.append(insertPatternByClass.get(f.getType().getName()));
                count++;
            }
        }
        return String.format(INSERT_SQL_PATTERN, tableName, insertFields, values, idFieldName);
    }

    private void createTable(Class<?> type) {
        String tableName = type.getSimpleName();
        StringBuilder fieldsSB = new StringBuilder();
        String idField = "";
        Field[] fields = type.getDeclaredFields();
        for (Field f : fields) {
            if (f.isAnnotationPresent(ID.class)) {
                idField = f.getName();
            }
            if (f.isAnnotationPresent(Column.class)) {
                fieldsSB.append(",").append(f.getName());
                fieldsSB.append(" ").append(classToSql.get(f.getType().getName()));
                if (f.getAnnotation(Column.class).nullable()) fieldsSB.append(" ").append("NOT NULL");
                if (f.getAnnotation(Column.class).unique()) fieldsSB.append(" ").append("UNIQUE");
            }
        }
        String sql = String.format(CREATE_TABLE_SQL_PATTERN, tableName, idField, SEQ_NAME, fieldsSB);
        try {
            Statement statement = connectionFactory.getConnection().createStatement();
            statement.execute(sql);
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private boolean isTableExists(String tableName) {
        boolean isExist = false;
        String sql = String.format(CHECK_TABLE_SQL_PATTERN, tableName);
        try {
            Statement statement = connectionFactory.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next())
                isExist = resultSet.getBoolean("exists");
            statement.close();
            resultSet.close();
            return isExist;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
