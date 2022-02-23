package application.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class VehicleDto {
    public int typeId;
    private final String typeName;
    private final double taxCoefficient;
    private final String modelName;
    private final int manufactureYear;
    private final String registrationNumber;
    private final double weight;
    private final int mileage;
    private final String color;
    private final double tankVolume;
    private final String engineName;
    private final double engineTaxCoefficient;
    private final int id;
    private final double per100km;
    private final double maxKm;
    private final double tax;
    private final double income;
}
