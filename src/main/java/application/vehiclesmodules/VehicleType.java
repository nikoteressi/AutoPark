package application.vehiclesmodules;

public class VehicleType {
    private String typeName;
    private double taxCoefficient;
    private int id;
    public VehicleType(String typeName, double taxCoefficient) {
        this.typeName = typeName;
        this.taxCoefficient = taxCoefficient;
    }

    public VehicleType() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public double getTaxCoefficient() {
        return taxCoefficient;
    }

    public void setTaxCoefficient(double taxCoefficient) {
        this.taxCoefficient = taxCoefficient;
    }
    public  void display() {
        System.out.println("Name of type = " + this.typeName);
        System.out.println("Tax coefficient = " + this.taxCoefficient);
    }
    public String getString() {
        return typeName + ", " + taxCoefficient;
    }
}
