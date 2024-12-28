package com.example.rockpaperscissorsapp

import android.app.Application
import com.example.rockpaperscissorsapp.di.AppContainer
import com.example.rockpaperscissorsapp.di.DefaultAppContainer

open class RockPaperScissorsApplication : Application() {
    /** AppContainer instance used by the rest of classes to obtain dependencies */
    open lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer
    }

}