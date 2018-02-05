package com.paul.teventis.game;

import static com.paul.teventis.game.Score.*;

interface TennisScore {

    TennisScore when(PlayerOneScored e);
    TennisScore when(PlayerTwoScored e);
}

enum Score {

    LOVE   ("love"),
    FIFTEEN("15"  ),
    THIRTY ("30"  ),
    FORTY  ("40"  ),
    WON    ("won" );

    private String score;

    Score(String score) {
        this.score = score;
    }

    public String getScore() {
        return score;
    }

    Score next() {
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

    private final Score playerOne;
    private final Score playerTwo;

    public PreDeuce(Score playerOne, Score playerTwo) {
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

    private TennisScore nextTennisScore(Score playerOneScore, Score playerTwoScore) {
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

    private boolean bothScore(Score score) {
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
