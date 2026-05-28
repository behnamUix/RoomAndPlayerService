package com.behnamuix.myapplication04.repository

import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.util.Log
import com.behnamuix.myapplication04.service.MusicForegroundService
import com.behnamuix.myapplication04.utils.formatSongTime
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import okhttp3.Dispatcher

class MusicServiceRepository(private val context: Context, val media: MediaPlayer) {
    var duration = ""

    fun startService() {
        val intent =
            Intent(
                context,
                MusicForegroundService::class.java
            )
        context.startForegroundService(intent)
        Log.d("LOG", media.duration.toString())
        duration = formatSongTime(media)


    }



    fun stopService() {
        val intent = Intent(context, MusicForegroundService::class.java)
        context.stopService(intent)
    }


}