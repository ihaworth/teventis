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
        return playerOneScore + "-" + playerTwoScore;
    }
}

class LoveAll implements TennisScore { 
    @Override     public String toString() {         return "love all";     }
    @Override     public TennisScore when(final PlayerOneScored e) {         return new FifteenLove("15", "love");     }
    @Override     public TennisScore when(final PlayerTwoScored e) {         return new LoveFifteen("love", "15");     }
}

class FifteenLove extends PreDeuce {
    public FifteenLove(String playerOneScore, String playerTwoScore) { super(playerOneScore, playerTwoScore); }
}

class FifteenFifteen extends PreDeuce {
    public FifteenFifteen(String playerOneScore, String playerTwoScore) { super(playerOneScore, playerTwoScore); }
}

class FifteenThirty extends PreDeuce {
    public FifteenThirty(String playerOneScore, String playerTwoScore) { super(playerOneScore, playerTwoScore); }
}

class FifteenForty extends PreDeuce {
    public FifteenForty(String playerOneScore, String playerTwoScore) { super(playerOneScore, playerTwoScore); }
}

class ThirtyForty extends PreDeuce {
    public ThirtyForty(String playerOneScore, String playerTwoScore) { super(playerOneScore, playerTwoScore); }
}

class ThirtyThirty extends PreDeuce {
    public ThirtyThirty(String playerOneScore, String playerTwoScore) { super(playerOneScore, playerTwoScore); }
}

class FortyThirty extends PreDeuce {
    public FortyThirty(String playerOneScore, String playerTwoScore) { super(playerOneScore, playerTwoScore); }
}

class ThirtyFifteen extends PreDeuce {
    public ThirtyFifteen(String playerOneScore, String playerTwoScore) { super(playerOneScore, playerTwoScore); }
}

class FortyFifteen extends PreDeuce {
    public FortyFifteen(String playerOneScore, String playerTwoScore) { super(playerOneScore, playerTwoScore); }
}

class ThirtyLove extends PreDeuce {
    public ThirtyLove(String playerOneScore, String playerTwoScore) { super(playerOneScore, playerTwoScore); }
}

class FortyLove extends PreDeuce {
    public FortyLove(String playerOneScore, String playerTwoScore) { super(playerOneScore, playerTwoScore); }
}

class LoveFifteen extends PreDeuce {
    public LoveFifteen(String playerOneScore, String playerTwoScore) { super(playerOneScore, playerTwoScore); }
}

class LoveThirty extends PreDeuce {
    public LoveThirty(String playerOneScore, String playerTwoScore) { super(playerOneScore, playerTwoScore); }
}

class LoveForty extends PreDeuce {
    public LoveForty(String playerOneScore, String playerTwoScore) { super(playerOneScore, playerTwoScore); }
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
