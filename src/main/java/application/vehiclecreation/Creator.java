package application.vehiclecreation;

import application.exceptions.NoVehicleException;

public interface Creator<T>{
    T create(String csvString) throws NoVehicleException;
}
