package com.behnamuix.myapplication04.viewModel.ui.comment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.behnamuix.myapplication04.data.local.db.model.Comment
import com.behnamuix.myapplication04.data.local.db.model.Post
import com.behnamuix.myapplication04.data.local.repository.comment.CommentRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CommentListViewModel(private val repo: CommentRepository) : ViewModel() {
    private val _comments = MutableStateFlow<List<Comment>>(emptyList())
    val comments: StateFlow<List<Comment>> = _comments.asStateFlow()
    init {

        loadComment()
    }
    fun loadComment() {
        viewModelScope.launch {
            _comments.value = repo.getComments()
        }
    }

    fun removeComment(comment: Comment) {
        viewModelScope.launch {
            repo.removeComment(comment)
            loadComment()
        }
    }

}