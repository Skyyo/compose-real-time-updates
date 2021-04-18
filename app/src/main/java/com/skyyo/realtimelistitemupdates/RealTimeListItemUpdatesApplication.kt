package com.skyyo.realtimelistitemupdates

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate

class RealTimeListItemUpdatesApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }
}