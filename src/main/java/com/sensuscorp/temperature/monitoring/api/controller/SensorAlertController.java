package com.sensuscorp.temperature.monitoring.api.controller;

import com.sensuscorp.temperature.monitoring.api.model.SensorAlertInput;
import com.sensuscorp.temperature.monitoring.api.model.SensorAlertOutput;
import com.sensuscorp.temperature.monitoring.domain.model.SensorAlert;
import com.sensuscorp.temperature.monitoring.domain.model.SensorId;
import com.sensuscorp.temperature.monitoring.domain.repository.SensorAlertRepository;
import io.hypersistence.tsid.TSID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;

@RestController
@RequestMapping("/api/sensors/{sensorId}/alert")
@RequiredArgsConstructor
public class SensorAlertController {
    private final SensorAlertRepository sensorAlertRepository;

    @GetMapping
    public SensorAlertOutput getDatail(@PathVariable TSID sensorId){
        validIdNotNull(sensorId);
        SensorAlert sensorAlert = getSensorAlert(sensorId);
        return getSensorAlertOutput(sensorId, sensorAlert);
    }

    @PutMapping
    public SensorAlertOutput update(@PathVariable TSID sensorId, @RequestBody SensorAlertInput input){
        validIdNotNull(sensorId);
        SensorAlert sensorAlert = getSensorAlert(sensorId);
        sensorAlert.setMinTemperature(input.getMinTemperature());
        sensorAlert.setMaxTemperature(input.getMaxTemperature());
        sensorAlertRepository.save(sensorAlert);

        return getSensorAlertOutput(sensorId, sensorAlert);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable TSID sensorId){
        validIdNotNull(sensorId);
        SensorAlert sensorAlert = getSensorAlert(sensorId);
        sensorAlertRepository.deleteById(sensorAlert.getId());
    }

    private static void validIdNotNull(TSID sensorId) {
        if (Objects.isNull(sensorId)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    private SensorAlert getSensorAlert(TSID sensorId) {
        return sensorAlertRepository.findById(new SensorId(sensorId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    private SensorAlertOutput getSensorAlertOutput(TSID sensorId, SensorAlert sensorAlert) {
        return SensorAlertOutput.builder()
                .id(sensorId)
                .maxTemperature(sensorAlert.getMaxTemperature())
                .minTemperature(sensorAlert.getMinTemperature())
                .build();
    }
}
