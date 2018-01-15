package com.paul.teventis;

import com.paul.teventis.events.Event;

import java.util.List;

interface EventStream {
    void addAll(List<Event> events);

    List<Event> readAll();
    Event readLast();

    void write(Event e);
}
