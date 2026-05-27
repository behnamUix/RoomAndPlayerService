package com.behnamuix.myapplication04.notification.model

import androidx.annotation.DrawableRes

data class Notif(
    val title: String,
    val desc: String,
    @DrawableRes val icon: Int
)
