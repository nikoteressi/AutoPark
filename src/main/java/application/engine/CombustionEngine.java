package application.engine;

public abstract class CombustionEngine extends AbstractEngine{
    private double engineVolume;
    private double tankCapacity;
    private double fuelConsumptionPer100;

    public CombustionEngine(String engineTypeName, double taxCoefficientByEngineType, double engineVolume, double fuelConsumptionPer100, double tankCapacity) {
        super(engineTypeName, taxCoefficientByEngineType);
        this.engineVolume = engineVolume;
        this.tankCapacity = tankCapacity;
        this.fuelConsumptionPer100 = fuelConsumptionPer100;
    }

    public double getEngineVolume() {
        return engineVolume;
    }

    public void setEngineVolume(double engineVolume) {
        this.engineVolume = engineVolume;
    }

    public double getTankCapacity() {
        return tankCapacity;
    }

    public void setTankCapacity(int tankCapacity) {
        this.tankCapacity = tankCapacity;
    }

    public double getFuelConsumptionPer100() {
        return fuelConsumptionPer100;
    }

    public void setFuelConsumptionPer100(double fuelConsumptionPer100) {
        this.fuelConsumptionPer100 = fuelConsumptionPer100;
    }

    public double getTaxPerMonth () {
        return this.getTaxCoefficientByEngineType() * 31;
    }

    public double getMaxKilometers() {
        return tankCapacity * 100 / fuelConsumptionPer100;
    }
}
