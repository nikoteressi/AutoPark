package engine;

public class DieselEngine  extends CombustionEngine{
    public DieselEngine(double engineVolume, double fuelConsumptionPerHour, double tankCapacity) {
        super("Diesel", 1.2, engineVolume, fuelConsumptionPerHour, tankCapacity);
    }
}
