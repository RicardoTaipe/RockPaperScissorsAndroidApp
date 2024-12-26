package com.example.rockpaperscissorsapp.play

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.rockpaperscissorsapp.RockPaperScissorsApplication
import com.example.rockpaperscissorsapp.data.Choice
import com.example.rockpaperscissorsapp.data.GameRepository

class PlayViewModel(
    private val gameRepository: GameRepository,
) :
    ViewModel() {

    fun selectOption(option: Choice) {
        gameRepository.userChoice = option
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val container = (this[APPLICATION_KEY] as RockPaperScissorsApplication).container
                PlayViewModel(container.gameRepository)
            }
        }
    }
}