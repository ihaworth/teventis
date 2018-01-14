package com.paul.teventis;

class Game {
    private TennisScore tennisScore = new LoveAll();

    Game(final EventStream eventStream) {

        eventStream.readAll().forEach(e -> {
            if (PlayerOneScored.class.isInstance(e)) {
                tennisScore = tennisScore.when((PlayerOneScored) e);
            }
            else if (PlayerTwoScored.class.isInstance(e)) {
                tennisScore = tennisScore.when((PlayerTwoScored) e);
            }
        });

        if (GamePlayerOne.class.isInstance(tennisScore)) {
            eventStream.write((GamePlayerOne) tennisScore);
        }

        if (GamePlayerTwo.class.isInstance(tennisScore)) {
            eventStream.write((GamePlayerTwo) tennisScore);
        }

    }

    String score() {
        return this.tennisScore.toString();
    }
}
