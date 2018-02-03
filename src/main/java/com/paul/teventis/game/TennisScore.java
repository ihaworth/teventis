package com.paul.teventis.game;

interface TennisScore {

    TennisScore when(PlayerOneScored e);
    TennisScore when(PlayerTwoScored e);
}

class LoveAll implements TennisScore { 
    @Override     public String toString() {         return "love all";     }
    @Override     public TennisScore when(final PlayerOneScored e) {         return new FifteenLove();     }
    @Override     public TennisScore when(final PlayerTwoScored e) {         return new LoveFifteen();     }
}

class FifteenLove implements TennisScore {
    @Override     public String toString() {         return "15-love";     }
    @Override     public TennisScore when(final PlayerOneScored e) {         return new ThirtyLove();      }
    @Override     public TennisScore when(final PlayerTwoScored e) {         return new FifteenFifteen();  }
}

class FifteenFifteen implements TennisScore {
    @Override     public String toString() {         return "15-15";     }
    @Override     public TennisScore when(final PlayerOneScored e) {         return new ThirtyFifteen();   }
    @Override     public TennisScore when(final PlayerTwoScored e) {         return new FifteenThirty();   }
}

class FifteenThirty implements TennisScore {
    @Override     public String toString() {         return "15-30";     }
    @Override     public TennisScore when(final PlayerOneScored e) {         return new ThirtyThirty();    }
    @Override     public TennisScore when(final PlayerTwoScored e) {         return new FifteenForty();    }
}

class FifteenForty implements TennisScore {
    @Override     public String toString() {         return "15-40";     }
    @Override     public TennisScore when(final PlayerOneScored e) {         return new ThirtyForty();     }
    @Override     public TennisScore when(final PlayerTwoScored e) {         return new GamePlayerTwo();   }
}

class ThirtyForty implements TennisScore {
    @Override     public String toString() {         return "30-40";     }
    @Override     public TennisScore when(final PlayerOneScored e) {         return new Deuce();           }
    @Override     public TennisScore when(final PlayerTwoScored e) {         return new GamePlayerTwo();   }
}

class ThirtyThirty implements TennisScore {
    @Override     public String toString() {         return "30-30";     }
    @Override     public TennisScore when(final PlayerOneScored e) {         return new FortyThirty();     }
    @Override     public TennisScore when(final PlayerTwoScored e) {         return new ThirtyForty();     }
}

class FortyThirty implements TennisScore {
    @Override     public String toString() {         return "40-30";     }
    @Override     public TennisScore when(final PlayerOneScored e) {         return new GamePlayerOne();   }
    @Override     public TennisScore when(final PlayerTwoScored e) {         return new Deuce();           }
}

class ThirtyFifteen implements TennisScore { 
    @Override     public String toString() {         return "30-15";     }
    @Override     public TennisScore when(final PlayerOneScored e) {         return new FortyFifteen();    }
    @Override     public TennisScore when(final PlayerTwoScored e) {         return new ThirtyThirty();    }
}

class FortyFifteen implements TennisScore {
    @Override     public String toString() {         return "40-15";     }
    @Override     public TennisScore when(final PlayerOneScored e) {         return new GamePlayerOne();   }
    @Override     public TennisScore when(final PlayerTwoScored e) {         return new FortyThirty();     }
}

class ThirtyLove implements TennisScore { 
    @Override     public String toString() {         return "30-love";     }
    @Override     public TennisScore when(final PlayerOneScored e) {         return new FortyLove();       }
    @Override     public TennisScore when(final PlayerTwoScored e) {         return new ThirtyFifteen();   }
}

class FortyLove implements TennisScore {
    @Override     public String toString() {         return "40-love";     }
    @Override     public TennisScore when(final PlayerOneScored e) {         return new GamePlayerOne();   }
    @Override     public TennisScore when(final PlayerTwoScored e) {         return new FortyFifteen();    }
}

class LoveFifteen implements TennisScore { 
    @Override     public TennisScore when(final PlayerOneScored e) {         return new FifteenFifteen();  }
    @Override     public TennisScore when(final PlayerTwoScored e) {         return new LoveThirty();      }
    @Override     public String toString() {         return "love-15";     }
}

class LoveThirty implements TennisScore {
    @Override     public String toString() {         return "love-30";     }
    @Override     public TennisScore when(final PlayerOneScored e) {         return new FifteenThirty();   }
    @Override     public TennisScore when(final PlayerTwoScored e) {         return new LoveForty();       }
}

class LoveForty implements TennisScore {
    @Override     public String toString() {         return "love-40";     }
    @Override     public TennisScore when(final PlayerOneScored e) {         return new FifteenForty();    }
    @Override     public TennisScore when(final PlayerTwoScored e) {         return new GamePlayerTwo();   }
}

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

