package com.behnamuix.myapplication04.data.local.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.behnamuix.myapplication04.data.local.db.model.PostEntity

@Dao
interface PostDao {
    @Insert
    suspend fun addPost(post: PostEntity)

    @Query("select * from posts")
    suspend fun getPosts(): List<PostEntity>

    @Query("select * from posts where id=:id")
    suspend fun getPostById(id: Int): PostEntity

    @Delete
    suspend fun removePost(post: PostEntity)

    @Update
    suspend fun updatePost(post: PostEntity)


}