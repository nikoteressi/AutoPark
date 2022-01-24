package mechanik;

import vehiclesmodules.Vehicle;

public interface Fixer {
//    Map<String, Integer> detectBreaking(vehiclescollection.Vehicle vehicle);
//    void repair(vehiclescollection.Vehicle vehicle);
    boolean isBroken(Vehicle vehicle);

    default  boolean detectAndRepair(Vehicle vehicle) {
//        detectBreaking(vehicle);
        if (isBroken(vehicle)) {
//            repair(vehicle);
            return true;
        }
        return false;
    }

}
