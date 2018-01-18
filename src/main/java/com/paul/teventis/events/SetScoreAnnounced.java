package com.paul.teventis.events;

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
