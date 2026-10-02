package com.example.rockpaperscissorsapp.game

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.rockpaperscissorsapp.MainCoroutineRule
import com.example.rockpaperscissorsapp.data.GameResult
import com.example.rockpaperscissorsapp.data.Move
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import kotlin.time.Duration.Companion.milliseconds

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class GameViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Test
    fun `GIVEN initial state WHEN nothing happens THEN state should be WaitingForPlayer`() = runTest {
        // GIVEN
        val viewModel = GameViewModel { Move.ROCK }

        // THEN
        val state = viewModel.state.value
        assertTrue(state.game is GameUiState.WaitingForPlayer)
        assertEquals(0, state.game.score)
        assertFalse(state.isRulesDialogOpen)
    }

    @Test
    fun `GIVEN game is idle WHEN OpenRules intent is processed THEN isRulesDialogOpen is true`() = runTest {
        // GIVEN
        val viewModel = GameViewModel { Move.ROCK }

        // WHEN
        viewModel.process(GameIntent.OpenRules)

        // THEN
        assertTrue(viewModel.state.value.isRulesDialogOpen)
    }

    @Test
    fun `GIVEN game is idle WHEN OpenRules intent is processed THEN isRulesDialogOpen is false`() = runTest {
        // GIVEN
        val viewModel = GameViewModel { Move.ROCK }

        // WHEN
        viewModel.process(GameIntent.CloseRules)

        // THEN
        assertFalse(viewModel.state.value.isRulesDialogOpen)
    }

    @Test
    fun `GIVEN player is waiting WHEN Play intent is processed THEN transition to WaitingForOpponent with countdown`() =
        runTest {
            // GIVEN
            val viewModel = GameViewModel { Move.ROCK }
            val playerMove = Move.PAPER

            // WHEN
            viewModel.process(GameIntent.Play(playerMove))

            // THEN
            val game = viewModel.state.value.game
            assertTrue(game is GameUiState.WaitingForOpponent)
            assertEquals(playerMove, (game as GameUiState.WaitingForOpponent).playerMove)
            assertEquals(3, game.countdown)
        }

    @Test
    fun `GIVEN countdown is active WHEN 3 seconds pass THEN game result is generated and score is updated`() =
        runTest {
            // GIVEN
            val opponentMove = Move.SCISSORS
            val viewModel = GameViewModel { opponentMove } // Opponent always SCISSORS
            viewModel.process(GameIntent.Play(Move.ROCK)) // Player ROCK (WIN)

            // WHEN
            advanceTimeBy(3001.milliseconds) // Fast-forward past the 3-second delay

            // THEN
            val resultState = viewModel.state.value.game
            assertTrue(resultState is GameUiState.Result)
            resultState as GameUiState.Result
            assertEquals(GameResult.WIN, resultState.gameResult)
            assertEquals(1, resultState.score)
        }

    @Test
    fun `GIVEN score is zero WHEN player loses THEN score remains zero and does not go negative`() =
        runTest {
            // GIVEN
            val opponentMove = Move.ROCK
            val viewModel = GameViewModel { opponentMove }
            viewModel.process(GameIntent.Play(Move.SCISSORS)) // Player SCISSORS (LOSE)

            // WHEN
            advanceTimeBy(3001.milliseconds)

            // THEN
            val resultState = viewModel.state.value.game as GameUiState.Result
            assertEquals(0, resultState.score)
        }

    @Test
    fun `GIVEN game is in result state WHEN NextRound intent is processed THEN state returns to WaitingForPlayer`() =
        runTest {
            // GIVEN
            val viewModel = GameViewModel { Move.ROCK }
            viewModel.process(GameIntent.Play(Move.ROCK))
            advanceTimeBy(3001.milliseconds) // Move to Result state

            // WHEN
            viewModel.process(GameIntent.NextRound)

            // THEN
            assertTrue(viewModel.state.value.game is GameUiState.WaitingForPlayer)
            assertNull((viewModel.state.value.game as? GameUiState.WaitingForOpponent)?.playerMove)
        }

    @Test
    fun `GIVEN countdown is active WHEN ResetGame intent is processed THEN state is fully reset`() =
        runTest {
            // GIVEN
            val viewModel = GameViewModel { Move.ROCK }
            viewModel.process(GameIntent.Play(Move.PAPER))
            advanceTimeBy(1000.milliseconds)

            // WHEN
            viewModel.process(GameIntent.ResetGame)

            // THEN
            val state = viewModel.state.value
            assertEquals(GameUiState.WaitingForPlayer(0), state.game)
        }
}
