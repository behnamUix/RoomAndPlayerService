package com.behnamuix.myapplication04.notification

import android.Manifest
import android.app.Notification
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.behnamuix.myapplication04.R
import com.behnamuix.myapplication04.notification.model.Notif

fun Context.showNotification(
    notifModel: Notif,
    channelId: String
) {
    val notification =
        NotificationCompat.Builder(this, channelId)
            .setSmallIcon(notifModel.icon)
            .setContentTitle(notifModel.title)
            .setContentText(notifModel.desc)
            .setAutoCancel(true)
            .build()

    if (ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.POST_NOTIFICATIONS
        ) != PackageManager.PERMISSION_GRANTED
    ) {

        return
    }
    NotificationManagerCompat.from(this).notify(
        System.currentTimeMillis().toInt().toString(),
        1,
        notification,
    )


}

fun Context.createNotif(
    notifModel: Notif,
    channelId: String
): Notification =
    NotificationCompat.Builder(this, channelId)
        .setSmallIcon(notifModel.icon)
        .setContentTitle(notifModel.title)
        .setContentText(notifModel.desc)
        .setAutoCancel(true)
        .setSilent(true)
        .build()

