package com.updown.app

import android.app.Application
import android.util.Log

class UpDownApp : Application() {
    override fun onCreate() {
        super.onCreate()
        Log.d("UpDownApp", "App initialized")
    }
}
