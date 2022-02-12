package application.vehiclecreation;

import application.infrastructure.core.annotations.AutoWired;
import application.vehiclesmodules.Rent;
import application.vehiclesmodules.TechnicalSpecialist;
import application.vehiclesmodules.Vehicle;
import application.vehiclesmodules.VehicleType;

import java.util.ArrayList;
import java.util.List;

public class ParserVehicleFromFile {
    private List<VehicleType> vehicleTypes = new ArrayList<>();
    private List<Vehicle> vehicles = new ArrayList<>();
    private List<Rent> rents = new ArrayList<>();
    @AutoWired
    private TechnicalSpecialist technicalSpecialist;

    public ParserVehicleFromFile() {

    }

    public List<VehicleType> fillVehicleTypes(List<VehicleType> vehicleTypes) {
        VehicleTypesLoader loader = new VehicleTypesLoader();
        String VehicleTypesPath = "src/main/java/application/csvfiles/types.csv";
        vehicleTypes.addAll(loader.load(VehicleTypesPath));
        this.vehicleTypes = vehicleTypes;
        return vehicleTypes;
    }

    public List<Rent> fillRents(List<Rent> rents) {
        RentsLoader loader = new RentsLoader();
        String rentsPath = "src/main/java/application/csvfiles/rents.csv";
        rents.addAll(loader.load(rentsPath));
        this.rents = rents;
        return rents;
    }

    public List<Vehicle> fillVehicles(List<Vehicle> vehicles) {
        VehicleLoader loader = new VehicleLoader(rents, vehicleTypes);
        String vehiclesPath = "src/main/java/application/csvfiles/vehicles.csv";
        vehicles.addAll(loader.load(vehiclesPath));
        this.vehicles = vehicles;
        return vehicles;
    }
}
