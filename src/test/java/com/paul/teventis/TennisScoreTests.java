package com.paul.teventis;

import com.paul.teventis.game.PlayerOneScored;
import com.paul.teventis.game.PlayerTwoScored;
import com.paul.teventis.game.CannotScoreAfterGameIsWon;
import com.paul.teventis.game.GamePlayerOne;
import com.paul.teventis.game.GamePlayerTwo;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

public class TennisScoreTests {
    @Test
    public void playerOneCannotScoreAfterTheyWinTheGame() {
        final Throwable ex = catchThrowable(() -> new GamePlayerOne().when(new PlayerOneScored()));
        assertThat(ex).isInstanceOf(CannotScoreAfterGameIsWon.class);
    }

    @Test
    public void playerOneCannotScoreAfterPlayerTwoWinsTheGame() {
        final Throwable ex = catchThrowable(() -> new GamePlayerTwo().when(new PlayerOneScored()));
        assertThat(ex).isInstanceOf(CannotScoreAfterGameIsWon.class);
    }

    @Test
    public void playerTwoCannotScoreAfterTheyWinTheGame() {
        final Throwable ex = catchThrowable(() -> new GamePlayerTwo().when(new PlayerTwoScored()));
        assertThat(ex).isInstanceOf(CannotScoreAfterGameIsWon.class);
    }

    @Test
    public void playerTwoCannotScoreAfterPlayerOneWinsTheGame() {
        final Throwable ex = catchThrowable(() -> new GamePlayerOne().when(new PlayerTwoScored()));
        assertThat(ex).isInstanceOf(CannotScoreAfterGameIsWon.class);
    }
}
