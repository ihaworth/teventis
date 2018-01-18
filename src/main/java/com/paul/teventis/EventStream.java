package com.paul.teventis;

import com.paul.teventis.events.Event;

import java.util.List;
import java.util.function.Consumer;

interface EventStream {
    void addAll(List<Event> events);

    List<Event> readAll();
    Event readLast();

    void write(Event e);

    void subscribe(Consumer<Event> eventConsumer);
}
