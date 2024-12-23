package com.example.rockpaperscissorsapp.countdown

import android.os.CountDownTimer


class MyCountDownTimer(millisInFuture: Long, countDownInterval: Long) :
    ShadowCountdownTimer {
    private var countDownTimer: CountDownTimer =
        object : CountDownTimer(millisInFuture, countDownInterval) {
            override fun onTick(millisUntilFinished: Long) {
                listener?.onTick(millisUntilFinished)
            }

            override fun onFinish() {
                listener?.onFinish()
            }
        }

    override var listener: ShadowCountdownTimer.Listener? = null

    override fun start() {
        countDownTimer.start()
    }

    override fun cancel() {
        countDownTimer.cancel()
    }

    companion object {
        const val ONE_SECOND = 1000L
        const val COUNTDOWN_TIME = 3000L
        const val TOTAL_TIME_TIMER = COUNTDOWN_TIME / ONE_SECOND
    }
}
