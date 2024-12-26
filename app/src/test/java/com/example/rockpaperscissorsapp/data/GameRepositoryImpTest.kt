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
        gameRepository.userChoice = Choice.PAPER
        gameRepository.getRandomComputerChoice()
        assertEquals(Result.LOSE, gameRepository.play())
    }

    @Test
    fun whenYouPaperAndComRockThenWin() {
        `when`(mockRandomProvider.invoke()).thenReturn(Choice.ROCK)
        gameRepository.userChoice = Choice.PAPER
        gameRepository.getRandomComputerChoice()
        assertEquals(Result.WIN, gameRepository.play())
    }

    @Test
    fun whenYouPaperAndComPaperThenDraw() {
        `when`(mockRandomProvider.invoke()).thenReturn(Choice.PAPER)
        gameRepository.userChoice = Choice.PAPER
        gameRepository.getRandomComputerChoice()
        assertEquals(Result.DRAW, gameRepository.play())
    }

    @Test
    fun whenYouWinThenAddScore() {
        `when`(mockRandomProvider.invoke()).thenReturn(Choice.ROCK)
        gameRepository.userChoice = Choice.PAPER
        gameRepository.getRandomComputerChoice()
        gameRepository.play()
        assertEquals(gameRepository.score, 1)
    }

    @Test
    fun whenScoreZeroAndYouLoseThenScoreZero() {
        `when`(mockRandomProvider.invoke()).thenReturn(Choice.SCISSORS)
        gameRepository.userChoice = Choice.PAPER
        gameRepository.getRandomComputerChoice()
        gameRepository.play()
        assertEquals(gameRepository.score, 0)
    }

    @Test
    fun whenScoreTwoAndYouLoseThenScoreOne() {
        repeat(2) {
            `when`(mockRandomProvider.invoke()).thenReturn(Choice.PAPER)
            gameRepository.userChoice = Choice.SCISSORS
            gameRepository.getRandomComputerChoice()
            gameRepository.play()
        }

        `when`(mockRandomProvider.invoke()).thenReturn(Choice.SCISSORS)
        gameRepository.userChoice = Choice.PAPER
        gameRepository.getRandomComputerChoice()
        gameRepository.play()

        assertEquals(gameRepository.score, 1)
    }
}