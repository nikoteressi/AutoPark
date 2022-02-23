package application.engine;

public interface Startable {
    double getTaxPerMonth();
    double getMaxKilometers();
    String getEngineName();
    double getEngineTaxCoefficient();
    double getFuelPer100Km();
}
