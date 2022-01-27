package application.vehiclesmodules;

import application.engine.AbstractEngine;
import application.engine.DefectVehicleException;
import application.engine.Startable;

import java.io.File;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Scanner;

public class Vehicle implements Comparable<Vehicle> {
    private AbstractEngine engine;
    private int yearOfProduction;
    private VehicleType vehicleType;
    private String model;
    private String licensePlate;
    private int weight;
    private int mileage;
    private Colors color;
    private int id;
    private List<Rent> rentList;
    private String brokenParts;
    private int sumOfBrokenParts;
    private boolean isBroken = false;
    private boolean isWashed = false;
    private boolean garage = false;
    private boolean rent = false;

    public Vehicle() {
    }

    public Vehicle(VehicleType vehicleType, AbstractEngine engine, String model, String licensePlat, int weight, int yearOfProduction, int mileage, Colors color, List<Rent> rentList) {
        this.vehicleType = vehicleType;
        this.engine = engine;
        this.model = model;
        this.licensePlate = licensePlat;
        this.weight = weight;
        this.yearOfProduction = yearOfProduction;
        this.mileage = mileage;
        this.color = color;
        this.rentList = rentList;
    }

    public int getSumOfBrokenParts() {
        return sumOfBrokenParts;
    }

    public void setSumOfBrokenParts(int sumOfBrokenParts) {
        this.sumOfBrokenParts = sumOfBrokenParts;
    }
    public String getBrokenParts() {
        return brokenParts;
    }

    public void setBrokenParts(String brokenPartsPath) {
        File file = new File(brokenPartsPath);
        Scanner scanner = new Scanner(brokenPartsPath);
        while (scanner.hasNext()) {
            String brokenParts = scanner.nextLine();
            String[] tokens = brokenParts.split(",");

                for (int i = 1; i < tokens.length; i += 2) {
                    if (Integer.parseInt(tokens[i]) != 0) {
                        isBroken = true;
                        this.brokenParts = brokenParts;
                    }
                }
        }

        this.brokenParts = brokenParts;
    }

    public boolean isBroken() {
        return isBroken;
    }

    public void setBroken(boolean broken) {
        isBroken = broken;
    }

    public boolean isGarage() {
        return garage;
    }

    public void setGarage(boolean garage) {
        this.garage = garage;
    }
//    private double tankCapacity;

    public boolean isWashed() {
        return isWashed;
    }

    public void setWashed(boolean washed) {
        isWashed = washed;
    }

    public double getTotalIncome() {
        double sum = 0.0;
        for (Rent r : rentList) {
            sum += r.getRentCost();
        }
        return sum;
    }


    public double getTotalProfit() {
        return getTotalIncome() - getCalcTaxPerMonth();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Rent> getRentList() {
        return rentList;
    }

    public void setRentList(List<Rent> rentList) {
        this.rentList = rentList;
    }

    public Startable getEngine() {
        return engine;
    }

    public void setEngine(AbstractEngine engine) {
        this.engine = engine;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(VehicleType vehicleType) {
        this.vehicleType = vehicleType;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getYearOfProduction() {
        return yearOfProduction;
    }

    public void setYearOfProduction(int yearOfProduction) {
        this.yearOfProduction = yearOfProduction;
    }

    public int getMileage() {
        return mileage;
    }

    public void setMileage(int mileage) {
        this.mileage = mileage;
    }

    public Colors getColor() {
        return color;
    }

    public void setColor(Colors color) {
        this.color = color;
    }

//    public double getTankCapacity() {
//        return tankCapacity;
//    }
    public void  rentVehicle(int days) {
        if (isBroken) {
            try {
                throw new DefectVehicleException();
            } catch (DefectVehicleException e) {
                System.out.println("The vehicle is not order! Please get another car");
            }
        } else {
            double rentCharge = (getCalcTaxPerMonth() / 31) * days;
            DecimalFormat f = new DecimalFormat("##.00");
            System.out.println(getModel() + " was rented on " + days + " days");
            System.out.println("Total cost = " + f.format(rentCharge));
        }
    }

    public double getCalcTaxPerMonth() {
        return (weight * 0.0013) + (vehicleType.getTaxCoefficient() * 30 + engine.getTaxCoefficientByEngineType() * 30) + 5;
    }

    public int compareTo(Vehicle vehicle) {
        if (this.yearOfProduction - vehicle.getYearOfProduction() == 0) {
            return mileage - vehicle.getMileage();
        } else {
            return yearOfProduction - vehicle.getYearOfProduction();
        }
    }

    @Override
    public String toString() {
        return vehicleType.getTypeName() + ", " + engine.toString() + ", " + model +
                ", " + licensePlate + ", " + weight + ", " + yearOfProduction +
                ", " + mileage + ", " + color;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vehicle vehicle = (Vehicle) o;
        return vehicleType.equals(vehicle.vehicleType) && model.equals(vehicle.model);
    }

}
