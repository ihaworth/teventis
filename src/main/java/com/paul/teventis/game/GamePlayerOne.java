package com.paul.teventis.game;

import com.paul.teventis.events.Event;

public class GamePlayerOne implements TennisScore, Event {

    public GamePlayerOne() {
    }

    @Override
    public String toString() {
        return "Game player one";
    }

    @Override
    public TennisScore when(final PlayerOneScored e) {
        //ugh
        throw new CannotScoreAfterGameIsWon();
    }

    @Override
    public TennisScore when(final PlayerTwoScored e) {
        //ugh
        throw new CannotScoreAfterGameIsWon();
    }
}
