package com.example.rockpaperscissorsapp

import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GameTest {
    private lateinit var game: Game

    @Before
    fun setUp() {
        game = Game()
    }

    @After
    fun tearDown() {
        game.score = 0
    }

    @Test
    fun whenYouPaperAndComScissorsThenLose() {
        game.compChoice = Game.Choice.SCISSORS
        val result = game.play(Game.Choice.PAPER)
        assertEquals(Game.Result.LOSE, result)
    }

    @Test
    fun whenYouPaperAndComRockThenWin() {
        game.compChoice = Game.Choice.ROCK
        val result = game.play(Game.Choice.PAPER)
        assertEquals(Game.Result.WIN, result)
    }

    @Test
    fun whenYouPaperAndComPaperThenDraw() {
        game.compChoice = Game.Choice.PAPER
        val result = game.play(Game.Choice.PAPER)
        assertEquals(Game.Result.DRAW, result)
    }

    @Test
    fun whenYouWinThenAddScore() {
        game.compChoice = Game.Choice.ROCK
        game.play(Game.Choice.PAPER)
        assertEquals(game.score, 1)
    }

    @Test
    fun whenScoreZeroAndYouLoseThenScoreZero() {
        game.compChoice = Game.Choice.SCISSORS
        game.play(Game.Choice.PAPER)
        assertEquals(game.score, 0)
    }

    @Test
    fun whenScoreTwoAndYouLoseThenScoreOne() {
        repeat(2){
            game.compChoice = Game.Choice.PAPER
            game.play(Game.Choice.SCISSORS)
        }

        game.compChoice = Game.Choice.SCISSORS
        game.play(Game.Choice.PAPER)

        assertEquals(game.score, 1)
    }
}