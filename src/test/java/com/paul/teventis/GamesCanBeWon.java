package com.paul.teventis;

import com.google.common.collect.ImmutableList;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class GamesCanBeWon {

    @Test
    public void playerOneCanWinAGame() {
        final FakeEventStream inMemoryEventStream = new FakeEventStream();
        Game game = new Game(inMemoryEventStream);

        ImmutableList.of(
                new PlayerOneScored(),
                new PlayerOneScored(),
                new PlayerOneScored(),
                new PlayerOneScored()
        ).forEach(game::when);

        Event e = inMemoryEventStream.readLast();
        assertThat(e).isInstanceOf(GamePlayerOne.class);
    }

    @Test
    public void playerTwoCanWinAGame() {
        final FakeEventStream inMemoryEventStream = new FakeEventStream();
        Game game = new Game(inMemoryEventStream);

        ImmutableList.of(
                new PlayerTwoScored(),
                new PlayerTwoScored(),
                new PlayerTwoScored(),
                new PlayerTwoScored()
        ).forEach(game::when);

        Event e = inMemoryEventStream.readLast();
        assertThat(e).isInstanceOf(GamePlayerTwo.class);
    }
}
