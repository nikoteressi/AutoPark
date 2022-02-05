package application.mechanik;

import application.infrastructure.core.annotations.AutoWired;
import application.vehiclesmodules.Vehicle;

import java.util.List;

public class Workroom {
    @AutoWired
    private Fixer mechanic;

    public Workroom() {
    }

    public Fixer getMechanic() {
        return mechanic;
    }

    public void setMechanic(MechanicService mechanic) {
        this.mechanic = mechanic;
    }

    public void checkAllVehicle(List<Vehicle> vehicles) {
        for (Vehicle v : vehicles) {
            mechanic.detectBreaking(v);
        }
        System.out.println("Broken vehicles: ");
        for (Vehicle v : vehicles) {
            if (mechanic.getParser().checkVehicle(v)) {
                System.out.println(v);
            }
        }
        System.out.println("Good vehicles: ");
        for (Vehicle v : vehicles) {
            if (!mechanic.getParser().checkVehicle(v)) {
                System.out.println(v);
            }
        }
    }
}
