package application.mechanik;

import application.vehiclesmodules.Vehicle;

public interface Fixer {
    boolean detectBreaking(Vehicle vehicle);

    void repair(Vehicle vehicle);
    ParserBreakingsInterface getParser();
    boolean isBroken(Vehicle vehicle);

    default boolean detectAndRepair(Vehicle vehicle) {
        detectBreaking(vehicle);
        if (isBroken(vehicle)) {
            repair(vehicle);
            return true;
        }
        return false;
    }
}
