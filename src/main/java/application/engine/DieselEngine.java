package application.engine;

public class DieselEngine  extends CombustionEngine{
    public DieselEngine(double engineVolume, double fuelConsumptionPerHour, double tankCapacity) {
        super("Diesel", 1.2, engineVolume, fuelConsumptionPerHour, tankCapacity);
    }

    @Override
    public String getEngineName() {
        return getEngineTypeName();
    }

    @Override
    public double getEngineTaxCoefficient() {
        return getTaxCoefficientByEngineType();
    }

    @Override
    public double getFuelPer100Km() {
        return getFuelConsumptionPer100();
    }
}
