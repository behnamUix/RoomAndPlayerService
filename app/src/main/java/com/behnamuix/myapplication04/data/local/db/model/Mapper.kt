package com.behnamuix.myapplication04.data.local.db.model.mapper

import com.behnamuix.myapplication04.data.local.db.model.Comment
import com.behnamuix.myapplication04.data.local.db.model.CommentEntity
import com.behnamuix.myapplication04.data.local.db.model.Post
import com.behnamuix.myapplication04.data.local.db.model.PostEntity

fun PostEntity.toPost(): Post {
    return Post(
        id = id,
        imageUri = imageUri,
        caption = caption,
        username = username,
        createdAt = createdAt
    )
}

fun Post.toPostEntity(): PostEntity {
    return PostEntity(
        id = id,
        imageUri = imageUri,
        caption = caption,
        username = username,
        createdAt = createdAt
    )
}

fun CommentEntity.toComment(): Comment {
    return Comment(
        id = id,
        profile = profile,
        username = username,
        msg = msg
    )
}

fun Comment.toCommentEntity(): CommentEntity {
    return CommentEntity(
        id = id,
        profile = profile,
        username = username,
        msg = msg
    )
}