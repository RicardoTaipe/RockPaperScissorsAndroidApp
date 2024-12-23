package com.example.rockpaperscissorsapp.data

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GameRepositoryImpTest {
    private lateinit var gameRepository: GameRepositoryImp

    @Mock
    lateinit var mockRandomProvider: () -> Choice

    @Before
    fun setUp() {
        gameRepository = GameRepositoryImp(mockRandomProvider)
    }

    @Test
    fun wheGetRandomComputerChoiceCalledThenVerifyWasCalled() {
        `when`(mockRandomProvider.invoke()).thenReturn(Choice.SCISSORS)
        gameRepository.getRandomComputerChoice()
        verify(mockRandomProvider).invoke()
    }

    @Test
    fun whenYouPaperAndComScissorsThenLose() {
        `when`(mockRandomProvider.invoke()).thenReturn(Choice.SCISSORS)
        gameRepository.getRandomComputerChoice()
        assertEquals(Result.LOSE, gameRepository.play(Choice.PAPER))
    }

    @Test
    fun whenYouPaperAndComRockThenWin() {
        `when`(mockRandomProvider.invoke()).thenReturn(Choice.ROCK)
        gameRepository.getRandomComputerChoice()
        assertEquals(Result.WIN, gameRepository.play(Choice.PAPER))
    }

    @Test
    fun whenYouPaperAndComPaperThenDraw() {
        `when`(mockRandomProvider.invoke()).thenReturn(Choice.PAPER)
        gameRepository.getRandomComputerChoice()
        assertEquals(Result.DRAW, gameRepository.play(Choice.PAPER))
    }

    @Test
    fun whenYouWinThenAddScore() {
        `when`(mockRandomProvider.invoke()).thenReturn(Choice.ROCK)
        gameRepository.getRandomComputerChoice()
        gameRepository.play(Choice.PAPER)
        assertEquals(gameRepository.score, 1)
    }

    @Test
    fun whenScoreZeroAndYouLoseThenScoreZero() {
        `when`(mockRandomProvider.invoke()).thenReturn(Choice.SCISSORS)
        gameRepository.getRandomComputerChoice()
        gameRepository.play(Choice.PAPER)
        assertEquals(gameRepository.score, 0)
    }

    @Test
    fun whenScoreTwoAndYouLoseThenScoreOne() {
        repeat(2) {
            `when`(mockRandomProvider.invoke()).thenReturn(Choice.PAPER)
            gameRepository.getRandomComputerChoice()
            gameRepository.play(Choice.SCISSORS)
        }

        `when`(mockRandomProvider.invoke()).thenReturn(Choice.SCISSORS)
        gameRepository.getRandomComputerChoice()
        gameRepository.play(Choice.PAPER)

        assertEquals(gameRepository.score, 1)
    }
}