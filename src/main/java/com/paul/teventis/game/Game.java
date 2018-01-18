package com.paul.teventis.game;

import com.paul.teventis.events.Event;
import com.paul.teventis.events.EventStore;

public class Game {

    private final EventStore eventStore;
    private final String setId;
    private TennisScore tennisScore = new LoveAll();

    public Game(final EventStore eventStore, final String setId) {
        this.eventStore = eventStore;
        this.setId = setId;
    }

    public void when(Event e) {
        if (someoneHasWon()) {
            return;
        }

        if (PlayerOneScored.class.isInstance(e)) {
            tennisScore = tennisScore.when((PlayerOneScored) e);
        } else if (PlayerTwoScored.class.isInstance(e)) {
            tennisScore = tennisScore.when((PlayerTwoScored) e);
        }

        if (GamePlayerOne.class.isInstance(tennisScore)) {
            eventStore.write("set-"+ setId, (GamePlayerOne) tennisScore);
        }

        if (GamePlayerTwo.class.isInstance(tennisScore)) {
            eventStore.write("set-"+ setId, (GamePlayerTwo) tennisScore);
        }
    }

    private boolean someoneHasWon() {
        return GamePlayerOne.class.isInstance(tennisScore)
                || GamePlayerTwo.class.isInstance(tennisScore);
    }

    public String score() {
        return this.tennisScore.toString();
    }
}
