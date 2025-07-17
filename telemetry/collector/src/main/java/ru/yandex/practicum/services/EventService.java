package ru.yandex.practicum.services;

import ru.yandex.practicum.dto.HubEvent;
import ru.yandex.practicum.dto.SensorEvent;

public interface EventService {
    void preparationAndSendSensorEvent(SensorEvent sensorEvent);

    void preparationAndSendHubEvent(HubEvent hubEvent);
}
