package com.behnamuix.myapplication04.viewModel.ui.post

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.behnamuix.myapplication04.data.local.db.model.Post
import com.behnamuix.myapplication04.data.local.repository.post.PostRepository
import kotlinx.coroutines.launch

class PostAddViewModel(
    private val postRepo: PostRepository,
) : ViewModel() {

    val imageUri = mutableStateOf("")
    val caption = mutableStateOf("")
    val username = mutableStateOf("")
    val createdAt = mutableStateOf("")

    fun addPost() {

            val post = Post (
                    imageUri = imageUri.value,
            caption = caption.value,
            username = username.value,
            createdAt = createdAt.value
            )
            viewModelScope.launch {
                postRepo.addPost(post)
            }

    }



}