package ru.yandex.practicum.handler;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.grpc.telemetry.event.SensorEventProto.PayloadCase;
import ru.yandex.practicum.kafka.KafkaAvroProducer;
import ru.yandex.practicum.mappers.EventMapper;

@Component
@RequiredArgsConstructor
@Getter
public class MotionSensorEventHandler implements SensorEventHandler {

    private final EventMapper mapper;
    private final KafkaAvroProducer producer;

    @Override
    public PayloadCase getHandledType() {
        return PayloadCase.MOTION_SENSOR_EVENT;
    }
}
