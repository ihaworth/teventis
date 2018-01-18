package com.paul.teventis.set;

import com.paul.teventis.events.*;
import com.paul.teventis.game.*;

public class Set {

    private final EventStore eventStore;
    private final String matchId;
    private final String setId;
    private int gamesPlayerOne = 0;
    private int gamesPlayerTwo = 0;

    Game game;

    public Set(final EventStore eventStore, String matchId, final String setId) {
        this.eventStore = eventStore;
        this.matchId = matchId;
        this.setId = setId;

        this.eventStore.readAll("set-" + setId).forEach(this::when);

        this.eventStore.subscribe("games-" + matchId, this::when);
    }

    public void when(Event e) {
        if (SetStarted.class.isInstance(e)) {
            announceScore();
            game = new Game(eventStore, setId);
        }
        if (GamePlayerOne.class.isInstance(e)) {
            gamesPlayerOne++;
            announceScore();
            game = new Game(eventStore, setId);
        }
        if (GamePlayerTwo.class.isInstance(e)) {
            gamesPlayerTwo++;
            announceScore();
            game = new Game(eventStore, setId);
        }
        if (PlayerOneScored.class.isInstance(e)
                || PlayerTwoScored.class.isInstance(e)) {
            game.when(e);
        }
    }

    private void announceScore() {
        String score = String.format("%s-%s", gamesPlayerOne, gamesPlayerTwo);
        eventStore.write("match-" + matchId, new SetScoreAnnounced(score));
    }
}
