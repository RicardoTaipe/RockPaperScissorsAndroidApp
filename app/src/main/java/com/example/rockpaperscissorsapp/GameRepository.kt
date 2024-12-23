package com.example.rockpaperscissorsapp

import com.example.rockpaperscissorsapp.model.Choice
import com.example.rockpaperscissorsapp.model.Result

interface GameRepository {
    val score: Int
    var randomProvider: () -> Choice
    fun getRandomComputerChoice(): Choice
    fun play(userChoice: Choice): Result
}