package com.paul.teventis;

import com.paul.teventis.set.Set;
import com.paul.teventis.set.SetScoreAnnounced;
import com.paul.teventis.set.SetStarted;
import org.junit.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class SetsCanBeScored {

    @Test
    public void byReplayingASetWithNoActivity() {
        String setId = UUID.randomUUID().toString();
        String matchId = UUID.randomUUID().toString();

        final FakeEventStore inMemoryEventStream = new FakeEventStore();
        inMemoryEventStream.write("set-"+setId, new SetStarted(setId));

        Set set = new Set(inMemoryEventStream, matchId, setId);

        final SetScoreAnnounced scoreAnnounced = (SetScoreAnnounced) inMemoryEventStream.readLast("match-" + matchId);

        assertThat(scoreAnnounced.toString()).isEqualTo("0-0");
    }

}

