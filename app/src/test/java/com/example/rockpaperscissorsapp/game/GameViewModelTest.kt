package com.example.rockpaperscissorsapp.game

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.rockpaperscissorsapp.MainCoroutineRule
import com.example.rockpaperscissorsapp.countdown.FakeTimer
import com.example.rockpaperscissorsapp.countdown.FakeTimer.TypeListener.OnFinish
import com.example.rockpaperscissorsapp.countdown.FakeTimer.TypeListener.OnTick
import com.example.rockpaperscissorsapp.countdown.MyCountDownTimer.Companion.ONE_SECOND
import com.example.rockpaperscissorsapp.data.Choice
import com.example.rockpaperscissorsapp.data.GameRepository
import com.example.rockpaperscissorsapp.data.Result
import com.example.rockpaperscissorsapp.getOrAwaitValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class GameViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Mock
    lateinit var mockGameRepository: GameRepository

    private lateinit var mockTimer: FakeTimer

    private lateinit var gameViewModel: GameViewModel

    @Before
    fun setUp() {
        mockTimer = FakeTimer()
        gameViewModel = GameViewModel(mockGameRepository, mockTimer)
    }

    @Test
    fun `GIVEN a choice is selected WHEN selectOption is called THEN yourChoice is updated and timer starts`() {
        // GIVEN
        val testChoice = Choice.ROCK

        // WHEN
        gameViewModel.selectOption(testChoice)

        // THEN
        assertEquals(testChoice, gameViewModel.yourChoice.getOrAwaitValue())
        assertTrue(mockTimer.onStartCalled)
    }

    @Test
    fun `GIVEN game is reset WHEN resetGame is called THEN yourChoice, comChoice, and result are null and timer is canceled`() {
        // GIVEN: Game is initialized and state is set
        // WHEN
        gameViewModel.resetGame()

        // THEN
        assertNull(gameViewModel.yourChoice.getOrAwaitValue())
        assertNull(gameViewModel.comChoice.getOrAwaitValue())
        assertNull(gameViewModel.result.getOrAwaitValue())
        assertTrue(mockTimer.onCancelCalled)
    }


    @Test
    fun `GIVEN a choice is selected WHEN selectOption is called THEN the onFinish listener is triggered and the computer's choice, user and score are set`() {
        // GIVEN
        val testChoice = Choice.ROCK
        mockTimer.typeListener = OnFinish
        `when`(mockGameRepository.getRandomComputerChoice()).thenReturn(Choice.SCISSORS)
        `when`(mockGameRepository.play(testChoice)).thenReturn(Result.WIN)
        val expectedScore = 1
        `when`(mockGameRepository.score).thenReturn(expectedScore)

        // WHEN
        gameViewModel.selectOption(testChoice)

        // THEN
        assertEquals(Choice.SCISSORS, gameViewModel.comChoice.getOrAwaitValue())
        assertEquals(testChoice, gameViewModel.yourChoice.getOrAwaitValue())
        assertEquals(expectedScore.toString(), gameViewModel.score.getOrAwaitValue())
        assertTrue(mockTimer.onFinishCalled)
    }

    @Test
    fun `GIVEN a choice is selected WHEN selectOption is called THEN the onTick listener is triggered and the counter is updated`() {
        // GIVEN
        val expectedSecondsRemaining = (2000L / ONE_SECOND).inc()
        mockTimer.typeListener = OnTick(expectedSecondsRemaining)

        // WHEN
        gameViewModel.selectOption(Choice.ROCK)

        // THEN
        assertEquals(expectedSecondsRemaining.toString(), gameViewModel.counter.getOrAwaitValue())
    }
}
