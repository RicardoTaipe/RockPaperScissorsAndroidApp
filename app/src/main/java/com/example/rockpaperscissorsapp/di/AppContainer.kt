package com.example.rockpaperscissorsapp.di

import com.example.rockpaperscissorsapp.data.Move

interface AppContainer {
    //val gameRepository: GameRepository
    val randomGenerator: () -> Move
}

object DefaultAppContainer : AppContainer {
    override val randomGenerator: () -> Move = { Move.entries.random() }
    //override val gameRepository: GameRepository = GameRepositoryImp(randomProvider)

}