package com.example.rockpaperscissorsapp

class Game {

    enum class Choice {
        ROCK,
        PAPER,
        SCISSORS
    }

    var score = 0

    private fun randomChoice() = Choice.values().random()

    fun play(userChoice: Choice): String {
        val compChoice = randomChoice()
        val winner = if ((userChoice.ordinal + 1) % 3 == compChoice.ordinal) {
            score--
            "$compChoice House win"
        } else if (userChoice.ordinal == compChoice.ordinal) {
            "$compChoice It's a draw"
        } else {
            score++
            "$compChoice You win"
        }
        return winner
    }
}