package com.behnamuix.myapplication04.service

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.behnamuix.myapplication04.R
import com.behnamuix.myapplication04.notification.model.Notif
import com.behnamuix.myapplication04.notification.showNotification

class NotificationWorkManager(
    context: Context,
    param: WorkerParameters,
) : Worker(context, param) {
    override fun doWork(): Result {
        val notif = Notif(
            inputData.getString("title") ?: "",
            inputData.getString("desc") ?: "",
            R.drawable.ic_launcher_foreground
        )

        applicationContext.showNotification(
            notif,
            "like"

        )
        return Result.success()
    }


}