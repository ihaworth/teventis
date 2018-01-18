package com.paul.teventis;

import com.google.common.collect.ImmutableList;
import com.paul.teventis.events.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(Parameterized.class)
public class SetsCanBeScored {

    private final String setScoreAnnounced;
    private final List<Event> pointsScored;

    @Parameterized.Parameters(name = "Set Scoring: {index}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                        {"0-0", ImmutableList.of(new SetStarted())},
                        {"1-0", playerOneWinOneGame()},
                        {"0-1", playerTwoWinOneGame()},
                        {"2-0", playerOneWinTwoGames()},
                {"0-2", playerTwoWinTwoGames()}
                }
        );
    }

    private static Object playerOneWinOneGame() {
        return ImmutableList.of(
                new SetStarted(),
                new PlayerOneScored(),
                new PlayerOneScored(),
                new PlayerOneScored(),
                new PlayerOneScored()
        );
    }

    private static Object playerTwoWinOneGame() {
        return ImmutableList.of(
                new SetStarted(),
                new PlayerTwoScored(),
                new PlayerTwoScored(),
                new PlayerTwoScored(),
                new PlayerTwoScored()
        );
    }

    private static Object playerOneWinTwoGames() {
        return ImmutableList.of(
                new SetStarted(),
                new PlayerOneScored(),
                new PlayerOneScored(),
                new PlayerOneScored(),
                new PlayerOneScored(),
                new PlayerOneScored(),
                new PlayerOneScored(),
                new PlayerOneScored(),
                new PlayerOneScored()
        );
    }

    private static Object playerTwoWinTwoGames() {
        return ImmutableList.of(
                new SetStarted(),
                new PlayerTwoScored(),
                new PlayerTwoScored(),
                new PlayerTwoScored(),
                new PlayerTwoScored(),
                new PlayerTwoScored(),
                new PlayerTwoScored(),
                new PlayerTwoScored(),
                new PlayerTwoScored()
        );
    }

    public SetsCanBeScored(String setScoreAnnounced, List<Event> pointsScored) {
        this.setScoreAnnounced = setScoreAnnounced;
        this.pointsScored = pointsScored;
    }

    @Test
    public void scoreIsAnnounced() {
        final FakeEventStream inMemoryEventStream = new FakeEventStream();
        inMemoryEventStream.addAll(pointsScored);

        Set set = new Set(inMemoryEventStream);
        pointsScored.forEach(set::when);

        final SetScoreAnnounced scoreAnnounced = (SetScoreAnnounced) inMemoryEventStream.readLast();

        assertThat(scoreAnnounced.toString()).isEqualTo(setScoreAnnounced);
    }


}

