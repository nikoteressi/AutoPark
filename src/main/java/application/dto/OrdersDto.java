package application.dto;

import application.infrastructure.orm.annotations.Column;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class OrdersDto {
    private final Long id;
    private final Integer vehicle_id;
    private final Integer oil;
    private final Integer grm;
    private final Integer filter;
    private final Integer shaft;
    private final Integer sleeve;
    private final Integer joint;
    private final Integer axis;
    private final Integer glow_plug;
}
