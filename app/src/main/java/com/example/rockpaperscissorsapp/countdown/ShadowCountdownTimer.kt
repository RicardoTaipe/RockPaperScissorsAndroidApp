package com.example.rockpaperscissorsapp.countdown

interface ShadowCountdownTimer {
    var listener: Listener?
    fun start()
    fun cancel()
    interface Listener {
        fun onTick(millisUntilFinished: Long)
        fun onFinish()
    }
}