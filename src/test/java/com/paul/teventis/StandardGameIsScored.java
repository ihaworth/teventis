package com.paul.teventis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(Parameterized.class)
public class StandardGameIsScored {
    private static final List<Event> threePointsEach = Arrays.asList(
            new PlayerOneScored(), new PlayerOneScored(), new PlayerOneScored(),
            new PlayerTwoScored(), new PlayerTwoScored(), new PlayerTwoScored());

    private List<Event> playersScoringEvents;
    private String expectedScore;

    // love -> 15 -> 30 -> 40
    // when no players have scored... love-all
    // when player one has scored... 15-love
    // when player two has scored... love-15
    // when both have scored three times... deuce

    @Parameterized.Parameters(name = "Standard Game: {index}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                        {"love all", Collections.emptyList()},
                        {"15-love", Collections.singletonList(new PlayerOneScored())},
                        {"30-love", Arrays.asList(new PlayerOneScored(), new PlayerOneScored())},
                        {"40-love", Arrays.asList(new PlayerOneScored(), new PlayerOneScored(), new PlayerOneScored())},
                        {"Game player one", Arrays.asList(new PlayerOneScored(), new PlayerOneScored(), new PlayerOneScored(), new PlayerOneScored())},
                        {"love-15", Collections.singletonList(new PlayerTwoScored())},
                        {"love-30", Arrays.asList(new PlayerTwoScored(), new PlayerTwoScored())},
                        {"love-40", Arrays.asList(new PlayerTwoScored(), new PlayerTwoScored(), new PlayerTwoScored())},
                        {"Game player two", Arrays.asList(new PlayerTwoScored(), new PlayerTwoScored(), new PlayerTwoScored(), new PlayerTwoScored())},
                        {"15-15", Arrays.asList(new PlayerOneScored(), new PlayerTwoScored())},
                        {"30-30", Arrays.asList(new PlayerOneScored(), new PlayerTwoScored(), new PlayerOneScored(), new PlayerTwoScored())},
                        {"deuce", threePointsEach},
                        {"advantage player one", concat(threePointsEach, Collections.singletonList(new PlayerOneScored()))},
                        {"advantage player two", concat(threePointsEach, Collections.singletonList(new PlayerTwoScored()))},
                        {"Game player one", concat(threePointsEach, Arrays.asList(new PlayerOneScored(), new PlayerOneScored()))},
                        {"deuce", concat(threePointsEach, Arrays.asList(new PlayerOneScored(), new PlayerTwoScored()))},
                        {"Game player two", concat(threePointsEach, Arrays.asList(new PlayerOneScored(), new PlayerTwoScored(), new PlayerTwoScored(), new PlayerTwoScored()))},
                // game stops processing events when somebody wins
                        {"Game player one", Arrays.asList(
                                new PlayerOneScored(),
                                new PlayerOneScored(),
                                new PlayerOneScored(),
                                new PlayerOneScored(), //game is won
                                new PlayerOneScored(),
                                new PlayerOneScored(),
                                new PlayerOneScored(),
                                new PlayerOneScored(),
                                new PlayerOneScored(),
                                new PlayerOneScored())},
                }
        );
    }

    private static Object concat(final List<Event> a, final List<Event> b) {
        return Stream.of(a, b).flatMap(List::stream).collect(Collectors.toList());
    }

    public StandardGameIsScored(final String expectedScore, final List<Event> playersScoringEvents) {
        //parameters are injected here...
        this.playersScoringEvents = playersScoringEvents;
        this.expectedScore = expectedScore;
    }

    @Test
    public void PlayersWinningPointsChangesTheGamesScore() {
        final FakeEventStream inMemoryEventStream = new FakeEventStream();

        final Game game = new Game(inMemoryEventStream);
        playersScoringEvents.forEach(game::when);
        final String score = game.score();
        assertThat(score).isEqualTo(expectedScore);
    }
}


