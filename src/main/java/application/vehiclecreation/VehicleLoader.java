package application.vehiclecreation;

import application.exceptions.NoVehicleException;
import application.vehiclesmodules.Rent;
import application.vehiclesmodules.Vehicle;
import application.vehiclesmodules.VehicleType;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class VehicleLoader implements Loader<Vehicle> {
   private List<Rent> rents;
    private List<VehicleType> types;

    public VehicleLoader(List<Rent> rents, List<VehicleType> types) {
        this.rents = rents;
        this.types = types;
    }

    @Override
    public List<Vehicle> load(String path) {
        List<Vehicle> vehicle = new ArrayList<>();
        Scanner scanner = null;
        try {
            scanner = new Scanner(new File(path));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        while (scanner.hasNext()) {
            try {
                vehicle.add(new VehicleCreator(rents, types).create(scanner.nextLine()));
            } catch (NoVehicleException e) {
                System.out.println(e.getMessage());
            }
        }
        return vehicle;
    }
}
