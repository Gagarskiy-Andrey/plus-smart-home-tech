package ru.yandex.practicum.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.avro.specific.SpecificRecordBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.kafka.telemetry.event.HubEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.SensorEventAvro;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
public class KafkaAvroProducer {

    private final KafkaTemplate<String, SpecificRecordBase> kafkaTemplate;

    @Value("${kafka.sensor-events-topic}")
    private String sensorTopic;

    @Value("${kafka.hub-events-topic}")
    private String hubTopic;

    @Autowired
    public KafkaAvroProducer(KafkaTemplate<String, SpecificRecordBase> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendSensorEvent(SensorEventAvro sensorEvent) {

        long timeStamp = sensorEvent.getTimestamp().toEpochMilli();

        String key = sensorEvent.getHubId();

        log.info("Отправка Avro-сообщения в топик {}: Ключ='{}', Значение='{}'", sensorTopic, key, sensorEvent);

        CompletableFuture<SendResult<String, SpecificRecordBase>> future = kafkaTemplate.send(sensorTopic, null, timeStamp, sensorEvent.getHubId(), sensorEvent);

        future.whenComplete((result, ex) -> {
            if (ex == null) {
                log.info("Сообщение успешно отправлено в топик '{}', раздел {}, смещение {}",
                        result.getRecordMetadata().topic(),
                        result.getRecordMetadata().partition(),
                        result.getRecordMetadata().offset());
            } else {
                log.error("Не удалось отправить сообщение: {}", ex.getMessage(), ex);
            }
        });
    }

    public void sendHubEvent(HubEventAvro hubEvent) {

        long timeStamp = hubEvent.getTimestamp().toEpochMilli();

        String key = hubEvent.getHubId();

        log.info("Отправка Avro-сообщения в топик {}: Ключ='{}', Значение='{}'", hubTopic, key, hubEvent);

        CompletableFuture<SendResult<String, SpecificRecordBase>> future = kafkaTemplate.send(hubTopic, null, timeStamp, hubEvent.getHubId(), hubEvent);

        future.whenComplete((result, ex) -> {
            if (ex == null) {
                log.info("Сообщение успешно отправлено в топик '{}', раздел {}, смещение {}",
                        result.getRecordMetadata().topic(),
                        result.getRecordMetadata().partition(),
                        result.getRecordMetadata().offset());
            } else {
                log.error("Не удалось отправить сообщение: {}", ex.getMessage(), ex);
            }
        });
    }


}

