package com.paul.teventis.games;

import com.google.common.collect.ImmutableList;
import com.paul.teventis.*;
import com.paul.teventis.events.Event;
import com.paul.teventis.game.PlayerOneScored;
import com.paul.teventis.game.PlayerTwoScored;
import com.paul.teventis.game.Game;
import com.paul.teventis.game.GamePlayerOne;
import com.paul.teventis.game.GamePlayerTwo;
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