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
}

class LoveAll implements TennisScore { 
    @Override     public String toString() {         return "love all";     }
    @Override     public TennisScore when(final PlayerOneScored e) {         return new FifteenLove("15", "love");     }
    @Override     public TennisScore when(final PlayerTwoScored e) {         return new LoveFifteen("love", "15");     }
}

class FifteenLove extends PreDeuce {
    public FifteenLove(String playerOneScore, String playerTwoScore) { super(playerOneScore, playerTwoScore); }

    @Override     public String toString() {         return playerOneScore + "-" + playerTwoScore;     }
    @Override     public TennisScore when(final PlayerOneScored e) {         return new ThirtyLove("30", "love");      }
    @Override     public TennisScore when(final PlayerTwoScored e) {         return new FifteenFifteen("15", "15");  }
}

class FifteenFifteen extends PreDeuce {
    public FifteenFifteen(String playerOneScore, String playerTwoScore) { super(playerOneScore, playerTwoScore); }

    @Override     public String toString() {         return playerOneScore + "-" + playerTwoScore;     }
    @Override     public TennisScore when(final PlayerOneScored e) {         return new ThirtyFifteen("30", "15");   }
    @Override     public TennisScore when(final PlayerTwoScored e) {         return new FifteenThirty("15", "30");   }
}

class FifteenThirty extends PreDeuce {
    public FifteenThirty(String playerOneScore, String playerTwoScore) { super(playerOneScore, playerTwoScore); }

    @Override     public String toString() {         return playerOneScore + "-" + playerTwoScore;     }
    @Override     public TennisScore when(final PlayerOneScored e) {         return new ThirtyThirty("30", "30");    }
    @Override     public TennisScore when(final PlayerTwoScored e) {         return new FifteenForty("15", "40");    }
}

class FifteenForty extends PreDeuce {
    public FifteenForty(String playerOneScore, String playerTwoScore) { super(playerOneScore, playerTwoScore); }

    @Override     public String toString() {         return playerOneScore + "-" + playerTwoScore;     }
    @Override     public TennisScore when(final PlayerOneScored e) {         return new ThirtyForty("30", "40");     }
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
    @Override     public TennisScore when(final PlayerOneScored e) {         return new FortyThirty("40", "30");     }
    @Override     public TennisScore when(final PlayerTwoScored e) {         return new ThirtyForty("30", "40");     }
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
    @Override     public TennisScore when(final PlayerOneScored e) {         return new FortyFifteen("40", "15");    }
    @Override     public TennisScore when(final PlayerTwoScored e) {         return new ThirtyThirty("30", "30");    }
}

class FortyFifteen extends PreDeuce {
    public FortyFifteen(String playerOneScore, String playerTwoScore) { super(playerOneScore, playerTwoScore); }

    @Override     public String toString() {         return playerOneScore + "-" + playerTwoScore;     }
    @Override     public TennisScore when(final PlayerOneScored e) {         return new GamePlayerOne();   }
    @Override     public TennisScore when(final PlayerTwoScored e) {         return new FortyThirty("40", "30");     }
}

class ThirtyLove extends PreDeuce {
    public ThirtyLove(String playerOneScore, String playerTwoScore) { super(playerOneScore, playerTwoScore); }

    @Override     public String toString() {         return playerOneScore + "-" + playerTwoScore;     }
    @Override     public TennisScore when(final PlayerOneScored e) {         return new FortyLove("40", "love");       }
    @Override     public TennisScore when(final PlayerTwoScored e) {         return new ThirtyFifteen("30", "15");   }
}

class FortyLove extends PreDeuce {
    public FortyLove(String playerOneScore, String playerTwoScore) { super(playerOneScore, playerTwoScore); }

    @Override     public String toString() {         return playerOneScore + "-" + playerTwoScore;     }
    @Override     public TennisScore when(final PlayerOneScored e) {         return new GamePlayerOne();   }
    @Override     public TennisScore when(final PlayerTwoScored e) {         return new FortyFifteen("40", "15");    }
}

class LoveFifteen extends PreDeuce {
    public LoveFifteen(String playerOneScore, String playerTwoScore) { super(playerOneScore, playerTwoScore); }

    @Override     public String toString() {         return playerOneScore + "-" + playerTwoScore;     }
    @Override     public TennisScore when(final PlayerOneScored e) {         return new FifteenFifteen("15", "15");  }
    @Override     public TennisScore when(final PlayerTwoScored e) {         return new LoveThirty("love", "30");      }
}

class LoveThirty extends PreDeuce {
    public LoveThirty(String playerOneScore, String playerTwoScore) { super(playerOneScore, playerTwoScore); }

    @Override     public String toString() {         return playerOneScore + "-" + playerTwoScore;     }
    @Override     public TennisScore when(final PlayerOneScored e) {         return new FifteenThirty("15", "30");   }
    @Override     public TennisScore when(final PlayerTwoScored e) {         return new LoveForty("love", "40");       }
}

class LoveForty extends PreDeuce {
    public LoveForty(String playerOneScore, String playerTwoScore) { super(playerOneScore, playerTwoScore); }

    @Override     public String toString() {         return playerOneScore + "-" + playerTwoScore;     }
    @Override     public TennisScore when(final PlayerOneScored e) {         return new FifteenForty("15", "40");    }
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
