package com.example.rockpaperscissorsapp.data

interface GameRepository {
    val score: Int
    var randomProvider: () -> Choice
    fun getRandomComputerChoice(): Choice
    fun play(userChoice: Choice): Result
}