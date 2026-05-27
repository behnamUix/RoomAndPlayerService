package com.behnamuix.myapplication04.viewModel.ui.comment

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.behnamuix.myapplication04.data.local.db.model.Comment
import com.behnamuix.myapplication04.data.local.repository.comment.CommentRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

class CommentAddViewModel(private val repo: CommentRepository) : ViewModel() {

    val _send = MutableSharedFlow<Boolean>()
    var send : SharedFlow<Boolean> = _send

    val profile = mutableStateOf("")
    var username = mutableStateOf("")
    var msg = mutableStateOf("")

    fun addComment() {
        val comment = Comment(
            profile = profile.value,
            username = username.value,
            msg = msg.value
        )
        viewModelScope.launch {
            repo.addComment(comment)
            _send.emit(true)


        }
    }
}