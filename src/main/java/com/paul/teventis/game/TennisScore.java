package com.paul.teventis.game;

interface TennisScore {

    TennisScore when(PlayerOneScored e);
    TennisScore when(PlayerTwoScored e);
}

class PreDeuce implements TennisScore {

            static final String LOVE    = "love";
    private static final String FIFTEEN = "15";
    private static final String THIRTY  = "30";
    private static final String FORTY   = "40";
    private static final String WON     = "won";

    final String playerOneScore;
    final String playerTwoScore;

    public PreDeuce(String playerOneScore, String playerTwoScore) {
        this.playerOneScore = playerOneScore;
        this.playerTwoScore = playerTwoScore;
    }

    @Override
    public TennisScore when(final PlayerOneScored e) {
        return nextTennisScore(nextScore(playerOneScore), playerTwoScore);
    }

    @Override
    public TennisScore when(final PlayerTwoScored e) {
        return nextTennisScore(playerOneScore, nextScore(playerTwoScore));
    }

    public String nextScore(String score) {
        switch (score) {
            case    LOVE: return FIFTEEN;
            case FIFTEEN: return THIRTY;
            case  THIRTY: return FORTY;
            case   FORTY: return WON;
        }
        throw new IllegalArgumentException(score);
    }

    private TennisScore nextTennisScore(String nextPlayerOneScore, String nextPlayerTwoScore) {
        return new PreDeuce(nextPlayerOneScore, nextPlayerTwoScore).bestRepresentation();
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

        return playerOneScore + "-" + playerTwoScore;
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
