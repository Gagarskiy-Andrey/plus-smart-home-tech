package ru.yandex.practicum.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.dto.*;
import ru.yandex.practicum.kafka.KafkaAvroProducer;
import ru.yandex.practicum.kafka.telemetry.event.HubEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.SensorEventAvro;
import ru.yandex.practicum.mappers.EventMapper;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final EventMapper eventMapper;
    private final KafkaAvroProducer kafkaProducer;

    @Override
    public void preparationAndSendSensorEvent(SensorEvent sensorEvent) {

        SensorEventAvro avroEvent = switch (sensorEvent) {
            case LightSensorEvent e -> eventMapper.toAvro(e);
            case MotionSensorEvent e -> eventMapper.toAvro(e);
            case ClimateSensorEvent e -> eventMapper.toAvro(e);
            case SwitchSensorEvent e -> eventMapper.toAvro(e);
            case TemperatureSensorEvent e -> eventMapper.toAvro(e);
            default -> throw new IllegalArgumentException("Unsupported sensor event type: " + sensorEvent.getType());
        };
        kafkaProducer.sendSensorEvent(avroEvent);

    }

    @Override
    public void preparationAndSendHubEvent(HubEvent hubEvent) {

        HubEventAvro avroEvent = switch (hubEvent) {
            case DeviceAddedEvent e -> eventMapper.toAvro(e);
            case DeviceRemovedEvent e -> eventMapper.toAvro(e);
            case ScenarioAddedEvent e -> eventMapper.toAvro(e);
            case ScenarioRemovedEvent e -> eventMapper.toAvro(e);
            default -> throw new IllegalArgumentException("Unsupported hub event type: " + hubEvent.getType());
        };
        kafkaProducer.sendHubEvent(avroEvent);

    }
}
