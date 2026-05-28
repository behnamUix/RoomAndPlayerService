package com.behnamuix.myapplication04.data.local.repository.post

import com.behnamuix.myapplication04.data.local.db.dao.PostDao
import com.behnamuix.myapplication04.data.local.db.model.Post
import com.behnamuix.myapplication04.data.local.db.model.mapper.toPost
import com.behnamuix.myapplication04.data.local.db.model.mapper.toPostEntity

class PostRepositoryImpl(private val dao: PostDao) : PostRepository {
    override suspend fun addPost(post: Post) {
        dao.addPost(
            post.toPostEntity()
        )
    }

    override suspend fun getPosts(): List<Post> {
        return dao.getPosts().map { list ->
            list.toPost()
        }
    }


    override suspend fun removePost(post: Post) {
        dao.removePost(post.toPostEntity())
    }




}