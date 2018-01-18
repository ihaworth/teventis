package com.paul.teventis.set;

import com.paul.teventis.events.Event;

public class SetPlayerOne implements Event {
    private final String score;

    public SetPlayerOne(final String score) {

        this.score = score;
    }
}

