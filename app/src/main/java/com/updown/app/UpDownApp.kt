package com.updown.app

import android.app.Application
import android.util.Log
import com.yausername.youtubedl_android.YoutubeDL
import com.yausername.youtubedl_android.YoutubeDLException

class UpDownApp : Application() {
    override fun onCreate() {
        super.onCreate()
        try {
            YoutubeDL.getInstance().init(this)
            // FFmpeg.getInstance().init(this) // Initialize FFmpeg if you want to merge audio/video
            Log.d("UpDownApp", "YoutubeDL initialized successfully")
        } catch (e: YoutubeDLException) {
            Log.e("UpDownApp", "failed to initialize youtubedl-android", e)
        }
    }
}
