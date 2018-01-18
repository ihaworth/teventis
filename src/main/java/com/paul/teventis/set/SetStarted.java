package com.paul.teventis.set;

import com.paul.teventis.events.Event;

public class SetStarted implements Event {
    public final String setId;

    public SetStarted(final String setId) {
        this.setId = setId;
    }
}
