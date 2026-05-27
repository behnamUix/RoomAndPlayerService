package com.behnamuix.myapplication04.data.local.repository.comment

import com.behnamuix.myapplication04.data.local.db.dao.CommentDao
import com.behnamuix.myapplication04.data.local.db.model.Comment
import com.behnamuix.myapplication04.data.local.db.model.mapper.toComment
import com.behnamuix.myapplication04.data.local.db.model.mapper.toCommentEntity

class CommentRepositoryImpl(private val commentDao: CommentDao) : CommentRepository {
    override suspend fun addComment(comment: Comment) {
        commentDao.addComment(comment.toCommentEntity())
    }

    override suspend fun getComments(): List<Comment> {
        return commentDao.getComments().map {
            it.toComment()
        }
    }

    override suspend fun removeComment(comment: Comment) {
        commentDao.removeComment(comment.toCommentEntity())
    }
}