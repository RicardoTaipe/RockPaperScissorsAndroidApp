package com.example.rockpaperscissorsapp.countdown

import com.example.rockpaperscissorsapp.countdown.FakeTimer.TypeListener.*

class FakeTimer : ShadowCountdownTimer {
    var onStartCalled = false
        private set
    var onCancelCalled = false
        private set
    var onFinishCalled = false
        private set

    var typeListener: TypeListener? = null

    override var listener: ShadowCountdownTimer.Listener? = null

    override fun start() {
        onStartCalled = true
        invokeListener()
    }

    override fun cancel() {
        onCancelCalled = true
    }

    private fun invokeListener() {
        when (val listenerType = typeListener) {
            is OnFinish -> {
                onFinishCalled = true
                listener?.onFinish()
            }

            is OnTick -> {
                listener?.onTick(listenerType.millisUntilFinished)
            }

            null -> Unit
        }
    }

    sealed class TypeListener {
        data class OnTick(val millisUntilFinished: Long) : TypeListener()
        data object OnFinish : TypeListener()
    }
}