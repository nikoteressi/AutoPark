package application.mechanik;

import application.infrastructure.core.annotations.InitMethod;
import application.vehiclesmodules.Vehicle;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class ParserBreakingFromFile {
    private final String ORDERS_PATH = "src/main/java/application/csvfiles/orders.csv";

    public void writeBreakings(Map<String, Integer> breakings, Vehicle vehicle) {
        try {
            FileWriter writer = new FileWriter("src/main/java/application/csvfiles/orders.csv", true);
            StringBuilder details = new StringBuilder();
            details.append(vehicle.getId());
            for (String key : breakings.keySet()) {
                details.append(",").append(key).append(",").append(breakings.get(key));
            }
            details.append("\n");
            writer.write(details.toString());
            writer.flush();
            vehicle.setBrokenParts(details.substring(2, details.length()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void clearBreackings(List<String> breakings, Vehicle vehicle) {
        try {
            File file = new File(ORDERS_PATH);
            FileWriter writer = new FileWriter(file, false);
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String str = scanner.nextLine();
                String[] tokens = str.split(",");
                if (vehicle.getId() != Integer.parseInt(tokens[0])) {
                    breakings.add(str + "\n");
                }
            }
            for (String s : breakings) {
                writer.write(s);
            }
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public boolean checkVehicle(Vehicle vehicle) {
        int id = vehicle.getId();
        try {
            File file = new File(ORDERS_PATH);
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String str = scanner.nextLine();
                String[] tokens = str.split(",");
                if (id == Integer.parseInt(tokens[0])) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @InitMethod
    public void init() {

    }
}
