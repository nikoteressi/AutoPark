package application.vehiclecreation;

import application.engine.AbstractEngine;
import application.engine.DieselEngine;
import application.engine.ElectricalEngine;
import application.engine.GasolineEngine;
import application.exceptions.NoVehicleException;
import application.vehiclesmodules.Colors;
import application.vehiclesmodules.Rent;
import application.vehiclesmodules.Vehicle;
import application.vehiclesmodules.VehicleType;

import java.util.List;

public class VehicleCreator implements Creator<Vehicle>{
    private List<Rent> rents;
    private List<VehicleType> types;

    public VehicleCreator(List<Rent> rents, List<VehicleType> types) {
        this.rents = rents;
        this.types = types;
    }

    @Override
    public Vehicle create(String csvString) throws NoVehicleException {
        Vehicle result;
        String[] parameters = csvString.split(",");
        if (parameters.length < 10) throw new NoVehicleException("Incorrect data in .csv file");
        VehicleType type = types.get(Integer.parseInt(parameters[1]) - 1);
        String modelName = parameters[2];
        String licensePlate = parameters[3];
        int weight = Integer.parseInt(parameters[4]);
        int yearOfManufacture = Integer.parseInt(parameters[5]);
        int mileage = Integer.parseInt(parameters[6]);
        Colors color = Colors.get(parameters[7]);
        AbstractEngine engineType;
        switch (parameters[8]) {
            case "Gasoline":
                engineType = new GasolineEngine(Double.parseDouble(parameters[9]), Double.parseDouble(parameters[10]), Double.parseDouble(parameters[11]));
                break;
            case "Electrical":
                engineType = new ElectricalEngine(Double.parseDouble(parameters[9]), Double.parseDouble(parameters[10]));
                break;
            case "Diesel":
                engineType = new DieselEngine(Double.parseDouble(parameters[9]), Double.parseDouble(parameters[10]), Double.parseDouble(parameters[11]));
                break;
            default:
                throw new NoVehicleException("No such application.engine type");
        }
        result = new Vehicle(type, engineType, modelName, licensePlate, weight, yearOfManufacture, mileage, color, rents);
        result.setId(Integer.parseInt(parameters[0]));
        return result;
    }
}
