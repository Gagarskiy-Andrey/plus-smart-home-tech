package ru.yandex.practicum.smarthometech.telemetry.collector.services;

import ru.yandex.practicum.smarthometech.telemetry.collector.dto.HubEvent;
import ru.yandex.practicum.smarthometech.telemetry.collector.dto.SensorEvent;

public interface EventService {
    void preparationAndSendSensorEvent(SensorEvent sensorEvent);

    void preparationAndSendHubEvent(HubEvent hubEvent);
}
