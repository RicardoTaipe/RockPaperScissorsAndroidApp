package com.example.rockpaperscissorsapp.game

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.map
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.rockpaperscissorsapp.countdown.MyCountDownTimer
import com.example.rockpaperscissorsapp.countdown.MyCountDownTimer.Companion.COUNTDOWN_TIME
import com.example.rockpaperscissorsapp.countdown.MyCountDownTimer.Companion.ONE_SECOND
import com.example.rockpaperscissorsapp.countdown.MyCountDownTimer.Companion.TOTAL_TIME_TIMER
import com.example.rockpaperscissorsapp.countdown.ShadowCountdownTimer
import com.example.rockpaperscissorsapp.data.Choice
import com.example.rockpaperscissorsapp.data.GameRepository
import com.example.rockpaperscissorsapp.data.GameRepositoryImp
import com.example.rockpaperscissorsapp.data.Result
import com.example.rockpaperscissorsapp.utils.EspressoIdlingResource

class GameViewModel(
    private val gameRepository: GameRepository,
    private val timer: ShadowCountdownTimer
) :
    ViewModel() {
    private val _yourChoice = MutableLiveData<Choice?>()
    val yourChoice: LiveData<Choice?> = _yourChoice

    private val _comChoice = MutableLiveData<Choice?>()
    val comChoice: LiveData<Choice?> = _comChoice

    private val _result = MutableLiveData<Result?>()
    val result: LiveData<Result?> = _result

    private val _score = MutableLiveData(0)
    val score = _score.map { it.toString() }

    private val _counter = MutableLiveData(TOTAL_TIME_TIMER)
    val counter = _counter.map { it.toString() }

    private fun setTimerListener() {
        timer.listener = object : ShadowCountdownTimer.Listener {
            override fun onTick(millisUntilFinished: Long) {
                _counter.value = ((millisUntilFinished) / ONE_SECOND).inc()
            }

            override fun onFinish() {
                _comChoice.value = gameRepository.getRandomComputerChoice()
                _yourChoice.value?.let {
                    _result.value = gameRepository.play(it)
                    _score.value = gameRepository.score
                    EspressoIdlingResource.decrement()
                }
            }
        }
    }

    fun selectOption(option: Choice) {
        _yourChoice.value = option
        setTimerListener()
        timer.start()
        EspressoIdlingResource.increment()
    }

    override fun onCleared() {
        super.onCleared()
        timer.cancel()
    }

    fun resetGame() {
        _yourChoice.value = null
        _comChoice.value = null
        _result.value = null
        timer.cancel()
    }

    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>, extras: CreationExtras
            ): T {
                return GameViewModel(
                    GameRepositoryImp { Choice.entries.random() },
                    MyCountDownTimer(COUNTDOWN_TIME, ONE_SECOND)
                ) as T
            }
        }
    }
}