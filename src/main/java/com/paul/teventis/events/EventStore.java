package com.paul.teventis.events;

import com.google.common.collect.ImmutableList;

import java.util.function.Consumer;

public interface EventStore {
    ImmutableList<Event> readAll(String streamName);

    Event readLast(String stream);

    void write(String stream, Event e);

    void writeAll(String stream, ImmutableList<Event> event);

    void subscribe(final String stream, Consumer<Event> subscription);
}
