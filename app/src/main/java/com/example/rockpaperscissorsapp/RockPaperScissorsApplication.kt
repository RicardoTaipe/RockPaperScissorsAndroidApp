package com.example.rockpaperscissorsapp

import android.app.Application
import com.example.rockpaperscissorsapp.di.AppContainer
import com.example.rockpaperscissorsapp.di.DefaultAppContainer

//TODO review this https://github.com/android/architecture-samples/tree/dev-dagger/app/src
//manual DI with tests
open class RockPaperScissorsApplication : Application() {
    /** AppContainer instance used by the rest of classes to obtain dependencies */
    val container: AppContainer by lazy {
        initializeAppContainer()
    }

    open fun initializeAppContainer(): AppContainer = DefaultAppContainer()

}