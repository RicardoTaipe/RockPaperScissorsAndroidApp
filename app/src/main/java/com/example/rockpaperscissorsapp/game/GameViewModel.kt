package com.example.rockpaperscissorsapp.game

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.rockpaperscissorsapp.RockPaperScissorsApplication
import com.example.rockpaperscissorsapp.countdown.MyCountDownTimer.Companion.ONE_SECOND
import com.example.rockpaperscissorsapp.countdown.MyCountDownTimer.Companion.TOTAL_TIME_TIMER
import com.example.rockpaperscissorsapp.countdown.ShadowCountdownTimer
import com.example.rockpaperscissorsapp.data.Choice
import com.example.rockpaperscissorsapp.data.GameRepository
import com.example.rockpaperscissorsapp.data.Result
import com.example.rockpaperscissorsapp.utils.EspressoIdlingResource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class GameViewModel(
    private val gameRepository: GameRepository,
    private val timer: ShadowCountdownTimer
) : ViewModel() {

    val yourChoice: StateFlow<Choice> = gameRepository.userChoice

    val comChoice: StateFlow<Choice> = gameRepository.computerChoice

    val result: StateFlow<Result> = gameRepository.result

    val score: StateFlow<String> = gameRepository.score.map { it.toString() }.stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        gameRepository.score.value.toString()
    )

    private val _counter = MutableStateFlow(TOTAL_TIME_TIMER)
    val counter: StateFlow<String> = _counter
        .map { it.toString() }
        .stateIn(viewModelScope, SharingStarted.Eagerly, _counter.value.toString())


    private fun setTimerListener() {
        timer.listener = object : ShadowCountdownTimer.Listener {
            override fun onTick(millisUntilFinished: Long) {
                _counter.value = ((millisUntilFinished) / ONE_SECOND).inc()
            }

            override fun onFinish() {
                gameRepository.play()
                EspressoIdlingResource.decrement()
            }
        }
    }

    fun playGame() {
        setTimerListener()
        timer.start()
        EspressoIdlingResource.increment()
    }

    override fun onCleared() {
        super.onCleared()
        timer.cancel()
    }

    fun resetGame() {
        gameRepository.reset()
        timer.cancel()
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val container = (this[APPLICATION_KEY] as RockPaperScissorsApplication).container
                GameViewModel(container.gameRepository, container.timer)
            }
        }
    }
}