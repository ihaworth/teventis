package com.paul.teventis.set;

import com.paul.teventis.events.Event;

public class SetScoreAnnounced implements Event {
    private final String score;

    public SetScoreAnnounced(final String score) {

        this.score = score;
    }

    @Override
    public String toString() {
        return score;
    }
}
