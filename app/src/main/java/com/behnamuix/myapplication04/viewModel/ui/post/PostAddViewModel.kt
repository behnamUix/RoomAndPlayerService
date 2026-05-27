package com.behnamuix.myapplication04.viewModel.ui.post

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.behnamuix.myapplication04.data.local.db.model.Post
import com.behnamuix.myapplication04.data.local.repository.post.PostRepository
import com.behnamuix.myapplication04.utils.ext.checkNetwork
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PostAddViewModel(
    private val postRepo: PostRepository,
    private val context: Context
) : ViewModel() {

    val imageUri = mutableStateOf("")
    val caption = mutableStateOf("")
    val username = mutableStateOf("")
    val createdAt = mutableStateOf("")

    val _state = MutableStateFlow<Boolean?>(null)
    val state: StateFlow<Boolean?> = _state
    fun addPost() {
        if (context.checkNetwork()) {
            _state.value=true
            val post = Post (
                    imageUri = imageUri.value,
            caption = caption.value,
            username = username.value,
            createdAt = createdAt.value
            )
            viewModelScope.launch {
                postRepo.addPost(post)

            }
        } else {
            _state.value=false
        }

    }

    fun updatePost(id: Int, imgUri: String, caption: String, username: String) {
        val post = Post(
            id = id,
            caption = caption,
            username = username,
        )
        viewModelScope.launch {
            postRepo.updatePost(post)
        }
    }

}