package com.example.rockpaperscissorsapp.model

import android.os.CountDownTimer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations.map
import androidx.lifecycle.ViewModel
import com.example.rockpaperscissorsapp.EspressoIdlingResource
import com.example.rockpaperscissorsapp.Game

class GameViewModel : ViewModel() {
    companion object {
        private const val ONE_SECOND = 1000L
        private const val COUNTDOWN_TIME = 3000L

    }

    private val timer: CountDownTimer

    private var game: Game = Game()
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
        timer = object : CountDownTimer(COUNTDOWN_TIME, ONE_SECOND) {

            override fun onTick(millisUntilFinished: Long) {
                _counter.value = ((millisUntilFinished + ONE_SECOND) / ONE_SECOND)
            }

            override fun onFinish() {
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
    }

}