package application.mechanik;

import application.infrastructure.core.annotations.AutoWired;
import application.vehiclesmodules.Vehicle;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class MechanicService implements Fixer{
    @AutoWired
    private Fixer fixer;
    @AutoWired
    ParserBreakingFromFile parser = new ParserBreakingFromFile();
    private static String[] details = {
            "Фильтр", "Втулка", "Вал", "Ось", "Свеча", "Масло", "ГРМ", "ШРУС"};

    public MechanicService() {
    }

    public Fixer getFixer() {
        return fixer;
    }

    public void setFixer(Fixer fixer) {
        this.fixer = fixer;
    }

    public ParserBreakingFromFile getParser() {
        return parser;
    }

    public void setParser(ParserBreakingFromFile parser) {
        this.parser = parser;
    }

    public static String[] getDetails() {
        return details;
    }

    public static void setDetails(String[] details) {
        MechanicService.details = details;
    }

    public Map<String, Integer> detectBreaking(Vehicle vehicle) {
        Map<String, Integer> result = new HashMap<>();
        int sum = 0;
        for (String s : details) {
            int randomNumber = (int) (Math.random() * 2);
            result.put(s, randomNumber);
            sum += randomNumber;
            if (randomNumber > 0) vehicle.setBroken(true);
        }
        vehicle.setSumOfBrokenParts(sum);
        try {
            FileWriter writer = new FileWriter("src/main/java/application.csvfiles/orders.csv", true);
            StringBuilder details = new StringBuilder();
            details.append(vehicle.getId());
            for (String key : result.keySet()) {
                details.append(",").append(key).append(",").append(result.get(key));
            }
            details.append("\n");
            writer.write(details.toString());
            writer.flush();
            vehicle.setBrokenParts(details.substring(2, details.length()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
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
            parser.clearBreackings(result, vehicle);
        }
        if (counter == 0) System.out.println("Vehicle is healthy");
    }

    public boolean isBroken(Vehicle vehicle) {
        return parser.checkVehicle(vehicle);
    }
}
