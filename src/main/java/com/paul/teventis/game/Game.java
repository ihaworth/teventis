package com.paul.teventis.game;

import com.paul.teventis.events.Event;
import com.paul.teventis.events.EventStore;

public class Game {

    private final EventStore eventStore;
    private final String matchId;
    private TennisScore tennisScore = new LoveAll();

    public Game(final EventStore eventStore, final String matchId) {
        this.eventStore = eventStore;
        this.matchId = matchId;

        this.eventStore.readAll("games-"+matchId).forEach(this::when);
        this.eventStore.subscribe("games-"+matchId, this::when);
    }

    public void when(Event e) {
        if (someoneHasWon()) {
            return;
        }

        if (PlayerOneScored.class.isInstance(e)) {
            tennisScore = tennisScore.when((PlayerOneScored) e);
            checkForGameWon();
        } else if (PlayerTwoScored.class.isInstance(e)) {
            tennisScore = tennisScore.when((PlayerTwoScored) e);
            checkForGameWon();
        }
    }

    private void checkForGameWon() {
        if (GamePlayerOne.class.isInstance(tennisScore)) {
            eventStore.write("set-"+ matchId, (GamePlayerOne) tennisScore);
        }

        if (GamePlayerTwo.class.isInstance(tennisScore)) {
            eventStore.write("set-"+ matchId, (GamePlayerTwo) tennisScore);
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
