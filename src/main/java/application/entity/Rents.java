package application.entity;

import application.infrastructure.orm.annotations.Column;
import application.infrastructure.orm.annotations.ID;
import application.infrastructure.orm.annotations.Table;
import lombok.*;

import java.util.Date;

@Table(name = "rents")
@Builder
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Rents {
    @ID
    private Long id;
    @Column(name = "vehicle_id", unique = true)
    private Integer vehicle_id;
    @Column(name = "date")
    private String rent_date;
    @Column(name = "rent_cost")
    private Double rent_cost;
}
