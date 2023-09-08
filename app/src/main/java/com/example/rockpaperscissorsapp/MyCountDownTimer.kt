package com.example.rockpaperscissorsapp

import android.os.CountDownTimer


class MyCountDownTimer(millisInFuture: Long, countDownInterval: Long) :
    CountDownTimer(millisInFuture, countDownInterval) {

    var onTickListener: ((millisUntilFinished: Long) -> Unit)? = null
    var onFinishListener: (() -> Unit)? = null


    override fun onTick(millisUntilFinished: Long) {
        onTickListener?.invoke(millisUntilFinished)
    }

    override fun onFinish() {
        onFinishListener?.invoke()
    }
}
