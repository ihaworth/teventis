package com.paul.teventis;

import com.paul.teventis.events.Event;
import com.paul.teventis.events.EventStream;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class FakeEventStream implements EventStream {
    private final List<Event> events = new ArrayList<>();

    private Consumer<Event> eventConsumer = e -> {}; //no-op

    @Override
    public void addAll(final List<Event> events) {
        this.events.addAll(events);
    }

    @Override
    public Event readLast() {
        return events.get(events.size() - 1);
    }

    @Override
    public void write(final Event e) {
        events.add(e);
        eventConsumer.accept(e);
    }

    @Override
    public void subscribe(final Consumer<Event> eventConsumer) {
        this.eventConsumer = eventConsumer;
    }
}
