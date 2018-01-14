package com.paul.teventis;

import java.util.List;

interface EventStream {
    void addAll(List<Event> events);
    List<Event> readAll();
}
