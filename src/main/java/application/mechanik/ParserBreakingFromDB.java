package application.mechanik;

import application.entity.Orders;
import application.infrastructure.core.annotations.AutoWired;
import application.service.OrdersService;
import application.service.VehicleService;
import application.vehiclesmodules.Vehicle;
import java.util.List;

public class ParserBreakingFromDB implements ParserBreakingsInterface{
    @AutoWired
    private OrdersService ordersService;
    @AutoWired
    private VehicleService vehicleService;

    @Override
    public void clearBreakdowns() {

    }

    @Override
    public boolean checkVehicle(Vehicle vehicle) {
        List<Orders> orders = ordersService.getAll();
        int ordersVehicleId = 0;
        for (Orders o : orders) {
            if (vehicle.getId() == o.getVehicle_id()) ordersVehicleId = o.getVehicle_id();
        }
        return vehicle.getId() == ordersVehicleId;
    }

    @Override
    public void writeBreakings() {

    }
}
