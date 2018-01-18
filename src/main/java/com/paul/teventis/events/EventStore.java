package com.paul.teventis.events;

import com.google.common.collect.ImmutableList;

import java.util.List;
import java.util.function.Consumer;

public interface EventStore {
    void addAll(List<Event> events);

    ImmutableList<Event> readAll(String streamName);

    Event readLast(String stream);

    void write(String stream, Event e);

    void subscribe(Consumer<Event> eventConsumer);
}
