package com.example.finalplayground

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class PlaygroundApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {
        private var instance: PlaygroundApplication? = null
        fun get(): PlaygroundApplication? = instance
    }
}
