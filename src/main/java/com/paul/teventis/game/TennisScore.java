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

    private final PlayerScore playerOneScore;
    private final PlayerScore playerTwoScore;

    public PreDeuce(PlayerScore playerOneScore, PlayerScore playerTwoScore) {
        this.playerOneScore = playerOneScore;
        this.playerTwoScore = playerTwoScore;
    }

    @Override
    public TennisScore when(final PlayerOneScored e) {
        return nextTennisScore(playerOneScore.next(), playerTwoScore);
    }

    @Override
    public TennisScore when(final PlayerTwoScored e) {
        return nextTennisScore(playerOneScore, playerTwoScore.next());
    }

    private TennisScore nextTennisScore(PlayerScore playerOneScore, PlayerScore playerTwoScore) {
        return new PreDeuce(playerOneScore, playerTwoScore).bestRepresentation();
    }

    private TennisScore bestRepresentation() {
        if (playerOneWon())
            return new GamePlayerOne();

        if (playerTwoWon())
            return new GamePlayerTwo();

        if (isDeuce())
            return new Deuce();

        return this;
    }

    private boolean playerOneWon() {
        return playerOneScore.equals(WON);
    }

    private boolean playerTwoWon() {
        return playerTwoScore.equals(WON);
    }

    private boolean isDeuce() {
        return playerOneScore.equals(FORTY) && playerTwoScore.equals(FORTY);
    }

    @Override
    public String toString() {
        if (playerOneScore.equals(LOVE) && playerTwoScore.equals(LOVE))
            return "love all";

        return playerOneScore.getScore() + "-" + playerTwoScore.getScore();
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
