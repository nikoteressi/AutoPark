package application.infrastructure.orm.impl;

import application.infrastructure.core.annotations.InitMethod;
import application.infrastructure.core.annotations.Proprety;
import application.infrastructure.orm.ConnectionFactory;
import lombok.SneakyThrows;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactoryImpl implements ConnectionFactory {
    @Proprety("url")
    private String url;
    @Proprety("username")
    private String username;
    @Proprety("password")
    private String password;
    private Connection connection;

    public ConnectionFactoryImpl() {
    }

    @SneakyThrows
    @InitMethod
    public void initConnection() {
        Class.forName("org.postgresql.Driver");
        connection = DriverManager.getConnection(url, username, password);
    }

    @Override
    public Connection getConnection() {
        return connection;
    }
}
