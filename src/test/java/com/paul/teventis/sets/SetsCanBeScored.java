package com.paul.teventis.sets;

import com.google.common.collect.ImmutableList;
import com.paul.teventis.FakeEventStore;
import com.paul.teventis.game.GamePlayerOne;
import com.paul.teventis.game.GamePlayerTwo;
import com.paul.teventis.set.Set;
import org.junit.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class SetsCanBeScored {

    @Test
    public void byReplayingASetWithNoActivity() {
        String matchId = UUID.randomUUID().toString();

        final FakeEventStore inMemoryEventStream = new FakeEventStore();

        final Set set = new Set(inMemoryEventStream, matchId);

        assertThat(set.score()).isEqualTo("0-0");
    }

    @Test
    public void byReplayingASetWithOneGameWon() {
        String matchId = UUID.randomUUID().toString();

        final FakeEventStore inMemoryEventStream = new FakeEventStore();
        inMemoryEventStream.writeAll(Set.streamNameFor(matchId), ImmutableList.of(
                new GamePlayerOne()
        ));

        new Set(inMemoryEventStream, matchId);

        final Set set = new Set(inMemoryEventStream, matchId);

        assertThat(set.score()).isEqualTo("1-0");
    }

    @Test
    public void byReplayingAndSubscribing() {
        String matchId = UUID.randomUUID().toString();

        final FakeEventStore inMemoryEventStream = new FakeEventStore();
        inMemoryEventStream.writeAll(Set.streamNameFor(matchId), ImmutableList.of(
                new GamePlayerOne(),
                new GamePlayerTwo()
        ));

        final Set set = new Set(inMemoryEventStream, matchId);

        inMemoryEventStream.write(Set.streamNameFor(matchId), new GamePlayerTwo());

        assertThat(set.score()).isEqualTo("1-2");
    }

}

