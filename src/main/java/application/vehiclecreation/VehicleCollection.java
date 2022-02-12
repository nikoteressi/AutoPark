package application.vehiclecreation;

import application.infrastructure.core.annotations.AutoWired;
import application.infrastructure.core.annotations.InitMethod;
import application.vehiclesmodules.Rent;
import application.vehiclesmodules.Vehicle;
import application.vehiclesmodules.VehicleType;

import java.util.*;

public class VehicleCollection implements Iterable<Vehicle> {
    private static List<VehicleType> vehicleTypes = new ArrayList<>();
    private static List<Rent> rents = new ArrayList<>();
    private List<Vehicle> vehicles = new ArrayList<>();
    @AutoWired
    private ParserVehicleInterface parser;

    public VehicleCollection() {
    }

    public static List<VehicleType> getVehicleTypes() {
        return vehicleTypes;
    }

    public static void setVehicleTypes(List<VehicleType> vehicleTypes) {
        VehicleCollection.vehicleTypes = vehicleTypes;
    }

    public static List<Rent> getRents() {
        return rents;
    }

    public static void setRents(List<Rent> rents) {
        VehicleCollection.rents = rents;
    }

    public List<Vehicle> getVehicles() {
        return vehicles;
    }

    public void setVehicles(List<Vehicle> vehicles) {
        this.vehicles = vehicles;
    }

    public ParserVehicleInterface getParser() {
        return parser;
    }

    public void setParser(ParserVehicleFromFile parser) {
        this.parser = parser;
    }

    @InitMethod
    public void init() {
       vehicleTypes = parser.fillVehicleTypes();
       rents = parser.fillRents();
       vehicles = parser.fillVehicles();
    }

    public void insert(int index, Vehicle v) {
        if (index <= vehicles.size() - 1) vehicles.add(index, v);
        vehicles.add(v);
    }

    public void delete(int index) {
        if (vehicles.size() - index >= 0) {
            vehicles.remove(index);
        } else {
            System.out.println("No vehicle number " + index);
        }
    }

    public double sumTotalProfit() {
        double total = 0;
        for (Vehicle v : vehicles) {
            total += v.getTotalIncome() - v.getCalcTaxPerMonth();
        }
        return total;
    }

    public void display() {
        Formatter formatter = new Formatter();
        System.out.println(formatter.format("%s     %s     %s     %s     %s     %s     %s     %s     %s     %s     %s", "ID*", "TYPE*", "MODEL NAME*", "NUMBER*", "WEIGHT (kg)*",
                "YEAR*", "MILEAGE*", "COLOR*", "INCOME*", "TAX*", "PROFIT*"));
        for (Vehicle v : vehicles) {
            System.out.format("%d      %s    %s    %s    %f    %d    %d    %s    %f    %f    %f \n",
                    v.getId(), v.getVehicleType().getTypeName(), v.getModel(), v.getLicensePlate(),
                    v.getWeight(), v.getYearOfProduction(), v.getMileage(), v.getColor(), v.getTotalIncome(),
                    v.getCalcTaxPerMonth(), v.getTotalIncome() - v.getCalcTaxPerMonth());
        }
        System.out.println("Total                                                                  " + sumTotalProfit());

    }

    public void sort(Comparator<Vehicle> comparator) {
        vehicles.sort(comparator);
    }

    public Vehicle getMaxBrokenVehicle() {
        int max = 0;
        Vehicle vehicle = null;
        for (Vehicle v : vehicles) {
            if (max < v.getSumOfBrokenParts()) {
                max = v.getSumOfBrokenParts();
                vehicle = v;
            }
        }
        return vehicle;
    }

    public Vehicle getVehicle(int index) {
        if (index > vehicles.size()) {
            System.out.println("No such vehicle");
        }
        return vehicles.get(index);
    }


    @Override
    public Iterator<Vehicle> iterator() {
        return new Iterator<Vehicle>() {
            private int currentIndex = 0;

            @Override
            public boolean hasNext() {
                return currentIndex < vehicles.size() && vehicles.get(currentIndex) != null;
            }

            @Override
            public Vehicle next() {
                return vehicles.get(currentIndex++);
            }
        };
    }
}
