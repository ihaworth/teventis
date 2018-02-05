package com.paul.teventis.game;

import static com.paul.teventis.game.PlayerScore.*;

interface TennisScore {

    TennisScore when(PlayerOneScored e);
    TennisScore when(PlayerTwoScored e);
}

enum PlayerScore {

    FIFTEEN("15"  ),
    THIRTY ("30"  ),
    FORTY  ("40"  ),
    WON    ("won" ),
    LOVE   ("love");

    private String score;

    PlayerScore(String score) {
        this.score = score;
    }

    public String getScore() {
        return score;
    }

    PlayerScore next() {
        switch (this) {
            case LOVE   : return FIFTEEN;
            case FIFTEEN: return THIRTY;
            case THIRTY : return FORTY;
            case FORTY  : return WON;
        }
        throw new IllegalArgumentException(getScore());
    }
}

class PreDeuce implements TennisScore {

    private final PlayerScore playerOne;
    private final PlayerScore playerTwo;

    public PreDeuce(PlayerScore playerOne, PlayerScore playerTwo) {
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;
    }

    @Override
    public TennisScore when(final PlayerOneScored e) {
        return nextTennisScore(playerOne.next(), playerTwo);
    }

    @Override
    public TennisScore when(final PlayerTwoScored e) {
        return nextTennisScore(playerOne, playerTwo.next());
    }

    private TennisScore nextTennisScore(PlayerScore playerOneScore, PlayerScore playerTwoScore) {
        return new PreDeuce(playerOneScore, playerTwoScore).bestRepresentation();
    }

    private TennisScore bestRepresentation() {
        if (playerOne == WON)
            return new GamePlayerOne();

        if (playerTwo == WON)
            return new GamePlayerTwo();

        if (bothScore(FORTY))
            return new Deuce();

        return this;
    }

    @Override
    public String toString() {
        if (bothScore(LOVE))
            return "love all";

        return playerOne.getScore() + "-" + playerTwo.getScore();
    }

    private boolean bothScore(PlayerScore score) {
        return playerOne == score &&
               playerTwo == score;
    }
}

class LoveAll extends PreDeuce {
    public LoveAll() {
        super(LOVE, LOVE);
    }
}


/* DEUCE */


class Deuce implements TennisScore {
    @Override   public String toString() {         return "deuce";     }
    @Override   public TennisScore when(final PlayerOneScored e) {           return new AdvantagePlayerOne();     }
    @Override   public TennisScore when(final PlayerTwoScored e) {           return new AdvantagePlayerTwo();     }
}

class AdvantagePlayerOne implements TennisScore {
    @Override   public String toString() {         return "advantage player one";     }
    @Override   public TennisScore when(final PlayerOneScored e) {           return new GamePlayerOne();   }
    @Override   public TennisScore when(final PlayerTwoScored e) {           return new Deuce();           }
}

class AdvantagePlayerTwo implements TennisScore {
    @Override   public String toString() {         return "advantage player two";     }
    @Override   public TennisScore when(final PlayerTwoScored e) {           return new GamePlayerTwo();   }
    @Override   public TennisScore when(final PlayerOneScored e) {           return new Deuce();           }
}
