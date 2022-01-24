package vehiclesmodules;

public class Rent {
    private String rentDate;
    private double rentCost;

    public Rent(String rentDate, double rentCost) {
        this.rentDate = rentDate;
        this.rentCost = rentCost;
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
