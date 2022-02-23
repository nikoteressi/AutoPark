package application.vehiclecreation;

import application.engine.AbstractEngine;
import application.engine.DieselEngine;
import application.engine.ElectricalEngine;
import application.engine.GasolineEngine;
import application.entity.Orders;
import application.entity.Rents;
import application.entity.Types;
import application.entity.Vehicles;
import application.exceptions.NoVehicleException;
import application.infrastructure.core.annotations.AutoWired;
import application.infrastructure.orm.EntityManager;
import application.service.OrdersService;
import application.service.RentsService;
import application.service.TypesService;
import application.service.VehicleService;
import application.vehiclesmodules.Colors;
import application.vehiclesmodules.Rent;
import application.vehiclesmodules.Vehicle;
import application.vehiclesmodules.VehicleType;
import lombok.SneakyThrows;

import java.util.ArrayList;
import java.util.List;

public class ParserVehicleFromDB implements ParserVehicleInterface {
    @AutoWired
    private TypesService typesService;
    @AutoWired
    private VehicleService vehicleService;
    @AutoWired
    private RentsService rentsService;
    @AutoWired
    private OrdersService ordersService;
    @AutoWired
    EntityManager entityManager;

    @Override
    public List<VehicleType> fillVehicleTypes() {
        List<VehicleType> vehicleType = new ArrayList<>();
        for (Types t : typesService.getAll()) {
            vehicleType.add(new VehicleType(t.getName(), t.getCoefTaxes()));
        }
        return vehicleType;
    }

    @Override
    public List<Rent> fillRents() {
        List<Rent> rent = new ArrayList<>();
        for (Rents r : rentsService.getAll()) {
            rent.add(new Rent(r.getRent_date(), r.getRent_cost()));
        }
        return rent;
    }

    @Override
    public List<Vehicle> fillVehicles() {
        List<Vehicle> vehicle = new ArrayList<>();
        List<VehicleType> vehicleType = fillVehicleTypes();
        for (Vehicles v : vehicleService.getAll()) {
            vehicle = createVehicle();

        }
        return vehicle;
    }

    public List<Orders> fillOrders() {
        return new ArrayList<>(ordersService.getAll());
    }
    @SneakyThrows
    public List<Vehicle> createVehicle() {
        List<Vehicle> vehicle = new ArrayList<>();
        List<Vehicles> vehicles = vehicleService.getAll();
        List<VehicleType> types = fillVehicleTypes();
        for (Vehicles v : vehicles) {
            VehicleType type = types.get(v.getType() - 1);
            String modelName = v.getModel();
            String licensePlate = v.getPlate();
            double weight = v.getWeight();
            int yearOfManufacture = v.getYear_of_production();
            double mileage = v.getMileage();
            Colors color = Colors.get(v.getColor());
            AbstractEngine engineType;
            switch (v.getEngine_type()) {
                case "Gasoline":
                    engineType = new GasolineEngine(v.getEngine_volume(), v.getFuel_consumption_per_hour(), v.getTank_capacity());
                    break;
                case "Electrical":
                    engineType = new ElectricalEngine(v.getEngine_volume(), v.getFuel_consumption_per_hour());
                    break;
                case "Diesel":
                    engineType = new DieselEngine(v.getEngine_volume(), v.getFuel_consumption_per_hour(), v.getTank_capacity());
                    break;
                default:
                    throw new NoVehicleException("No such application.engine type");
            }
            Vehicle veh = new Vehicle(type, engineType, modelName, licensePlate, (int) weight, yearOfManufacture, (int) mileage, color, fillRents());
            veh.setId(v.getVehicle_id().intValue());
            vehicle.add(veh);
        }
        return vehicle;
    }
}
