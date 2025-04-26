package com.sensuscorp.temperature.monitoring.api.controller;

import com.sensuscorp.temperature.monitoring.api.model.SensorMonitoringOutput;
import com.sensuscorp.temperature.monitoring.domain.model.SensorId;
import com.sensuscorp.temperature.monitoring.domain.model.SensorMonitoring;
import com.sensuscorp.temperature.monitoring.domain.repository.SensorMonitoryRepository;
import io.hypersistence.tsid.TSID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/sensors/{sensorId}/monitoring")
@RequiredArgsConstructor
public class SensorMonitoryController {
    private final SensorMonitoryRepository repository;

    @GetMapping
    public SensorMonitoringOutput getDetail(@PathVariable TSID sensorId){
        SensorMonitoring sensorMonitoring = getSensorMonitoringById(sensorId);

        return SensorMonitoringOutput.builder()
                .id(sensorMonitoring.getId().getValue())
                .enabled(sensorMonitoring.getEnabled())
                .lastTemperature(sensorMonitoring.getLastTemperature())
                .updateAt(sensorMonitoring.getUpdatedAt())
                .build();
    }

    private SensorMonitoring getSensorMonitoringById(TSID sensorId) {
        return repository.findById(new SensorId(sensorId))
                .orElse(SensorMonitoring.builder()
                        .id(new SensorId(sensorId))
                        .enabled(false)
                        .lastTemperature(null)
                        .updatedAt(null)
                        .build());
    }

    @PutMapping("/enable")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void enable(@PathVariable TSID sensorId){
        SensorMonitoring sensorMonitoring = getSensorMonitoringById(sensorId);
        sensorMonitoring.setEnabled(true);
        repository.saveAndFlush(sensorMonitoring);
    }

    @DeleteMapping("/enable")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void disable(@PathVariable TSID sensorId){
        SensorMonitoring sensorMonitoring = getSensorMonitoringById(sensorId);
        sensorMonitoring.setEnabled(false);
        repository.saveAndFlush(sensorMonitoring);
    }
}
