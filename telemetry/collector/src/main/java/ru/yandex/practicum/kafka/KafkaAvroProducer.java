package ru.yandex.practicum.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.avro.specific.SpecificRecordBase;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.kafka.telemetry.event.HubEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.SensorEventAvro;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
public class KafkaAvroProducer {

    private final KafkaProducer<String, SpecificRecordBase> kafkaProducer;

    @Value("${kafka.sensor-events-topic}")
    private String sensorTopic;

    @Value("${kafka.hub-events-topic}")
    private String hubTopic;

    @Autowired
    public KafkaAvroProducer(KafkaProducer<String, SpecificRecordBase> kafkaProducer) {
        this.kafkaProducer = kafkaProducer;
    }

    public void sendSensorEvent(SensorEventAvro sensorEvent) {

        long timeStamp = sensorEvent.getTimestamp().toEpochMilli();

        String key = sensorEvent.getHubId();

        log.info("Отправка Avro-сообщения в топик {}: Ключ='{}', Значение='{}'", sensorTopic, key, sensorEvent);

        ProducerRecord<String, SpecificRecordBase> record = new ProducerRecord<>(sensorTopic, null, timeStamp, key, sensorEvent);
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

        long timeStamp = hubEvent.getTimestamp().toEpochMilli();

        String key = hubEvent.getHubId();

        log.info("Отправка Avro-сообщения в топик {}: Ключ='{}', Значение='{}'", hubTopic, key, hubEvent);

        ProducerRecord<String, SpecificRecordBase> record = new ProducerRecord<>(hubTopic, null, timeStamp, key, hubEvent);
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

