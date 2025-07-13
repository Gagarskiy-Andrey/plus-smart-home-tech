package ru.yandex.practicum.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.dto.HubEvent;
import ru.yandex.practicum.dto.SensorEvent;
import ru.yandex.practicum.services.EventService;

@Slf4j
@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
public class EventController {
    private final EventService eventService;

    @PostMapping("/sensors")
    public ResponseEntity<Void> collectSensorEvent(@Valid @RequestBody SensorEvent sensorEvent) {
        log.info("Обработка события от датчика: {}", sensorEvent);
        eventService.preparationAndSendSensorEvent(sensorEvent);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/hubs")
    public ResponseEntity<Void> collectHubEvent(@Valid @RequestBody HubEvent hubEvent) {
        log.info("Обработка события от хаба: {}", hubEvent);
        eventService.preparationAndSendHubEvent(hubEvent);
        return ResponseEntity.ok().build();
    }
}
