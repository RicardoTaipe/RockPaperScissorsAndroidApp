package com.example.rockpaperscissorsapp.di

import com.example.rockpaperscissorsapp.data.Choice
import com.example.rockpaperscissorsapp.data.GameRepository
import com.example.rockpaperscissorsapp.data.GameRepositoryImp
import com.example.rockpaperscissorsapp.data.Move

interface AppContainer {
    val gameRepository: GameRepository
    val randomProvider: () -> Choice
    val randomGenerator: () -> Move
}

object DefaultAppContainer : AppContainer {

    override val randomProvider: () -> Choice = { Choice.entries.random() }
    override val randomGenerator: () -> Move = { Move.entries.random() }
    override val gameRepository: GameRepository = GameRepositoryImp(randomProvider)

}