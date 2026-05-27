package com.behnamuix.myapplication04.notification.repository

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.behnamuix.myapplication04.R
import com.behnamuix.myapplication04.service.NotificationWorkManager
import com.behnamuix.myapplication04.notification.model.Notif
import com.behnamuix.myapplication04.notification.showNotification
import java.time.Duration
import java.time.LocalDateTime
import java.util.concurrent.TimeUnit

@RequiresApi(Build.VERSION_CODES.O)
class NotificationRepository(
    private val context: Context,
    private val workManager: WorkManager
) {
    private val data = workDataOf(
        "title" to "لایک",
        "desc" to "کاربر مورد نظر لایک شد"
    )

    fun showNotifLikes() {
        val notif =
            Notif("لایک", "کاربر مورد نظر لایک شد", R.drawable.ic_launcher_foreground)
        context.showNotification(
            notif,
            "like"
        )

    }

    fun showNotifShare() {
        val notif = Notif("اشتراک گزاری", "پست اشتراک گزاری شد", R.drawable.ic_launcher_foreground)
        context.showNotification(
            notif,
            "share"
        )
    }


    fun minuteUntilNext(): Long {
        val now = LocalDateTime.now()
        var next = now.withHour(23).withMinute(45).withSecond(0).withNano(0)
        if (next <= now) {
            next = next.plusDays(1)

        }
        return Duration.between(now, next).toMinutes()
    }

    fun startWork() {
        val dailyRequest =
            PeriodicWorkRequestBuilder<NotificationWorkManager>(
                repeatInterval = 1, TimeUnit.DAYS
            )
                .setInitialDelay(minuteUntilNext(), TimeUnit.SECONDS)
                .setInputData(data)
                .build()
        workManager.enqueueUniquePeriodicWork(
            "day_notif",
            ExistingPeriodicWorkPolicy.UPDATE,
            dailyRequest
        )
    }

}