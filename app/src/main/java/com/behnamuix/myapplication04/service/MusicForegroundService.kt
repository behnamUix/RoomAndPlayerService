package com.behnamuix.myapplication04.service

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder
import com.behnamuix.myapplication04.R
import com.behnamuix.myapplication04.media.getMediaPlayer
import com.behnamuix.myapplication04.notification.createNotif
import com.behnamuix.myapplication04.notification.model.Notif

class MusicForegroundService : Service() {

    private var mediaPlayer: MediaPlayer? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val notif = createNotif(
            Notif("سررویس", "موزیک در حال اجرا است", R.drawable.ic_launcher_foreground),
            "music"
        )
        startForeground(1, notif)

        if (mediaPlayer == null) {
            mediaPlayer = getMediaPlayer(this)
        }

        mediaPlayer?.start()

        return START_STICKY
    }


    override fun onDestroy() {
        mediaPlayer?.release()
        mediaPlayer = null
        super.onDestroy()
    }
    override fun onBind(intent: Intent?): IBinder? = null
}
