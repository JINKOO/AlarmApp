package com.jinkweonko.alarm

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class AlarmApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initTimerLogger()
    }

    private fun initTimerLogger() {
        Timber.plant(Timber.DebugTree())
    }
}