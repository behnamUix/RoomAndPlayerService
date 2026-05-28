package com.behnamuix.myapplication04.data.local.repository.post

import com.behnamuix.myapplication04.data.local.db.model.Post

interface PostRepository {
    suspend fun addPost(post: Post)
    suspend fun getPosts(): List<Post>
    suspend fun removePost(post: Post)


}