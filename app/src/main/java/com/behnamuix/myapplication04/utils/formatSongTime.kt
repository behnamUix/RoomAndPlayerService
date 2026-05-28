package com.behnamuix.myapplication04.utils

import android.media.MediaPlayer

fun formatSongTime(mediaPlayer: MediaPlayer):String{
    val durationMillis = mediaPlayer.duration // زمان به میلی‌ثانیه
    val durationSeconds = durationMillis / 1000 // زمان به ثانیه

    val minutes = durationSeconds / 60
    val seconds = durationSeconds % 60
    return String.format("%02d:%02d", minutes, seconds) // "02:05"
}