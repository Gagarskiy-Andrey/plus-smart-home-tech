package ru.yandex.practicum.smarthometech.telemetry.collector.handler;

public interface AbstractEventHandler<P, A> {

    void process(P protoEvent);

}
