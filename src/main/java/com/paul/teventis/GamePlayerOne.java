package com.paul.teventis;

import com.paul.teventis.events.Event;
import com.paul.teventis.events.PlayerOneScored;
import com.paul.teventis.events.PlayerTwoScored;

public class GamePlayerOne implements TennisScore, Event {
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
