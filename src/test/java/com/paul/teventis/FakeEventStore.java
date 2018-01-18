package com.paul.teventis;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.paul.teventis.events.Event;
import com.paul.teventis.events.EventStore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class FakeEventStore implements EventStore {
    private final Map<String, List<Event>> eventStreams = new HashMap<>();

    private final List<Event> events = new ArrayList<>();

    private Consumer<Event> eventConsumer = e -> {}; //no-op

    @Override
    public void addAll(final List<Event> events) {
        this.events.addAll(events);
    }

    @Override
    public ImmutableList<Event> readAll(final String streamName) {
        final List<Event> events = eventStreams.getOrDefault(streamName, new ArrayList<>());
        return ImmutableList.copyOf(events);
    }

    @Override
    public Event readLast(String streamName) {
        return Iterables.getLast(readAll(streamName));
    }

    @Override
    public void write(final String stream, final Event e) {
        final List<Event> events = eventStreams.getOrDefault(stream, new ArrayList<>());
        events.add(e);
        eventStreams.put(stream, events);
    }

    @Override
    public void subscribe(final Consumer<Event> eventConsumer) {
        this.eventConsumer = eventConsumer;
    }
}
