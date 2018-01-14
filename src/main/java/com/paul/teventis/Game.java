package com.paul.teventis;

class Game {
    private TennisScore tennisScore = new LoveAll();

    Game(final EventStream eventStream) {

        eventStream.readAll().forEach(e -> {
            if (PlayerOneScored.class.isInstance(e)) {
                tennisScore = tennisScore.when((PlayerOneScored) e);
                return;
            }
            if (PlayerTwoScored.class.isInstance(e)) {
                tennisScore = tennisScore.when((PlayerTwoScored) e);
            }
        });

    }

    String score() {
        return this.tennisScore.toString();
    }
}
