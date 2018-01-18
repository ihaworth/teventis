package com.paul.teventis.set;

import com.paul.teventis.events.Event;

public class SetPlayerTwo implements Event {
    private final String score;

    public SetPlayerTwo(final String score) {

        this.score = score;
    }
}
