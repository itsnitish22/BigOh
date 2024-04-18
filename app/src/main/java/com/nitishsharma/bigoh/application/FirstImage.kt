package com.nitishsharma.bigoh.application

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class FirstImage : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}