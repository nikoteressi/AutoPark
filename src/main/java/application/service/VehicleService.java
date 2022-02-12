package application.service;

import application.engine.*;
import application.entity.Vehicles;
import application.exceptions.NoVehicleException;
import application.infrastructure.core.annotations.AutoWired;
import application.infrastructure.core.annotations.InitMethod;
import application.infrastructure.orm.EntityManager;
import application.infrastructure.orm.service.PostgreDataBaseService;
import application.vehiclecreation.ParserVehicleFromFile;
import application.vehiclecreation.ParserVehicleInterface;
import application.vehiclesmodules.Colors;
import application.vehiclesmodules.Rent;
import application.vehiclesmodules.Vehicle;
import application.vehiclesmodules.VehicleType;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class VehicleService {
    @AutoWired
    EntityManager entityManager;

    @InitMethod
    public void init() {
        ParserVehicleInterface parser = new ParserVehicleFromFile();
        List<VehicleType> types = parser.fillVehicleTypes();
        List<Rent> rents = parser.fillRents();
        Scanner scanner = null;
        try {
            scanner = new Scanner(new File("src/main/java/application/csvfiles/vehicles.csv"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        while (Objects.requireNonNull(scanner).hasNext()) {
            String csvString;
            try {
                csvString = scanner.nextLine();
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
                String engineName;
                switch (parameters[8]) {
                    case "Gasoline":
                        engineType = new GasolineEngine(Double.parseDouble(parameters[9]), Double.parseDouble(parameters[10]), Double.parseDouble(parameters[11]));
                        engineName = "Gasoline";
                        break;
                    case "Electrical":
                        engineType = new ElectricalEngine(Double.parseDouble(parameters[9]), Double.parseDouble(parameters[10]));
                        engineName = "Electrical";
                        break;
                    case "Diesel":
                        engineType = new DieselEngine(Double.parseDouble(parameters[9]), Double.parseDouble(parameters[10]), Double.parseDouble(parameters[11]));
                        engineName = "Diesel";
                        break;
                    default:
                        throw new NoVehicleException("No such application.engine type");
                }
                result = new Vehicle(type, engineType, modelName, licensePlate, weight, yearOfManufacture, mileage, color, rents);
                result.setId(Integer.parseInt(parameters[0]));
                Vehicles vehicles;
                if (engineName.equalsIgnoreCase("Diesel") || engineName.equalsIgnoreCase("Gasoline")) {
                    vehicles = Vehicles.builder().vehicle_id((long) result.getId()).type(result.getVehicleType().getId()).
                            model(result.getModel()).plate(result.getLicensePlate()).weight(result.getWeight()).year_of_production(result.getYearOfProduction()).mileage(result.getMileage()).
                            color(result.getColor().name()).engine_type(engineName).engine_volume(Double.parseDouble(parameters[9])).fuel_consumption_per_hour(Double.parseDouble(parameters[10])).tank_capacity(Double.parseDouble(parameters[11])).build();
                } else {
                    vehicles = Vehicles.builder().vehicle_id((long) result.getId()).type(result.getVehicleType().getId()).
                            model(result.getModel()).plate(result.getLicensePlate()).weight(result.getWeight()).year_of_production(result.getYearOfProduction()).mileage(result.getMileage()).
                            color(result.getColor().name()).engine_type(engineName).engine_volume(Double.parseDouble(parameters[9])).fuel_consumption_per_hour(Double.parseDouble(parameters[10])).tank_capacity(0.0).build();
                }
                entityManager.save(vehicles);
            } catch (NoVehicleException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public Optional<Vehicles> get(Long id) {
        return entityManager.get(id, Vehicles.class);
    }

    public List<Vehicles> getAll() {
        return entityManager.getAll(Vehicles.class);
    }

    public Long save(Vehicles v) {
        Vehicles vehicle = Vehicles.builder().vehicle_id(v.getVehicle_id()).type(v.getType()).
                model(v.getModel()).plate(v.getPlate()).weight(v.getWeight()).year_of_production(v.getYear_of_production()).mileage(v.getMileage()).
                color(v.getColor()).engine_type(v.getEngine_type()).engine_volume(v.getEngine_volume()).fuel_consumption_per_hour(v.getFuel_consumption_per_hour()).tank_capacity(v.getTank_capacity()).build();
        entityManager.save(vehicle);
        return vehicle.getVehicle_id();
    }
}
