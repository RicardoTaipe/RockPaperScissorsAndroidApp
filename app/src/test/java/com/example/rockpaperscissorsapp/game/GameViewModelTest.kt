package com.example.rockpaperscissorsapp.game

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.rockpaperscissorsapp.MainCoroutineRule
import com.example.rockpaperscissorsapp.countdown.FakeTimer
import com.example.rockpaperscissorsapp.data.Choice
import com.example.rockpaperscissorsapp.data.GameRepository
import com.example.rockpaperscissorsapp.data.Result
import com.example.rockpaperscissorsapp.countdown.FakeTimer.TypeListener.OnFinish
import com.example.rockpaperscissorsapp.countdown.FakeTimer.TypeListener.OnTick
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
import org.mockito.Mockito.*
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
    fun `test selectOption updates yourChoice and starts timer`() {
        val testChoice = Choice.ROCK
        gameViewModel.selectOption(testChoice)

        assertEquals(testChoice, gameViewModel.yourChoice.getOrAwaitValue())
        assertTrue(mockTimer.onStartCalled)
    }

    @Test
    fun `test resetGame set yourChoice, comChoice, result to null and cancel timer`() {
        gameViewModel.resetGame()

        assertNull(gameViewModel.yourChoice.getOrAwaitValue())
        assertNull(gameViewModel.comChoice.getOrAwaitValue())
        assertNull(gameViewModel.result.getOrAwaitValue())
        assertTrue(mockTimer.onCancelCalled)
    }

    @Test
    fun `test selectOption call onFinish listener`() {
        //given
        val testChoice = Choice.ROCK
        mockTimer.typeListener = OnFinish
        `when`(mockGameRepository.getRandomComputerChoice()).thenReturn(Choice.SCISSORS)
        `when`(mockGameRepository.play(testChoice)).thenReturn(Result.WIN)
        `when`(mockGameRepository.score).thenReturn(1)

        //when
        gameViewModel.selectOption(testChoice)
        //then
        assertEquals(Choice.SCISSORS, gameViewModel.comChoice.getOrAwaitValue())
        assertTrue(mockTimer.onFinishCalled)
        verify(mockGameRepository).getRandomComputerChoice()
        verify(mockGameRepository).play(testChoice)
        verify(mockGameRepository).score

    }

    @Test
    fun `test selectOption call onTick listener`() {
        //given
        val testChoice = Choice.ROCK
        mockTimer.typeListener = OnTick(2000L)
        //when
        gameViewModel.selectOption(testChoice)
        //then
        assertEquals("3", gameViewModel.counter.getOrAwaitValue())

    }
}
