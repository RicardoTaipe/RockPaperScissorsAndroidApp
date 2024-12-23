package com.example.rockpaperscissorsapp

import com.example.rockpaperscissorsapp.model.Choice
import com.example.rockpaperscissorsapp.model.Result

class GameRepositoryImp : GameRepository {
    override lateinit var randomProvider: () -> Choice

    private var computerChoice: Choice = Choice.ROCK

    override var score = 0
        private set

    override fun getRandomComputerChoice(): Choice {
        return randomProvider.invoke().also { computerChoice = it }
    }

    override fun play(userChoice: Choice): Result {
        return if ((userChoice.ordinal + 1) % 3 == computerChoice.ordinal) {
            score = (score - 1).coerceAtLeast(0)
            Result.LOSE
        } else if (userChoice.ordinal == computerChoice.ordinal) {
            Result.DRAW
        } else {
            score++
            Result.WIN
        }
    }
}