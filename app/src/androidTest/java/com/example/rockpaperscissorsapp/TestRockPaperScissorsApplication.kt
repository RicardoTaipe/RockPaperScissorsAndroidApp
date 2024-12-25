package com.example.rockpaperscissorsapp

import com.example.rockpaperscissorsapp.di.AppContainer
import org.mockito.Mockito

class TestRockPaperScissorsApplication : RockPaperScissorsApplication() {
    override fun initializeAppContainer(): AppContainer {
        return Mockito.mock(AppContainer::class.java)
    }
}
