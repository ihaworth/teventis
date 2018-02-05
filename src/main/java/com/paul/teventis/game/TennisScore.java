package com.paul.teventis.game;

import static com.paul.teventis.game.PlayerScore.*;

interface TennisScore {

    TennisScore when(PlayerOneScored e);
    TennisScore when(PlayerTwoScored e);
}

class PlayerScore {

    public static final PlayerScore SCORE_FIFTEEN = new PlayerScore("15");
    public static final PlayerScore SCORE_THIRTY  = new PlayerScore("30");
    public static final PlayerScore SCORE_FORTY   = new PlayerScore("40");
    public static final PlayerScore SCORE_WON     = new PlayerScore("won");
    public static final PlayerScore SCORE_LOVE    = new PlayerScore("love");

    private String score;

    public PlayerScore(String score) {
        this.score = score;
    }

    public String getScore() {
        return score;
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
        return nextTennisScore(nextScore(playerOneScore), playerTwoScore);
    }

    @Override
    public TennisScore when(final PlayerTwoScored e) {
        return nextTennisScore(playerOneScore, nextScore(playerTwoScore));
    }

    private PlayerScore nextScore(PlayerScore playerScore) {
        return nextScore(playerScore.getScore());
    }

    private PlayerScore nextScore(String score) {
        switch (score) {
            case "love": return SCORE_FIFTEEN;
            case "15": return SCORE_THIRTY;
            case "30": return SCORE_FORTY;
            case "40": return SCORE_WON;
        }
        throw new IllegalArgumentException(score);
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
        return playerOneScore.equals(SCORE_WON);
    }

    private boolean playerTwoWon() {
        return playerTwoScore.equals(SCORE_WON);
    }

    private boolean isDeuce() {
        return playerOneScore.equals(SCORE_FORTY) && playerTwoScore.equals(SCORE_FORTY);
    }

    @Override
    public String toString() {
        if (playerOneScore.equals(SCORE_LOVE) && playerTwoScore.equals(SCORE_LOVE))
            return "love all";

        return playerOneScore.getScore() + "-" + playerTwoScore.getScore();
    }
}

class LoveAll extends PreDeuce {
    public LoveAll() {
        super(SCORE_LOVE, SCORE_LOVE);
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
