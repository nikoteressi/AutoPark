package vehiclecreation;

import vehiclesmodules.Rent;
import vehiclesmodules.Vehicle;
import vehiclesmodules.VehicleType;

import java.io.FileNotFoundException;
import java.util.Comparator;
import java.util.Formatter;
import java.util.Iterator;
import java.util.List;

public class VehicleCollection implements Iterable<Vehicle> {
    private static List<VehicleType> vehicleTypes;
    private static List<Rent> rents;
    private List<Vehicle> vehicles;


    public VehicleCollection(String typesPath, String vehiclesPath, String rentPath) throws FileNotFoundException {
        rents = new RentsLoader().load(rentPath);
        vehicleTypes = new VehicleTypesLoader().load(typesPath);
        vehicles = new VehicleLoader(rents, vehicleTypes).load(vehiclesPath);
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
