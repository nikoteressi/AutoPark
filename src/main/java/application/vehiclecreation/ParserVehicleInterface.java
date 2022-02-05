package application.vehiclecreation;

import application.vehiclesmodules.Rent;
import application.vehiclesmodules.Vehicle;
import application.vehiclesmodules.VehicleType;
import java.util.List;

public interface ParserVehicleInterface {
    List<VehicleType> fillVehicleTypes();
    List<Rent> fillRents();
    List<Vehicle> fillVehicles();
}
