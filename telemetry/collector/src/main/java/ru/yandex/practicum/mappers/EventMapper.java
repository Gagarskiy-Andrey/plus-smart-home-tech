package ru.yandex.practicum.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.yandex.practicum.dto.*;
import ru.yandex.practicum.kafka.telemetry.event.*;
import ru.yandex.practicum.mappers.qualifier.FullEvent;
import ru.yandex.practicum.mappers.qualifier.Payload;

@Mapper(componentModel = "spring")
public interface EventMapper {

    // --- SENSOR EVENT MAPPERS ---

    @Mapping(target = "payload", source = "dto", qualifiedBy = Payload.class)
    @FullEvent
    SensorEventAvro toAvro(LightSensorEvent dto);

    @Payload
    LightSensorAvro toPayload(LightSensorEvent dto);

    @Mapping(target = "payload", source = "dto", qualifiedBy = Payload.class)
    @FullEvent
    SensorEventAvro toAvro(MotionSensorEvent dto);

    @Payload
    MotionSensorAvro toPayload(MotionSensorEvent dto);

    @Mapping(target = "payload", source = "dto", qualifiedBy = Payload.class)
    @FullEvent
    SensorEventAvro toAvro(ClimateSensorEvent dto);

    @Payload
    ClimateSensorAvro toPayload(ClimateSensorEvent dto);

    @Mapping(target = "payload", source = "dto", qualifiedBy = Payload.class)
    @FullEvent
    SensorEventAvro toAvro(SwitchSensorEvent dto);

    @Payload
    SwitchSensorAvro toPayload(SwitchSensorEvent dto);

    @Mapping(target = "payload", source = "dto", qualifiedBy = Payload.class)
    @FullEvent
    SensorEventAvro toAvro(TemperatureSensorEvent dto);

    @Payload
    TemperatureSensorAvro toPayload(TemperatureSensorEvent dto);

    // --- HUB EVENT MAPPERS ---

    @Mapping(target = "payload", source = "dto", qualifiedBy = Payload.class)
    @FullEvent
    HubEventAvro toAvro(DeviceAddedEvent dto);

    @Payload
    @Mapping(target = "type", source = "deviceType")
    DeviceAddedEventAvro toPayload(DeviceAddedEvent dto);

    @Mapping(target = "payload", source = "dto", qualifiedBy = Payload.class)
    @FullEvent
    HubEventAvro toAvro(DeviceRemovedEvent dto);

    @Payload
    DeviceRemovedEvent toPayload(DeviceRemovedEvent dto);

    @Mapping(target = "payload", source = "dto", qualifiedBy = Payload.class)
    @FullEvent
    HubEventAvro toAvro(ScenarioAddedEvent dto);

    @Payload
    ScenarioAddedEventAvro toPayload(ScenarioAddedEvent dto);

    @Mapping(target = "payload", source = "dto", qualifiedBy = Payload.class)
    @FullEvent
    HubEventAvro toAvro(ScenarioRemovedEvent dto);

    @Payload
    ScenarioRemovedEventAvro toPayload(ScenarioRemovedEvent dto);
}
