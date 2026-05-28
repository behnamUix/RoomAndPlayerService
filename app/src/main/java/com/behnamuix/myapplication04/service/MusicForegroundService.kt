package com.behnamuix.myapplication04.service

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.annotation.RequiresApi
import com.behnamuix.myapplication04.R
import com.behnamuix.myapplication04.media.getMediaPlayer
import com.behnamuix.myapplication04.notification.createNotif
import com.behnamuix.myapplication04.notification.model.Notif

class MusicForegroundService : Service() {

    private var mediaPlayer: MediaPlayer? = null

    @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val notif = createNotif(
            Notif("سررویس", "موزیک در حال اجرا است", R.drawable.ic_launcher_foreground),
            "music"
        )
        startForeground(1, notif)

        if (mediaPlayer == null) {
            mediaPlayer = getMediaPlayer()
        }
        Log.d("PLAYER", "loading")
        mediaPlayer?.setOnPreparedListener { mp ->
            mp.start()
            Log.d("PLAYER", "playing...")
        }


        return START_STICKY
    }


    override fun onDestroy() {
        mediaPlayer?.release()
        mediaPlayer = null
        super.onDestroy()
    }

    override fun onBind(intent: Intent?): IBinder? = null
}
