package vehiclecreation;

import vehiclesmodules.Rent;

import java.util.InputMismatchException;
import java.util.List;

public class RentCreator implements Creator<Rent>{

    @Override
    public Rent create(String csvString) {
        String[] parameters = csvString.split(",");
        if (parameters.length < 2) throw new InputMismatchException("Incorrect data in .csv file");
        String date = parameters[1];
        double rentCost = Double.parseDouble(parameters[2]);
        return new Rent(date, rentCost);
    }
}
