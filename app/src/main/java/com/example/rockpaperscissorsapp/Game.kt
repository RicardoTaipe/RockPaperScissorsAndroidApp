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
        compChoice = Choice.values().random()
        return compChoice as Choice
    }

    fun play(userChoice: Choice): Result {
        return if ((userChoice.ordinal + 1) % 3 == compChoice?.ordinal) {
            if (score > 0) score--
            Result.LOSE
        } else if (userChoice.ordinal == compChoice?.ordinal) {
            Result.DRAW
        } else {
            score++
            Result.WIN
        }
    }
}