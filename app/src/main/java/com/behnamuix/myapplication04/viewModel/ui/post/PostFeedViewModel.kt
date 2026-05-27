package com.behnamuix.myapplication04.viewModel.ui.post

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.behnamuix.myapplication04.data.local.db.model.Post
import com.behnamuix.myapplication04.data.local.repository.post.PostRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PostFeedViewModel(private val postRepo: PostRepository) : ViewModel() {
    private val _posts = MutableStateFlow<List<Post>>(emptyList())
    val posts: StateFlow<List<Post>> = _posts.asStateFlow()

    fun loadPost() {
        viewModelScope.launch {
            _posts.value = postRepo.getPosts()

        }
    }

    fun removePost(post: Post) {
        viewModelScope.launch {
            postRepo.removePost(post)
            loadPost()
        }
    }

}