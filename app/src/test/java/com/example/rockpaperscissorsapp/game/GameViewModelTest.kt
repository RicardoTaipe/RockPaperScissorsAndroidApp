package com.example.rockpaperscissorsapp.game

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.rockpaperscissorsapp.data.GameRepositoryImp
import com.example.rockpaperscissorsapp.MyCountDownTimer
import com.example.rockpaperscissorsapp.data.Choice
import com.example.rockpaperscissorsapp.data.GameRepository
import com.example.rockpaperscissorsapp.data.Result
import com.example.rockpaperscissorsapp.getOrAwaitValue
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GameViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var mockGameRepository: GameRepository

    @Mock
    lateinit var mockTimer: MyCountDownTimer

    private lateinit var gameViewModel: GameViewModel

    @Before
    fun setUp() {
        gameViewModel = GameViewModel(mockGameRepository, mockTimer)
    }

    @Test
    fun `test selectOption updates yourChoice and starts timer`() {
        val testChoice = Choice.ROCK
        gameViewModel.selectOption(testChoice)

        assertEquals(testChoice, gameViewModel.yourChoice.getOrAwaitValue())
        verify(mockTimer).start()
    }

    @Test
    fun `test resetGame set yourChoice, comChoice, result to null and cancel timer`() {
        gameViewModel.resetGame()

        assertNull(gameViewModel.yourChoice.getOrAwaitValue())
        assertNull(gameViewModel.comChoice.getOrAwaitValue())
        assertNull(gameViewModel.result.getOrAwaitValue())
        verify(mockTimer).cancel()
    }
    
}
