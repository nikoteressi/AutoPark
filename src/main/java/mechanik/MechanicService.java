package mechanik;

import vehiclesmodules.Vehicle;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class MechanicService {
    private static String[] details = {
            "Фильтр", "Втулка", "Вал", "Ось", "Свеча", "Масло", "ГРМ", "ШРУС"
    };

    public static Map<String, Integer> detectBreaking(Vehicle vehicle) {
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
            FileWriter fileWriter = new FileWriter("src/main/java/csvfiles/orders.csv", true);
            StringBuilder details = new StringBuilder();
            details.append(vehicle.getId());
            for (String key : result.keySet()) {
                details.append(",").append(key).append(",").append(result.get(key));
            }
            details.append("\n");
            fileWriter.write(details.toString());
            fileWriter.flush();
            vehicle.setBrokenParts(details.substring(2, details.length()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void repair(Vehicle vehicle) {
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
            System.out.println("vehiclescollection.Vehicle '" + vehicle.getModel() + "' was fixed");
            vehicle.setBroken(false);
            try {
                File writer = new File("src/main/java/csvfiles/orders.csv");
                FileWriter file = new FileWriter(writer, false);
                Scanner scanner = new Scanner(writer);
                while (scanner.hasNextLine()) {
                    String str = scanner.nextLine();
                        if (vehicle.getId() != Integer.parseInt(str.substring(0))) {
                            result.add(str + "\n");
                        }
                }
             for (String s : result) {
                 file.write(s);
             }
             file.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (counter == 0) System.out.println("vehiclescollection.Vehicle is healthy");
    }

    public boolean isBroken(Vehicle vehicle) {
        return vehicle.isBroken();
    }
}
