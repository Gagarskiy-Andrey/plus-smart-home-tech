package ru.yandex.practicum.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.avro.generic.GenericContainer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.kafka.telemetry.event.HubEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.SensorEventAvro;
import ru.yandex.practicum.kafka.telemetry.serializer.BaseAvroSerializer;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
public class KafkaAvroProducer {

    private final KafkaProducer<String, Object> kafkaProducer;

    private final BaseAvroSerializer<GenericContainer> avroSerializer = new BaseAvroSerializer<>();

    @Value("${kafka.sensor-events-topic}")
    private String sensorTopic;

    @Value("${kafka.hub-events-topic}")
    private String hubTopic;

    @Autowired
    public KafkaAvroProducer(KafkaProducer<String, Object> kafkaProducer) {
        this.kafkaProducer = kafkaProducer;
    }

    public void sendSensorEvent(SensorEventAvro sensorEvent) {

        byte[] eventBytes = avroSerializer.serialize(sensorTopic, sensorEvent);

        long timeStamp = sensorEvent.getTimestamp().toEpochMilli();

        String key = sensorEvent.getHubId();

        log.info("Отправка Avro-сообщения в топик {}: Ключ='{}', Значение='{}'", sensorTopic, key, sensorEvent);

        ProducerRecord<String, Object> record = new ProducerRecord<>(sensorTopic, null, timeStamp, key, eventBytes);
        CompletableFuture.runAsync(() -> {
            try {
                RecordMetadata metadata = kafkaProducer.send(record).get();
                log.info("Сообщение успешно отправлено в топик '{}', раздел {}, смещение {}",
                        metadata.topic(),
                        metadata.partition(),
                        metadata.offset());
            } catch (Exception ex) {
                log.error("Не удалось отправить сообщение: {}", ex.getMessage(), ex);
            }
        });
    }

    public void sendHubEvent(HubEventAvro hubEvent) {

        byte[] eventBytes = avroSerializer.serialize(hubTopic, hubEvent);

        long timeStamp = hubEvent.getTimestamp().toEpochMilli();

        String key = hubEvent.getHubId();

        log.info("Отправка Avro-сообщения в топик {}: Ключ='{}', Значение='{}'", hubTopic, key, hubEvent);

        ProducerRecord<String, Object> record = new ProducerRecord<>(hubTopic, null, timeStamp, key, eventBytes);
        CompletableFuture.runAsync(() -> {
            try {
                RecordMetadata metadata = kafkaProducer.send(record).get();
                log.info("Сообщение успешно отправлено в топик '{}', раздел {}, смещение {}",
                        metadata.topic(),
                        metadata.partition(),
                        metadata.offset());
            } catch (Exception ex) {
                log.error("Не удалось отправить сообщение: {}", ex.getMessage(), ex);
            }
        });
    }
}

