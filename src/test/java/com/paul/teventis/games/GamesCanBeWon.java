package com.paul.teventis.games;

import com.google.common.collect.ImmutableList;
import com.paul.teventis.*;
import com.paul.teventis.events.Event;
import com.paul.teventis.events.EventStore;
import com.paul.teventis.game.PlayerOneScored;
import com.paul.teventis.game.PlayerTwoScored;
import com.paul.teventis.game.Game;
import com.paul.teventis.game.GamePlayerOne;
import com.paul.teventis.game.GamePlayerTwo;
import org.junit.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class GamesCanBeWon {

    @Test
    public void playerOneCanWinAGame() {
        String arbitraryId = UUID.randomUUID().toString();

        final FakeEventStore inMemoryEventStore = new FakeEventStore();
        Game game = new Game(inMemoryEventStore, arbitraryId);

        ImmutableList.of(
                new PlayerOneScored(),
                new PlayerOneScored(),
                new PlayerOneScored(),
                new PlayerOneScored()
        ).forEach(game::when);

        Event e = inMemoryEventStore.readLast("set-" + arbitraryId);
        assertThat(e).isInstanceOf(GamePlayerOne.class);
    }

    @Test
    public void playerOneCanWinAGameByReplay() {
        String matchId = UUID.randomUUID().toString();

        final EventStore inMemoryEventStore = new FakeEventStore();
        inMemoryEventStore.writeAll("games-" + matchId, ImmutableList.of(
                new PlayerOneScored(),
                new PlayerOneScored(),
                new PlayerOneScored(),
                new PlayerOneScored()
        ));

        new Game(inMemoryEventStore, matchId);

        Event e = inMemoryEventStore.readLast("set-" + matchId);
        assertThat(e).isInstanceOf(GamePlayerOne.class);
    }

    @Test
    public void playerOneCanWinAGameBySubscribingToTheStream() {
        String matchId = UUID.randomUUID().toString();

        final EventStore inMemoryEventStore = new FakeEventStore();
        inMemoryEventStore.writeAll("games-" + matchId, ImmutableList.of(
                new PlayerOneScored(),
                new PlayerOneScored()
        ));

        new Game(inMemoryEventStore, matchId);
        inMemoryEventStore.write("games-"+matchId, new PlayerOneScored());
        inMemoryEventStore.write("games-"+matchId, new PlayerOneScored());

        Event e = inMemoryEventStore.readLast("set-" + matchId);
        assertThat(e).isInstanceOf(GamePlayerOne.class);
    }

    @Test
    public void playerTwoCanWinAGame() {
        String arbitraryId = UUID.randomUUID().toString();

        final FakeEventStore inMemoryEventStore = new FakeEventStore();
        Game game = new Game(inMemoryEventStore, arbitraryId);

        ImmutableList.of(
                new PlayerTwoScored(),
                new PlayerTwoScored(),
                new PlayerTwoScored(),
                new PlayerTwoScored()
        ).forEach(game::when);

        Event e = inMemoryEventStore.readLast("set-" + arbitraryId);
        assertThat(e).isInstanceOf(GamePlayerTwo.class);
    }
}
