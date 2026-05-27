package com.behnamuix.myapplication04.data.local.db.config

import androidx.room.Database
import androidx.room.RoomDatabase
import com.behnamuix.myapplication04.data.local.db.dao.CommentDao
import com.behnamuix.myapplication04.data.local.db.dao.PostDao
import com.behnamuix.myapplication04.data.local.db.model.CommentEntity
import com.behnamuix.myapplication04.data.local.db.model.PostEntity

@Database(entities = [PostEntity::class, CommentEntity::class], version = 1)
abstract class InstagramDatabase : RoomDatabase() {
    abstract fun postDao(): PostDao
    abstract fun commentDao(): CommentDao
}