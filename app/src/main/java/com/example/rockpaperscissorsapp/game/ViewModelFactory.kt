package com.example.rockpaperscissorsapp.game

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.rockpaperscissorsapp.RockPaperScissorsApplication

val GameViewModelFactory: ViewModelProvider.Factory = viewModelFactory {
    initializer {
        val container = (this[APPLICATION_KEY] as RockPaperScissorsApplication).container
        //container.gameRepository
        GameViewModel(container.randomGenerator)
    }
}