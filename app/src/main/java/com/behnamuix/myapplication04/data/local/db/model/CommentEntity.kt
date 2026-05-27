package com.behnamuix.myapplication04.data.local.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "comments")
data class CommentEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val profile: String,
    val username: String,
    val msg: String
)

data class Comment(
    val id: Int=0,
    val profile: String,
    val username: String,
    val msg: String=""
)
