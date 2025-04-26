package com.sensuscorp.temperature.monitoring.api.model;


import lombok.Builder;
import lombok.Data;

import java.time.OffsetDateTime;

@Data
@Builder
public class SensorAlertInput {

    private Double maxTemperature;
    private Double minTemperature;
}
