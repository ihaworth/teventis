package com.paul.teventis.game;

interface TennisScore {

    TennisScore when(PlayerOneScored e);
    TennisScore when(PlayerTwoScored e);
}

abstract class PreDeuce implements TennisScore {

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
}

class LoveAll implements TennisScore { 
    @Override     public String toString() {         return "love all";     }
    @Override     public TennisScore when(final PlayerOneScored e) {         return new FifteenLove("15", "love");     }
    @Override     public TennisScore when(final PlayerTwoScored e) {         return new LoveFifteen("love", "15");     }
}

class FifteenLove extends PreDeuce {
    public FifteenLove(String playerOneScore, String playerTwoScore) { super(playerOneScore, playerTwoScore); }

    @Override     public String toString() {         return playerOneScore + "-" + playerTwoScore;     }
    @Override     public TennisScore when(final PlayerOneScored e) {         return new ThirtyLove(nextScore(playerOneScore), playerTwoScore);      }
    @Override     public TennisScore when(final PlayerTwoScored e) {         return new FifteenFifteen(playerOneScore, nextScore(playerTwoScore));  }
}

class FifteenFifteen extends PreDeuce {
    public FifteenFifteen(String playerOneScore, String playerTwoScore) { super(playerOneScore, playerTwoScore); }

    @Override     public String toString() {         return playerOneScore + "-" + playerTwoScore;     }
    @Override     public TennisScore when(final PlayerOneScored e) {         return new ThirtyFifteen(nextScore(playerOneScore), playerTwoScore);   }
    @Override     public TennisScore when(final PlayerTwoScored e) {         return new FifteenThirty(playerOneScore, nextScore(playerTwoScore));   }
}

class FifteenThirty extends PreDeuce {
    public FifteenThirty(String playerOneScore, String playerTwoScore) { super(playerOneScore, playerTwoScore); }

    @Override     public String toString() {         return playerOneScore + "-" + playerTwoScore;     }
    @Override     public TennisScore when(final PlayerOneScored e) {         return new ThirtyThirty(nextScore(playerOneScore), playerTwoScore);    }
    @Override     public TennisScore when(final PlayerTwoScored e) {         return new FifteenForty(playerOneScore, nextScore(playerTwoScore));    }
}

class FifteenForty extends PreDeuce {
    public FifteenForty(String playerOneScore, String playerTwoScore) { super(playerOneScore, playerTwoScore); }

    @Override     public String toString() {         return playerOneScore + "-" + playerTwoScore;     }
    @Override     public TennisScore when(final PlayerOneScored e) {         return new ThirtyForty(nextScore(playerOneScore), playerTwoScore);     }
    @Override     public TennisScore when(final PlayerTwoScored e) {         return new GamePlayerTwo();   }
}

class ThirtyForty extends PreDeuce {
    public ThirtyForty(String playerOneScore, String playerTwoScore) { super(playerOneScore, playerTwoScore); }

    @Override     public String toString() {         return playerOneScore + "-" + playerTwoScore;     }
    @Override     public TennisScore when(final PlayerOneScored e) {         return new Deuce();           }
    @Override     public TennisScore when(final PlayerTwoScored e) {         return new GamePlayerTwo();   }
}

class ThirtyThirty extends PreDeuce {
    public ThirtyThirty(String playerOneScore, String playerTwoScore) { super(playerOneScore, playerTwoScore); }

    @Override     public String toString() {         return playerOneScore + "-" + playerTwoScore;     }
    @Override     public TennisScore when(final PlayerOneScored e) {         return new FortyThirty(nextScore(playerOneScore), playerTwoScore);     }
    @Override     public TennisScore when(final PlayerTwoScored e) {         return new ThirtyForty(playerOneScore, nextScore(playerTwoScore));     }
}

class FortyThirty extends PreDeuce {
    public FortyThirty(String playerOneScore, String playerTwoScore) { super(playerOneScore, playerTwoScore); }

    @Override     public String toString() {         return playerOneScore + "-" + playerTwoScore;     }
    @Override     public TennisScore when(final PlayerOneScored e) {         return new GamePlayerOne();   }
    @Override     public TennisScore when(final PlayerTwoScored e) {         return new Deuce();           }
}

class ThirtyFifteen extends PreDeuce {
    public ThirtyFifteen(String playerOneScore, String playerTwoScore) { super(playerOneScore, playerTwoScore); }

    @Override     public String toString() {         return playerOneScore + "-" + playerTwoScore;     }
    @Override     public TennisScore when(final PlayerOneScored e) {         return new FortyFifteen(nextScore(playerOneScore), playerTwoScore);    }
    @Override     public TennisScore when(final PlayerTwoScored e) {         return new ThirtyThirty(playerOneScore, nextScore(playerTwoScore));    }
}

class FortyFifteen extends PreDeuce {
    public FortyFifteen(String playerOneScore, String playerTwoScore) { super(playerOneScore, playerTwoScore); }

    @Override     public String toString() {         return playerOneScore + "-" + playerTwoScore;     }
    @Override     public TennisScore when(final PlayerOneScored e) {         return new GamePlayerOne();   }
    @Override     public TennisScore when(final PlayerTwoScored e) {         return new FortyThirty(playerOneScore, nextScore(playerTwoScore));     }
}

class ThirtyLove extends PreDeuce {
    public ThirtyLove(String playerOneScore, String playerTwoScore) { super(playerOneScore, playerTwoScore); }

    @Override     public String toString() {         return playerOneScore + "-" + playerTwoScore;     }
    @Override     public TennisScore when(final PlayerOneScored e) {         return new FortyLove(nextScore(playerOneScore), playerTwoScore);       }
    @Override     public TennisScore when(final PlayerTwoScored e) {         return new ThirtyFifteen(playerOneScore, nextScore(playerTwoScore));   }
}

class FortyLove extends PreDeuce {
    public FortyLove(String playerOneScore, String playerTwoScore) { super(playerOneScore, playerTwoScore); }

    @Override     public String toString() {         return playerOneScore + "-" + playerTwoScore;     }
    @Override     public TennisScore when(final PlayerOneScored e) {         return new GamePlayerOne();   }
    @Override     public TennisScore when(final PlayerTwoScored e) {         return new FortyFifteen(playerOneScore, nextScore(playerTwoScore));    }
}

class LoveFifteen extends PreDeuce {
    public LoveFifteen(String playerOneScore, String playerTwoScore) { super(playerOneScore, playerTwoScore); }

    @Override     public String toString() {         return playerOneScore + "-" + playerTwoScore;     }
    @Override     public TennisScore when(final PlayerOneScored e) {         return new FifteenFifteen(nextScore(playerOneScore), playerTwoScore);  }
    @Override     public TennisScore when(final PlayerTwoScored e) {         return new LoveThirty(playerOneScore, nextScore(playerTwoScore));      }
}

class LoveThirty extends PreDeuce {
    public LoveThirty(String playerOneScore, String playerTwoScore) { super(playerOneScore, playerTwoScore); }

    @Override     public String toString() {         return playerOneScore + "-" + playerTwoScore;     }
    @Override     public TennisScore when(final PlayerOneScored e) {         return new FifteenThirty(nextScore(playerOneScore), playerTwoScore);   }
    @Override     public TennisScore when(final PlayerTwoScored e) {         return new LoveForty(playerOneScore, nextScore(playerTwoScore));       }
}

class LoveForty extends PreDeuce {
    public LoveForty(String playerOneScore, String playerTwoScore) { super(playerOneScore, playerTwoScore); }

    @Override     public String toString() {         return playerOneScore + "-" + playerTwoScore;     }
    @Override     public TennisScore when(final PlayerOneScored e) {         return new FifteenForty(nextScore(playerOneScore), playerTwoScore);    }
    @Override     public TennisScore when(final PlayerTwoScored e) {         return new GamePlayerTwo();   }
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
