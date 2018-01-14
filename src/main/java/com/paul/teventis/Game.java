package com.paul.teventis;

class Game {
    private TennisScore tennisScore = new LoveAll();

    Game(final EventStream eventStream) {

        for (Event e : eventStream.readAll()) {
            if (PlayerOneScored.class.isInstance(e)) {
                tennisScore = tennisScore.when((PlayerOneScored) e);
            } else if (PlayerTwoScored.class.isInstance(e)) {
                tennisScore = tennisScore.when((PlayerTwoScored) e);
            }

            if (someoneHasWon()) {
                break;
            }
        }

        if (GamePlayerOne.class.isInstance(tennisScore)) {
            eventStream.write((GamePlayerOne) tennisScore);
        }

        if (GamePlayerTwo.class.isInstance(tennisScore)) {
            eventStream.write((GamePlayerTwo) tennisScore);
        }

    }

    private boolean someoneHasWon() {
        return GamePlayerOne.class.isInstance(tennisScore)
                || GamePlayerTwo.class.isInstance(tennisScore);
    }

    String score() {
        return this.tennisScore.toString();
    }
}
