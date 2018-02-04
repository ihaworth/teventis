package com.paul.teventis.game;

interface TennisScore {

    TennisScore when(PlayerOneScored e);
    TennisScore when(PlayerTwoScored e);
}

class PreDeuce implements TennisScore {

    final String playerOneScore;
    final String playerTwoScore;

    public PreDeuce(String playerOneScore, String playerTwoScore) {
        this.playerOneScore = playerOneScore;
        this.playerTwoScore = playerTwoScore;
    }

    public String nextScore(String score) {
        switch (score) {
            case "love": return "15";
            case   "15": return "30";
            case   "30": return "40";
            case   "40": return "won";
        }
        throw new IllegalArgumentException(score);
    }

    @Override
    public TennisScore when(final PlayerOneScored e) {

        String playerOneScore = nextScore(this.playerOneScore);
        String playerTwoScore = this.playerTwoScore;

        PreDeuce nextScore = new PreDeuce(playerOneScore, playerTwoScore);

        if (nextScore.playerOneWon())
            return new GamePlayerOne();

        if (nextScore.playerTwoWon())
            return new GamePlayerTwo();

        if (nextScore.isDeuce())
            return new Deuce();

        return nextScore;
    }

    @Override
    public TennisScore when(final PlayerTwoScored e) {

        String playerOneScore = this.playerOneScore;
        String playerTwoScore = nextScore(this.playerTwoScore);

        PreDeuce nextScore = new PreDeuce(playerOneScore, playerTwoScore);

        if (nextScore.playerOneWon())
            return new GamePlayerOne();

        if (nextScore.playerTwoWon())
            return new GamePlayerTwo();

        if (nextScore.isDeuce())
            return new Deuce();

        return nextScore;
    }

    private boolean playerOneWon() {
        return playerOneScore.equals("won");
    }

    private boolean playerTwoWon() {
        return playerTwoScore.equals("won");
    }

    private boolean isDeuce() {
        return this.playerOneScore.equals("40") && this.playerTwoScore.equals("40");
    }

    @Override
    public String toString() {
        if (playerOneScore.equals("love") && playerTwoScore.equals("love"))
            return "love all";

        return playerOneScore + "-" + playerTwoScore;
    }
}

class LoveAll extends PreDeuce {
    public LoveAll() {
        super("love", "love");
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
