package application.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class VehicleTypesDto {
    public int id;
    private final String typeName;
    private final double taxCoefficient;
}
