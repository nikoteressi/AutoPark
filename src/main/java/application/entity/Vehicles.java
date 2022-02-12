package application.entity;

import application.infrastructure.orm.annotations.Column;
import application.infrastructure.orm.annotations.ID;
import application.infrastructure.orm.annotations.Table;
import lombok.*;

@Table(name = "vehicles")
@Builder
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor

public class Vehicles {
    @ID
    Long id;
    @Column(name = "vehicle_id", unique = true)
    private Long vehicle_id;
    @Column(name = "type")
    private Integer type;
    @Column(name = "model")
    private String model;
    @Column(name = "plate")
    private String plate;
    @Column(name = "weight")
    private Double weight;
    @Column(name = "year_of_production")
    private Integer year_of_production;
    @Column(name = "mileage")
    private Integer mileage;
    @Column(name = "color")
    private String color;
    @Column(name = "engine_type")
    private String engine_type;
    @Column(name = "engine_volume")
    private Double engine_volume;
    @Column(name = "fuel_consumption_per_hour")
    private Double fuel_consumption_per_hour;
    @Column(name = "tank_capacity")
    private Double tank_capacity;
}
