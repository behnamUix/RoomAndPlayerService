package com.behnamuix.myapplication04.base

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import androidx.annotation.RequiresApi
import com.behnamuix.myapplication04.di.daoModule
import com.behnamuix.myapplication04.di.databaseModule
import com.behnamuix.myapplication04.di.mediaModule
import com.behnamuix.myapplication04.di.permModule
import com.behnamuix.myapplication04.di.repositoryModule
import com.behnamuix.myapplication04.di.viewModelModule
import com.behnamuix.myapplication04.di.workManagerModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApp : Application() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MyApp)
            modules(
                listOf(
                    databaseModule,
                    viewModelModule,
                    repositoryModule,
                    daoModule,
                    permModule,
                    workManagerModule,
                    mediaModule

                )
            )
        }

        val channelMusic = NotificationChannel(
            "music",
            "اعلان لایک ",
            NotificationManager.IMPORTANCE_DEFAULT
        ).apply {

            description = "اعلان مربوط به لایک های کاربر"
        }

        val channelLikes = NotificationChannel(
            "like",
            "اعلان لایک ",
            NotificationManager.IMPORTANCE_DEFAULT
        ).apply {

            description = "اعلان مربوط به لایک های کاربر"
        }

        val channelShare = NotificationChannel(
            "share",
            "اعلان اشتراک گزاری",
            NotificationManager.IMPORTANCE_DEFAULT
        ).apply {
            description="اعلان مربوط به اشتراک گزاری"
        }

        val nm = getSystemService(NotificationManager::class.java)
        nm.createNotificationChannels(listOf(channelLikes, channelShare,channelMusic))
    }
}