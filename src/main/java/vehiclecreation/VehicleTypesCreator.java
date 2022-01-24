package vehiclecreation;

import vehiclesmodules.VehicleType;

import java.util.InputMismatchException;

public class VehicleTypesCreator implements Creator<VehicleType>{
    @Override
    public VehicleType create(String csvString) {
        VehicleType type;
        String[] parameters = csvString.split(",");
        if (parameters.length < 3) throw new InputMismatchException("Incorrect data in .csv file");
        String name = parameters[1];
        double tax = Double.parseDouble(parameters[2]);
        type = new VehicleType(name, tax);
        type.setId(Integer.parseInt(parameters[0]));
        return type;
    }
}
