package application.mechanik;

import application.vehiclesmodules.Vehicle;

import java.util.List;
import java.util.Map;

public interface ParserBreakingsInterface {
    void clearBreakdowns();
    boolean checkVehicle(Vehicle vehicle);
    void writeBreakings();

}
