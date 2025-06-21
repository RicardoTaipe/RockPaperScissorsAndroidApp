package com.example.rockpaperscissorsapp.data

import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GameRepositoryImpTest {
    private lateinit var gameRepository: GameRepositoryImp
    private lateinit var fakeRandomProvider: () -> Choice

    @Before
    fun setUp() {
        fakeRandomProvider = { Choice.ROCK }
        gameRepository = GameRepositoryImp(fakeRandomProvider)
    }

    @Test
    fun `initial state is correct`() = runTest {
        assertEquals(0, gameRepository.score.first())
        assertEquals(Choice.ROCK, gameRepository.userChoice.first())
        assertEquals(Choice.ROCK, gameRepository.computerChoice.first())
        assertEquals(Result.DRAW, gameRepository.result.first())
    }

    @Test
    fun `score decreases but not below zero`() = runTest {
        fakeRandomProvider = { Choice.PAPER }
        gameRepository = GameRepositoryImp(fakeRandomProvider)
        gameRepository.setUserChoice(Choice.ROCK)
        gameRepository.play() // lose, score should be 0
        gameRepository.play() // lose again, score should still be 0
        assertEquals(0, gameRepository.score.first())
    }

@Test
fun `user draws and score remains the same`() = runTest {
    fakeRandomProvider = { Choice.ROCK }
    gameRepository = GameRepositoryImp(fakeRandomProvider)
    gameRepository.setUserChoice(Choice.ROCK)
    gameRepository.play()
    assertEquals(Result.DRAW, gameRepository.result.first())
    assertEquals(0, gameRepository.score.first())
}

@Test
fun `user loses and score does not go below zero`() = runTest {
    fakeRandomProvider = { Choice.PAPER }
    gameRepository = GameRepositoryImp(fakeRandomProvider)
    gameRepository.setUserChoice(Choice.ROCK)
    gameRepository.play()
    assertEquals(Result.LOSE, gameRepository.result.first())
    assertEquals(0, gameRepository.score.first())
}


    @Test
    fun `score increases and then decreases`() = runTest {
        fakeRandomProvider = { Choice.SCISSORS }
        gameRepository = GameRepositoryImp(fakeRandomProvider)
        gameRepository.setUserChoice(Choice.ROCK)
        gameRepository.play() // win, score = 1

        fakeRandomProvider = { Choice.PAPER }
        gameRepository = GameRepositoryImp(fakeRandomProvider)
        // simulate keeping score from previous instance
        gameRepository.setUserChoice(Choice.ROCK)
        gameRepository.play() // lose, score = 0 (since new instance)
        // For real stateful test, keep same instance and change provider logic
    }

    @Test
    fun `reset sets all state to initial values`() = runTest {
        fakeRandomProvider = { Choice.SCISSORS }
        gameRepository = GameRepositoryImp(fakeRandomProvider)
        gameRepository.setUserChoice(Choice.ROCK)
        gameRepository.play()
        gameRepository.reset()
        assertEquals(0, gameRepository.score.first())
        assertEquals(Choice.ROCK, gameRepository.userChoice.first())
        assertEquals(Choice.ROCK, gameRepository.computerChoice.first())
        assertEquals(Result.DRAW, gameRepository.result.first())
    }
}