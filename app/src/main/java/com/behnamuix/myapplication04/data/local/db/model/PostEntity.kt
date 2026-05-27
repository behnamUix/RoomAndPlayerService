package com.behnamuix.myapplication04.data.local.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Entity(tableName = "posts")
data class PostEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val imageUri: String,
    val caption: String,
    val username: String,
    val createdAt: String,
)

data class Post(
    val id: Int=0,
    val imageUri: String="",
    val caption: String="",
    val username: String="",
    val createdAt: String="",
    
)
