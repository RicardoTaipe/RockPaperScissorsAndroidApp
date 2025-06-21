package com.example.rockpaperscissorsapp.data

import kotlinx.coroutines.flow.StateFlow

interface GameRepository {
    val score: StateFlow<Int>
    val userChoice: StateFlow<Choice>
    val computerChoice: StateFlow<Choice>
    val result: StateFlow<Result>

    fun setUserChoice(choice: Choice)
    fun play()
    fun reset()
}