package com.example.rockpaperscissorsapp.data

interface GameRepository {
    val score: Int
    var userChoice: Choice
    fun getRandomComputerChoice(): Choice
    fun play(): Result
}