package application.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class VehicleRentsDto {
    private final int vehicleId;
    private final String date;
    private final double rentCost;

}
