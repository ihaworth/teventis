package com.paul.teventis;

import java.util.ArrayList;
import java.util.List;

public class FakeEventStream implements EventStream {
    private final List<Event> events = new ArrayList<>();

    @Override
    public void addAll(final List<Event> events) {
        this.events.addAll(events);
    }

    @Override
    public List<Event> readAll() {
        return events;
    }

    @Override
    public Event readLast() {
        return events.get(events.size() - 1);
    }

    @Override
    public void write(final Event e) {
        events.add(e);
    }
}
