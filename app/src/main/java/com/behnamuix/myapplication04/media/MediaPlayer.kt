package com.behnamuix.myapplication04.media

import android.content.Context
import android.media.MediaPlayer
import android.os.Build
import androidx.annotation.RequiresApi
import java.io.IOException


@RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
fun getMediaPlayer(): MediaPlayer? {
    val media = MediaPlayer().apply {
        isLooping = true
        setDataSource("https://dl3.songsara.net/FRE/2022/19/Secession%20Studios%20-%20Frontier%20%282022%29%20SONGSARA.NET/03%20Frontier.mp3")
    }
    //hatman bayad ghabl az start amade sazi shavad
    media.prepareAsync()

    return media
}