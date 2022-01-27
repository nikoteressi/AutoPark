package application.engine;

public class ElectricalEngine extends AbstractEngine{
    private double currentChargeLevel;
    private double electricityConsumptionPerHour;

    public ElectricalEngine(double currentChargeLevel, double electricityConsumptionPerHour) {
        super("Electrical", 0.1);
        this.currentChargeLevel = currentChargeLevel;
        this.electricityConsumptionPerHour = electricityConsumptionPerHour;
    }

    public double getCurrentChargeLevel() {
        return currentChargeLevel;
    }

    public void setCurrentChargeLevel(double currentChargeLevel) {
        this.currentChargeLevel = currentChargeLevel;
    }

    public double getElectricityConsumptionPerHour() {
        return electricityConsumptionPerHour;
    }

    public void setElectricityConsumptionPerHour(double electricityConsumptionPerHour) {
        this.electricityConsumptionPerHour = electricityConsumptionPerHour;
    }
    public double getTaxPerMonth() {
        return getTaxCoefficientByEngineType() * 31;
    }

    public double getMaxKilometers() {
        return currentChargeLevel / electricityConsumptionPerHour;
    }
}
