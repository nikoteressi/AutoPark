package application.engine;

public class GasolineEngine extends CombustionEngine{
    public GasolineEngine(double engineVolume, double fuelConsumptionPerHour, double tankCapacity) {
        super("Gasoline", 1.1, engineVolume, fuelConsumptionPerHour, tankCapacity);
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
