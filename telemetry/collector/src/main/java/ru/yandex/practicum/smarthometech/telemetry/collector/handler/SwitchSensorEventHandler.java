package ru.yandex.practicum.smarthometech.telemetry.collector.handler;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.grpc.telemetry.event.SensorEventProto.PayloadCase;
import ru.yandex.practicum.smarthometech.telemetry.collector.kafka.KafkaAvroProducer;
import ru.yandex.practicum.smarthometech.telemetry.collector.mapper.EventMapper;

@Component
@RequiredArgsConstructor
@Getter
public class SwitchSensorEventHandler implements SensorEventHandler {

    private final EventMapper mapper;
    private final KafkaAvroProducer producer;

    @Override
    public PayloadCase getHandledType() {
        return PayloadCase.SWITCH_SENSOR_EVENT;
    }
}
