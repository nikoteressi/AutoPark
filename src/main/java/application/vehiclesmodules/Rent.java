package application.vehiclesmodules;

public class Rent {
    private String rentDate;
    private double rentCost;
    private int vehicleId;

    public Rent(String rentDate, double rentCost) {
        this.rentDate = rentDate;
        this.rentCost = rentCost;
    }

    public int getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(int vehicleId) {
        this.vehicleId = vehicleId;
    }

    public Rent() {
    }

    public String getRentDate() {
        return rentDate;
    }

    public void setRentDate(String rentDate) {
        this.rentDate = rentDate;
    }

    public double getRentCost() {
        return rentCost;
    }

    public void setRentCost(double rentCost) {
        this.rentCost = rentCost;
    }
}
