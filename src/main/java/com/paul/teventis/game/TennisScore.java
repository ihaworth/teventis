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
        }
        throw new IllegalArgumentException(score);
    }

    @Override
    public TennisScore when(final PlayerOneScored e) {
        if (playerOneScore.equals("40"))
            return new GamePlayerOne();

        if (nextScore(playerOneScore).equals("40") && playerTwoScore.equals("40"))
            return new Deuce();

        return new PreDeuce(nextScore(playerOneScore), playerTwoScore);
    }

    @Override
    public TennisScore when(final PlayerTwoScored e) {
        if (playerTwoScore.equals("40"))
            return new GamePlayerTwo();

        if (playerOneScore.equals("40") && nextScore(playerTwoScore).equals("40"))
            return new Deuce();

        return new PreDeuce(playerOneScore, nextScore(playerTwoScore));
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
