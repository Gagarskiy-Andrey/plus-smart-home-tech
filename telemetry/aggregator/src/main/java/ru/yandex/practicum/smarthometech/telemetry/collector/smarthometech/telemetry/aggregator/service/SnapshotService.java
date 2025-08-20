package ru.yandex.practicum.smarthometech.telemetry.collector.smarthometech.telemetry.aggregator.service;

import java.util.Optional;
import ru.yandex.practicum.kafka.telemetry.event.SensorEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.SensorsSnapshotAvro;

public interface SnapshotService {


    Optional<SensorsSnapshotAvro> updateState(SensorEventAvro event);

}
