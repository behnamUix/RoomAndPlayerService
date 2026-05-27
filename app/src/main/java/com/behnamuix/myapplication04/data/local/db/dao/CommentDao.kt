package com.behnamuix.myapplication04.data.local.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.behnamuix.myapplication04.data.local.db.model.CommentEntity
import com.behnamuix.myapplication04.data.local.db.model.PostEntity

@Dao
interface CommentDao {
    @Insert
    suspend fun addComment(comment: CommentEntity)

    @Query("select * from comments")
    suspend fun getComments(): List<CommentEntity>

    @Delete
    suspend fun removeComment(comment: CommentEntity)
}