package ru.yandex.practicum.handler;

public interface AbstractEventHandler<P, A> {

    void process(P protoEvent);

}
