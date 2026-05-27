package com.behnamuix.myapplication04.media

import android.content.Context
import android.media.MediaPlayer
import com.behnamuix.myapplication04.R

fun getMediaPlayer(context: Context) =
    MediaPlayer.create(context, R.raw.music).apply {
        isLooping = true

    }