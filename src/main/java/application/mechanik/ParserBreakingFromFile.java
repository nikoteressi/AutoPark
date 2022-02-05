package application.mechanik;

import application.infrastructure.core.annotations.InitMethod;
import application.vehiclesmodules.Vehicle;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class ParserBreakingFromFile implements ParserBreakingsInterface{
    private final String ORDERS_PATH = "src/main/java/application.csvfiles/orders.csv";

    public void writeBreakings() {

    }

    public void clearBreakdowns() {

    }
    public boolean checkVehicle(Vehicle vehicle) {
        int id = vehicle.getId();
        try {
            File file = new File("src/main/java/application/csvfiles/orders.csv");
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
