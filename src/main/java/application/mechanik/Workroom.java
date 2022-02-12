package application.mechanik;

import application.infrastructure.core.annotations.AutoWired;
import application.vehiclesmodules.Vehicle;

import java.util.List;

public class Workroom {
    @AutoWired
    private MechanicService mechanic;

    public Workroom() {
    }

    public Fixer getMechanic() {
        return mechanic;
    }

    public void setMechanic(MechanicService mechanic) {
        this.mechanic = mechanic;
    }

    public void checkAllVehicle(List<Vehicle> vehicles) {
        System.out.println("Broken vehicles: ");
//        vehicles.stream().forEach(e -> mechanic.detectBreaking(e));
        for (Vehicle v : vehicles) {
            if (mechanic.isBroken(v)) {
                System.out.println(v);
            }
        }
        System.out.println("Good vehicles: ");
        for (Vehicle v : vehicles) {
            if (!mechanic.isBroken(v)) {
                System.out.println(v);
            }
        }
    }
}
