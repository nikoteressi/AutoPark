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
    private final String VEHICLE_TYPES_PATH = "src/main/java/application.csvfiles/types.csv";
    private final String VEHICLES_PATH = "src/main/java/application.csvfiles/vehicles.csv";
    private final String RENTS_PATH = "src/main/java/application.csvfiles/rents.csv";

    public ParserVehicleFromFile() {

    }

    public List<VehicleType> fillVehicleTypes(List<VehicleType> vehicleTypes) {
        VehicleTypesLoader loader = new VehicleTypesLoader();
        vehicleTypes.addAll(loader.load(VEHICLE_TYPES_PATH));
        this.vehicleTypes = vehicleTypes;
        return vehicleTypes;
    }

    public List<Rent> fillRents(List<Rent> rents) {
        RentsLoader loader = new RentsLoader();
        rents.addAll(loader.load(RENTS_PATH));
        this.rents = rents;
        return rents;
    }

    public List<Vehicle> fillVehicles(List<Vehicle> vehicles) {
        VehicleLoader loader = new VehicleLoader(rents, vehicleTypes);
        vehicles.addAll(loader.load(VEHICLES_PATH));
        this.vehicles = vehicles;
        return vehicles;
    }
}
