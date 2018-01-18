package com.paul.teventis.events;

import com.paul.teventis.events.Event;

import java.util.List;
import java.util.function.Consumer;

public interface EventStream {
    void addAll(List<Event> events);

    Event readLast();

    void write(Event e);

    void subscribe(Consumer<Event> eventConsumer);
}
