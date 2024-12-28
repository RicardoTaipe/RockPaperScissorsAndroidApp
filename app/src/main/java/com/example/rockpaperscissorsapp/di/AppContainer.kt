package com.example.rockpaperscissorsapp.di

import com.example.rockpaperscissorsapp.countdown.MyCountDownTimer
import com.example.rockpaperscissorsapp.countdown.MyCountDownTimer.Companion.COUNTDOWN_TIME
import com.example.rockpaperscissorsapp.countdown.MyCountDownTimer.Companion.ONE_SECOND
import com.example.rockpaperscissorsapp.countdown.ShadowCountdownTimer
import com.example.rockpaperscissorsapp.data.Choice
import com.example.rockpaperscissorsapp.data.GameRepository
import com.example.rockpaperscissorsapp.data.GameRepositoryImp

interface AppContainer {
    val gameRepository: GameRepository
    val timer: ShadowCountdownTimer
    val randomProvider: () -> Choice
}

object DefaultAppContainer : AppContainer {

    override val randomProvider: () -> Choice = { Choice.entries.random() }
    
    override val gameRepository: GameRepository = GameRepositoryImp(randomProvider)

    override val timer: ShadowCountdownTimer = MyCountDownTimer(COUNTDOWN_TIME, ONE_SECOND)

}