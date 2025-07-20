package ru.yandex.practicum.smarthometech.aggregator.service;

import java.util.Optional;
import ru.yandex.practicum.kafka.telemetry.event.SensorEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.SensorsSnapshotAvro;

public interface SnapshotService {


    public Optional<SensorsSnapshotAvro> updateState(SensorEventAvro event);

}
