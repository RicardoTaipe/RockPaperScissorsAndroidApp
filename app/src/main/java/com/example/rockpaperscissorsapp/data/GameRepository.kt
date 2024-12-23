package com.example.rockpaperscissorsapp.data

interface GameRepository {
    val score: Int
    fun getRandomComputerChoice(): Choice
    fun play(userChoice: Choice): Result
}