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


@Slf4j
@Service
public class KafkaAvroProducer {

    private final KafkaProducer<String, GenericContainer> kafkaProducer;

    @Value("${kafka.sensor-events-topic}")
    private String sensorTopic;

    @Value("${kafka.hub-events-topic}")
    private String hubTopic;

    @Autowired
    public KafkaAvroProducer(KafkaProducer<String, GenericContainer> kafkaProducer) {
        this.kafkaProducer = kafkaProducer;
    }

    public void sendSensorEvent(SensorEventAvro sensorEvent) {
        long timeStamp = sensorEvent.getTimestamp().toEpochMilli();
        String key = sensorEvent.getHubId();

        ProducerRecord<String, GenericContainer> record =
                new ProducerRecord<>(sensorTopic, null, timeStamp, key, sensorEvent);

        log.info("📤 Отправка CLIMATE_SENSOR_EVENT: ключ='{}'", key);

        try {
            RecordMetadata metadata = kafkaProducer.send(record).get();
            log.info("Успешно отправлено в '{}': partition={}, offset={}",
                    metadata.topic(), metadata.partition(), metadata.offset());
        } catch (Exception ex) {
            log.error("Ошибка при отправке SensorEvent: {}", ex.getMessage(), ex);
        }
    }

    public void sendHubEvent(HubEventAvro hubEvent) {
        long timeStamp = hubEvent.getTimestamp().toEpochMilli();
        String key = hubEvent.getHubId();

        ProducerRecord<String, GenericContainer> record =
                new ProducerRecord<>(hubTopic, null, timeStamp, key, hubEvent);

        log.info("📤 Отправка HubEvent: ключ='{}'", key);

        try {
            RecordMetadata metadata = kafkaProducer.send(record).get();
            log.info("Успешно отправлено в '{}': partition={}, offset={}",
                    metadata.topic(), metadata.partition(), metadata.offset());
        } catch (Exception ex) {
            log.error("Ошибка при отправке HubEvent: {}", ex.getMessage(), ex);
        }
    }
}

