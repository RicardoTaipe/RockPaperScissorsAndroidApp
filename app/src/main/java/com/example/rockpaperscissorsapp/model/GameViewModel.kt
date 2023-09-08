package com.example.rockpaperscissorsapp.model

import android.os.CountDownTimer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations.map
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.rockpaperscissorsapp.EspressoIdlingResource
import com.example.rockpaperscissorsapp.Game
import com.example.rockpaperscissorsapp.MyCountDownTimer

private const val ONE_SECOND = 1000L
private const val COUNTDOWN_TIME = 3000L

class GameViewModel(private val game: Game, private val timer: MyCountDownTimer) :
    ViewModel() {
    //var timer: CountDownTimer
    private val _yourChoice = MutableLiveData<Game.Choice?>()
    val yourChoice: LiveData<Game.Choice?> = _yourChoice
    private val _comChoice = MutableLiveData<Game.Choice?>()
    val comChoice: LiveData<Game.Choice?> = _comChoice
    private val _result = MutableLiveData<Game.Result?>()
    val result: LiveData<Game.Result?> = _result

    private val _score = MutableLiveData(0)
    val score = map(_score) {
        it.toString()
    }
    private val _counter = MutableLiveData<Long>()
    val counter = map(_counter) {
        it.toString()
    }

    init {

        timer.run {
            onTickListener = { millisUntilFinished ->
                _counter.value = ((millisUntilFinished + ONE_SECOND) / ONE_SECOND)
            }
            onFinishListener = {
                _comChoice.value = game.randomChoice()
                _result.value = game.play(_yourChoice.value!!)
                _score.value = game.score
                EspressoIdlingResource.decrement()
            }
        }
    }

    fun selectOption(option: Game.Choice) {
        _yourChoice.value = option
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
                return GameViewModel(Game(), MyCountDownTimer(COUNTDOWN_TIME, ONE_SECOND)) as T
            }
        }
    }
}