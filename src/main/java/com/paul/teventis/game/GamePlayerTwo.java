package com.paul.teventis.game;

import com.paul.teventis.events.Event;

public class GamePlayerTwo implements TennisScore, Event {
    @Override
    public String toString() {
        return "Game player two";
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
