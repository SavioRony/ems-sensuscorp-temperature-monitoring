package com.sensuscorp.temperature.monitoring.domain.repository;

import com.sensuscorp.temperature.monitoring.domain.model.SensorId;
import com.sensuscorp.temperature.monitoring.domain.model.SensorMonitoring;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SensorMonitoryRepository extends JpaRepository<SensorMonitoring, SensorId> {
}
