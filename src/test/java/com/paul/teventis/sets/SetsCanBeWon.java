package com.paul.teventis.sets;

import com.google.common.collect.ImmutableList;
import com.paul.teventis.FakeEventStore;
import com.paul.teventis.events.Event;
import com.paul.teventis.game.GamePlayerOne;
import com.paul.teventis.game.GamePlayerTwo;
import com.paul.teventis.set.Set;
import com.paul.teventis.set.SetPlayerOne;
import com.paul.teventis.set.SetPlayerTwo;
import org.junit.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class SetsCanBeWon {
    @Test
    public void playerOneCanWinASetViaReplay() {
        String matchId = UUID.randomUUID().toString();

        final FakeEventStore inMemoryEventStream = new FakeEventStore();
        inMemoryEventStream.writeAll(Set.streamNameFor(matchId), ImmutableList.of(
                new GamePlayerOne(),
                new GamePlayerOne(),
                new GamePlayerOne(),
                new GamePlayerOne(),
                new GamePlayerOne(),
                new GamePlayerOne()
        ));

        final Set set = new Set(inMemoryEventStream, matchId);
        assertThat(set.score()).isEqualTo("6-0");

        final Event event = inMemoryEventStream.readLast("match-" + matchId);
        assertThat(event).isInstanceOf(SetPlayerOne.class);
    }

    @Test
    public void playerOneCanWinASetViaReplayAndSubscription() {
        String matchId = UUID.randomUUID().toString();

        final FakeEventStore inMemoryEventStream = new FakeEventStore();
        inMemoryEventStream.writeAll(Set.streamNameFor(matchId), ImmutableList.of(
                new GamePlayerOne(),
                new GamePlayerOne(),
                new GamePlayerOne()
        ));

        final Set set = new Set(inMemoryEventStream, matchId);

        inMemoryEventStream.writeAll(Set.streamNameFor(matchId), ImmutableList.of(
                new GamePlayerOne(),
                new GamePlayerOne(),
                new GamePlayerOne()
        ));

        assertThat(set.score()).isEqualTo("6-0");

        final Event event = inMemoryEventStream.readLast("match-" + matchId);
        assertThat(event).isInstanceOf(SetPlayerOne.class);
    }

    @Test
    public void playerTwoCanWinASetViaReplay() {
        String matchId = UUID.randomUUID().toString();

        final FakeEventStore inMemoryEventStream = new FakeEventStore();
        inMemoryEventStream.writeAll(Set.streamNameFor(matchId), ImmutableList.of(
                new GamePlayerTwo(),
                new GamePlayerTwo(),
                new GamePlayerTwo(),
                new GamePlayerTwo(),
                new GamePlayerTwo(),
                new GamePlayerTwo()
        ));

        final Set set = new Set(inMemoryEventStream, matchId);
        assertThat(set.score()).isEqualTo("0-6");

        final Event event = inMemoryEventStream.readLast("match-" + matchId);
        assertThat(event).isInstanceOf(SetPlayerTwo.class);
    }
}
