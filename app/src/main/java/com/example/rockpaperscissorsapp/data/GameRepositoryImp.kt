package com.example.rockpaperscissorsapp.data

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class GameRepositoryImp(private val randomProvider: () -> Choice) : GameRepository {

    private val _computerChoice = MutableStateFlow(Choice.ROCK)
    override val computerChoice: StateFlow<Choice> = _computerChoice.asStateFlow()

    private val _userChoice = MutableStateFlow(Choice.ROCK)
    override val userChoice: StateFlow<Choice> = _userChoice.asStateFlow()

    private val _score = MutableStateFlow(0)
    override val score: StateFlow<Int> = _score.asStateFlow()

    private val _result = MutableStateFlow(Result.DRAW)
    override val result: StateFlow<Result> = _result.asStateFlow()

    override fun setUserChoice(choice: Choice) {
        _userChoice.value = choice
    }

    override fun play() {
        val user = _userChoice.value
        val computer = randomProvider.invoke()
        _computerChoice.value = computer

        val gameResult = when {
            user == computer -> Result.DRAW
            user.beats(computer) -> {
                _score.value += 1
                Result.WIN
            }
            else -> {
                _score.value = (_score.value - 1).coerceAtLeast(0)
                Result.LOSE
            }
        }
        _result.value = gameResult
    }

    override fun reset() {
        _score.value = 0
        _userChoice.value = Choice.ROCK
        _computerChoice.value = Choice.ROCK
        _result.value = Result.DRAW
    }
}