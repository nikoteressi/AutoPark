package application.vehiclesmodules;

import application.engine.AbstractEngine;
import java.time.LocalDate;

public final class TechnicalSpecialist {
    public static final int LOWER_LIMIT_MANUFACTURE_YEAR = 1886;

    public TechnicalSpecialist() {
    }


    public static boolean validateEngineType(AbstractEngine engine)  {
        String[] items = engine.toString().split(", ");
        int nullCounter = 0;
        for (String item : items) {
            if (item == null || item.equals("") || item.equals(" ")) {
                nullCounter++;
            }
        }
        return nullCounter == 0;
    }
    public static boolean validateWeight (int weight) {
        return weight > 0;
    }

    public static boolean validateManufactureYear(int year) {
        int currentIntYear = Integer.parseInt(LocalDate.now().toString().substring(0, 4));
        return year >= LOWER_LIMIT_MANUFACTURE_YEAR && year <= currentIntYear;
    }

    public static boolean validateMileage(int mileage) {
        return mileage > 0;
    }

    public static boolean validateColorString(String color) {
        for (Colors c : Colors.values()) {
            if (color.equals(c.name())) return true;
        }
        return false;
    }

    public static boolean validateTechnicalType(String vehicleType) {
        if (vehicleType == null || vehicleType.equals("") || vehicleType.equals(" ")) {
            System.out.println("Empty vehicle type.");
            return false;
        }
        String[] items = vehicleType.split(", ");
        String type = items[0];
        double taxCoefficient = Double.parseDouble(items[items.length - 1]);
        return type.matches("[A-za-z]+") && taxCoefficient >= 0;
    }

    public static boolean validateRegistrationNumber(String plateNumber) {
        return plateNumber.matches("^[0-9]{4}\\s[A-Z]{2}-[0-9]$");
    }

    public static boolean validateModelName(String model) {
        return model != null && !model.equals(" ") && !model.equals("");
    }
}
