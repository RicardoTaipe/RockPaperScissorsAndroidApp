package com.example.rockpaperscissorsapp.data

class GameRepositoryImp(private val randomProvider: () -> Choice) : GameRepository {

    private var computerChoice: Choice = Choice.ROCK

    override var score = 0
        private set

    override fun getRandomComputerChoice(): Choice {
        return randomProvider.invoke().also { computerChoice = it }
    }

    override fun play(userChoice: Choice): Result {
        return when {
            userChoice == computerChoice -> {
                Result.DRAW
            }

            userChoice.beats(computerChoice) -> {
                score++
                Result.WIN
            }

            else -> {
                score = (score - 1).coerceAtLeast(0)
                Result.LOSE
            }
        }
    }
}