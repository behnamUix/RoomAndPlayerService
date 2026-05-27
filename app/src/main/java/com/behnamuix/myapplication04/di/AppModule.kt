package com.behnamuix.myapplication04.di

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.Room
import androidx.work.WorkManager
import com.behnamuix.myapplication04.base.permissionManager.PermissionManager
import com.behnamuix.myapplication04.data.local.db.config.InstagramDatabase
import com.behnamuix.myapplication04.data.local.repository.comment.CommentRepository
import com.behnamuix.myapplication04.data.local.repository.comment.CommentRepositoryImpl
import com.behnamuix.myapplication04.data.local.repository.post.PostRepository
import com.behnamuix.myapplication04.data.local.repository.post.PostRepositoryImpl
import com.behnamuix.myapplication04.media.getMediaPlayer
import com.behnamuix.myapplication04.notification.repository.NotificationRepository
import com.behnamuix.myapplication04.notification.viewModel.NotifViewModel
import com.behnamuix.myapplication04.repository.MusicServiceRepository
import com.behnamuix.myapplication04.service.NotificationWorkManager
import com.behnamuix.myapplication04.viewModel.music.MusicViewModel
import com.behnamuix.myapplication04.viewModel.permission.PermissionManagerViewModel
import com.behnamuix.myapplication04.viewModel.ui.comment.CommentAddViewModel
import com.behnamuix.myapplication04.viewModel.ui.comment.CommentListViewModel
import com.behnamuix.myapplication04.viewModel.ui.post.PostAddViewModel
import com.behnamuix.myapplication04.viewModel.ui.post.PostFeedViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val databaseModule = module {
    single {
        Room.databaseBuilder(
            get(),
            InstagramDatabase::class.java,
            "instagram_database"
        )
            .fallbackToDestructiveMigration(false)

            .build()
    }
}


val permModule = module {
    single { PermissionManager(get()) }
}

val daoModule = module {
    single { get<InstagramDatabase>().commentDao() }
    single { get<InstagramDatabase>().postDao() }
}
val workManagerModule = module {
    single { WorkManager.getInstance(get()) }
    single { NotificationWorkManager(get(), get()) }
}
val mediaModule = module { single { getMediaPlayer(get()) } }

@RequiresApi(Build.VERSION_CODES.O)
val repositoryModule = module {
    single<CommentRepository> { CommentRepositoryImpl(get()) }
    single<PostRepository> { PostRepositoryImpl(get()) }

    single { NotificationRepository(get(), get()) }
    single { MusicServiceRepository(get()) }

}

@RequiresApi(Build.VERSION_CODES.O)
val viewModelModule = module {
    viewModel { MusicViewModel(get()) }
    viewModel { NotifViewModel(get()) }
    viewModel { MusicViewModel(get()) }
    viewModel { CommentAddViewModel(get()) }
    viewModel { PostFeedViewModel(get()) }
    viewModel { CommentListViewModel(get()) }
    viewModel { PostAddViewModel(get(), get()) }
    viewModel { PermissionManagerViewModel(get()) }
}