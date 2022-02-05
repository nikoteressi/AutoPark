package application.entity;

import application.infrastructure.orm.annotations.Column;
import application.infrastructure.orm.annotations.ID;
import application.infrastructure.orm.annotations.Table;
import lombok.*;

@Table(name = "orders")
@Builder
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Orders {
    @ID
    private Long id;
    @Column(name = "vehicle_id", unique = true)
    private Integer vehicle_id;
    @Column(name = "oil")
    private Integer oil;
    @Column(name = "grm")
    private Integer grm;
    @Column(name = "filter")
    private Integer filter;
    @Column(name = "shaft")
    private Integer shaft;
    @Column(name = "sleeve")
    private Integer sleeve;
    @Column(name = "joint")
    private Integer joint;
    @Column(name = "axis")
    private Integer axis;
    @Column(name = "glow_plug")
    private Integer glow_plug;

}
