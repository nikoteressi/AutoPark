package application.mechanik;

import application.entity.Orders;
import application.infrastructure.core.annotations.AutoWired;
import application.infrastructure.orm.EntityManager;
import application.vehiclesmodules.Vehicle;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class MechanicService implements Fixer {
    @AutoWired
    ParserBreakingsInterface parser;
    @AutoWired
    private EntityManager entityManager;
    private static String[] details = {
            "Фильтр", "Втулка", "Вал", "Ось", "Свеча", "Масло", "ГРМ", "ШРУС"};

    public MechanicService() {
    }

    public ParserBreakingsInterface getParser() {
        return parser;
    }

    public void setParser(ParserBreakingsInterface parser) {
        this.parser = parser;
    }

    public static String[] getDetails() {
        return details;
    }

    public static void setDetails(String[] details) {
        MechanicService.details = details;
    }

    public boolean detectBreaking(Vehicle vehicle) {
        boolean flag = false;
        File file = new File("src/main/java/application/csvfiles/orders.csv");
        String csvString;
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNext()) {
                csvString = scanner.nextLine();
                String[] tokens = csvString.split(",");
                Orders orders = Orders.builder().vehicle_id(Integer.parseInt(tokens[0])).oil(Integer.parseInt(tokens[2])).filter(Integer.parseInt(tokens[6])).shaft(Integer.parseInt(tokens[8])).sleeve(Integer.parseInt(tokens[10])).
                        joint(Integer.parseInt(tokens[12])).axis(Integer.parseInt(tokens[14])).glow_plug(Integer.parseInt(tokens[16])).grm(Integer.parseInt(tokens[4])).build();
                flag = true;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return flag;
    }

    public void repair(Vehicle vehicle) {
        List<String> result = new ArrayList<>();
        String[] details = vehicle.getBrokenParts().split(",");
        int counter = 0;
        for (int i = 1; i < details.length; i += 2) {
            if (Integer.parseInt(details[i]) != 0) {
                details[i] = "0";
                counter++;
            }
        }
        if (counter > 0) {
            System.out.println("Vehicle '" + vehicle.getModel() + "' was fixed");
            vehicle.setBroken(false);
            parser.clearBreakdowns();
        }
        if (counter == 0) System.out.println("Vehicle is healthy");
    }

    public boolean isBroken(Vehicle vehicle) {
        return parser.checkVehicle(vehicle);
    }
}
