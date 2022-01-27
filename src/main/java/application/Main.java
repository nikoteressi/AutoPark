package application;

import application.infrastructure.configurators.impl.AutowiredObjectConfigurator;
import application.infrastructure.configurators.impl.ObjectConfigurator;
import application.infrastructure.core.impl.ApplicationContext;
import application.mechanik.Workroom;
import application.vehiclecreation.VehicleCollection;
import application.vehiclesmodules.Vehicle;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        Map<Class<?>, Class<?>> implementations = new HashMap<>();
        implementations.put(ObjectConfigurator.class, AutowiredObjectConfigurator.class);

        ApplicationContext context = new ApplicationContext("java.application", implementations);
        VehicleCollection vehicles = context.getObject(VehicleCollection.class);
        Workroom workroom = context.getObject(Workroom.class);
        workroom.checkAllVehicle(vehicles.getVehicles());
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
