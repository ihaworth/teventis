package com.paul.teventis.game;

import com.paul.teventis.events.Event;
import com.paul.teventis.events.EventStream;

public class Game {

    private final EventStream eventStream;
    private TennisScore tennisScore = new LoveAll();

    public Game(final EventStream eventStream) {
        this.eventStream = eventStream;
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
            eventStream.write((GamePlayerOne) tennisScore);
        }

        if (GamePlayerTwo.class.isInstance(tennisScore)) {
            eventStream.write((GamePlayerTwo) tennisScore);
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
