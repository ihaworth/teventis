package com.paul.teventis;

import com.paul.teventis.events.*;

class Set {

    private final EventStream eventStream;
    private int gamesPlayerOne = 0;
    private int gamesPlayerTwo = 0;

    Game game;

    public Set(final EventStream eventStream) {
        this.eventStream = eventStream;

        this.eventStream.subscribe(this::when);
    }

    void when(Event e) {
        if (SetStarted.class.isInstance(e)) {
            announceScore();
            game = new Game(eventStream);
        }
        if (GamePlayerOne.class.isInstance(e)) {
            gamesPlayerOne++;
            announceScore();
            game = new Game(eventStream);
        }
        if (GamePlayerTwo.class.isInstance(e)) {
            gamesPlayerTwo++;
            announceScore();
            game = new Game(eventStream);
        }
        if (PlayerOneScored.class.isInstance(e)
                || PlayerTwoScored.class.isInstance(e)) {
            game.when(e);
        }
    }

    private void announceScore() {
        String score = String.format("%s-%s", gamesPlayerOne, gamesPlayerTwo);
        eventStream.write(new SetScoreAnnounced(score));
    }
}
