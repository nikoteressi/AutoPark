import exceptions.NoVehicleException;
import garage.Garage;
import mechanik.MechanicService;
import vehiclesmodules.Vehicle;
import vehiclecreation.VehicleCollection;
import washing.WashingQueue;

import java.io.FileNotFoundException;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) throws FileNotFoundException, NoVehicleException {

        try {
           VehicleCollection vehicleCollection = new VehicleCollection("src/main/java/csvfiles/types.csv", "src/main/java/csvfiles/vehicles.csv", "src/main/java/csvfiles/rents.csv");
            vehicleCollection.display();
            System.out.println();
            mechanik.MechanicService service = new mechanik.MechanicService();
            for (Vehicle v : vehicleCollection) {
                service.detectBreaking(v);
//                System.out.println("vehiclescollection.Vehicle " + v.getModel());
//                System.out.println("Number of broken parts - " + v.getSumOfBrokenParts());
            }
            vehicleCollection.getVehicle(3).rentVehicle(6);
            for (Vehicle v : vehicleCollection) {
                service.repair(v);
            }
            for (Vehicle v : vehicleCollection) {
                if (!v.isBroken()) {
                    System.out.println(v);
                }
            }
            System.out.println();
            System.out.println("Max broken vehicle - " + vehicleCollection.getMaxBrokenVehicle());
            System.out.println("Number of broken parts - " + vehicleCollection.getMaxBrokenVehicle().getSumOfBrokenParts());
            vehicleCollection.getVehicle(3).rentVehicle(6);


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    static class Util {
        public static boolean equalsVehicles(Vehicle firstVehicle, Vehicle secondVehicle) {
            if (firstVehicle.getVehicleType().equals(secondVehicle.getVehicleType())) {
                if (firstVehicle.getModel().equals(secondVehicle.getModel())) {
                    return firstVehicle.getEngine().equals(secondVehicle.getEngine());
                }
            }
            return false;
        }

        public static void printAll(Vehicle[] vehicles) {
            for (Vehicle v : vehicles) {
                System.out.println(v.toString());
            }
        }

        public static Vehicle[] sort(Vehicle[] vehicles) {
            Vehicle temp;
            int count;
            do {
                count = 0;
                for (int i = 0; i < vehicles.length - 1; i++) {
                    if (vehicles[i].compareTo(vehicles[i + 1]) > 0) {
                        temp = vehicles[i];
                        vehicles[i] = vehicles[i + 1];
                        vehicles[i + 1] = temp;
                        count++;
                    }
                }
            } while (count != 0);
            return vehicles;
        }

        public static Vehicle getMin(Vehicle[] vehicles) {
            Vehicle min = vehicles[0];
            for (Vehicle v : vehicles) {
                if (min.getMileage() > v.getMileage()) min = v;
            }
            return min;
        }

        public static Vehicle getMax(Vehicle[] vehicles) {
            Vehicle max = vehicles[0];
            for (Vehicle v : vehicles) {
                if (max.getMileage() < v.getMileage()) max = v;
            }
            return max;
        }

    }
}
