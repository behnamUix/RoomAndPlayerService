package com.behnamuix.myapplication04.data.local.repository.comment

import com.behnamuix.myapplication04.data.local.db.model.Comment
import kotlinx.coroutines.flow.Flow

interface CommentRepository {

    suspend fun addComment(comment: Comment)

    suspend fun getComments(): List<Comment>

    suspend fun removeComment(comment: Comment)
}