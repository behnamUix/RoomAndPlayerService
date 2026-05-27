package com.behnamuix.myapplication04.notification.viewModel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import com.behnamuix.myapplication04.notification.repository.NotificationRepository

@RequiresApi(Build.VERSION_CODES.O)
class NotifViewModel(

    private val repo: NotificationRepository,
) :
    ViewModel() {
    fun sendNotifLike() {
        repo.showNotifLikes()
    }


    fun sendNotifShare() {
        repo.showNotifShare()
    }

    fun startWorker() {
        repo.startWork()
    }


}