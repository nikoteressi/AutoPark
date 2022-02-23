package application.dto.service;

import application.dto.OrdersDto;
import application.dto.VehicleDto;
import application.dto.VehicleRentsDto;
import application.dto.VehicleTypesDto;
import application.entity.Orders;
import application.infrastructure.core.annotations.AutoWired;
import application.vehiclecreation.ParserVehicleFromDB;
import application.vehiclesmodules.Rent;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DtoService {
    @AutoWired
    ParserVehicleFromDB parserVehicleFromDB;

    public DtoService() {
    }

    public List<VehicleDto> getVehicles() {
        return parserVehicleFromDB.fillVehicles().stream().map(vehicle -> VehicleDto.builder()
                .id(vehicle.getId())
                .typeId(vehicle.getVehicleType().getId())
                .typeName(vehicle.getVehicleType().getTypeName())
                .taxCoefficient(vehicle.getVehicleType().getTaxCoefficient())
                .color(vehicle.getColor().name())
                .engineName(vehicle.getEngine().getEngineName())
                .engineTaxCoefficient(vehicle.getEngine().getEngineTaxCoefficient())
                .tax(vehicle.getCalcTaxPerMonth())
                .manufactureYear(vehicle.getYearOfProduction())
                .mileage(vehicle.getMileage())
                .modelName(vehicle.getModel())
                .registrationNumber(vehicle.getLicensePlate())
                .tankVolume(vehicle.getTankCapacity())
                .weight(vehicle.getWeight())
                .per100km(vehicle.getEngine().getFuelPer100Km())
                .maxKm(vehicle.getEngine().getMaxKilometers())
                .income(vehicle.getTotalIncome())
                .build()).collect(Collectors.toList());
    }

    public List<VehicleTypesDto> getTypes() {
        return parserVehicleFromDB.fillVehicleTypes().stream().map(vehicleType -> VehicleTypesDto.builder()
                .id(vehicleType.getId())
                .typeName(vehicleType.getTypeName())
                .taxCoefficient(vehicleType.getTaxCoefficient())
                .build()).collect(Collectors.toList());
    }

    public List<VehicleRentsDto> getRents() {
        return parserVehicleFromDB.fillRents().stream().map(rents -> VehicleRentsDto.builder()
                .vehicleId(rents.getVehicleId())
                .rentCost(rents.getRentCost())
                .date(rents.getRentDate())
                .build()).collect(Collectors.toList());
    }

    public List<VehicleRentsDto> getRent(int id) {
        List<VehicleRentsDto> rents = new ArrayList<>();
        for (Rent v : parserVehicleFromDB.fillRents()) {
            if (v.getVehicleId() == id) {
                rents.add(VehicleRentsDto.builder()
                        .vehicleId(v.getVehicleId())
                        .rentCost(v.getRentCost())
                        .date(v.getRentDate())
                        .build());
            }
        }
        return rents;
    }

    public List<OrdersDto> getBrokenList() {
        List<OrdersDto> rents = new ArrayList<>();
        for (Orders o : parserVehicleFromDB.fillOrders()) {
            rents.add(OrdersDto.builder()
                    .axis(o.getAxis())
                    .glow_plug(o.getGlow_plug())
                    .sleeve(o.getSleeve())
                    .id(o.getId())
                    .grm(o.getGrm())
                    .joint(o.getJoint())
                    .oil(o.getOil())
                    .shaft(o.getShaft())
                    .filter(o.getFilter())
                    .vehicle_id(o.getVehicle_id())
                    .build());
        }
        return rents;
    }
}
