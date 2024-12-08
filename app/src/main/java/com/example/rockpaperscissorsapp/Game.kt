package com.example.rockpaperscissorsapp

class Game {

    enum class Choice {
        ROCK,
        PAPER,
        SCISSORS
    }

    enum class Result {
        WIN,
        DRAW,
        LOSE
    }

    var compChoice: Choice? = null
    var score = 0

    fun randomChoice(): Choice {
        return Choice.entries.random()
    }

    fun play(userChoice: Choice): Result {
        return if ((userChoice.ordinal + 1) % 3 == compChoice?.ordinal) {
            score = (score - 1).coerceAtLeast(0)
            Result.LOSE
        } else if (userChoice.ordinal == compChoice?.ordinal) {
            Result.DRAW
        } else {
            score++
            Result.WIN
        }
    }
}