package com.example.rockpaperscissorsapp.data

enum class Move {
    ROCK, PAPER, SCISSORS;

    fun compare(other: Move): GameResult = when {
        this == other -> GameResult.DRAW
        this.beats(other) -> GameResult.WIN
        else -> GameResult.LOSE
    }

    private fun beats(other: Move): Boolean = when (this) {
        ROCK -> other == SCISSORS
        PAPER -> other == ROCK
        SCISSORS -> other == PAPER
    }
}