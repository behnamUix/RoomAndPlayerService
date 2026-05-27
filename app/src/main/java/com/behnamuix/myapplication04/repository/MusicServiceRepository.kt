package com.behnamuix.myapplication04.repository

import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import com.behnamuix.myapplication04.service.MusicForegroundService

class MusicServiceRepository(private val context: Context) {
    @RequiresApi(Build.VERSION_CODES.O)
    fun startService() {
        val intent =
            Intent(context,
                MusicForegroundService::class.java
            )
        context.startForegroundService(intent)
    }

    fun stopService() {
        val intent = Intent(context, MusicForegroundService::class.java)
        context.stopService(intent)
    }
}