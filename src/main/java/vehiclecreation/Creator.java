package vehiclecreation;

import exceptions.NoVehicleException;
import vehiclesmodules.Rent;

import java.util.InputMismatchException;

public interface Creator<T>{
    T create(String csvString) throws NoVehicleException;
}
