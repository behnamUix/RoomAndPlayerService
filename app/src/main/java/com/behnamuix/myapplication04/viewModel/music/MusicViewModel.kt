package com.behnamuix.myapplication04.viewModel.music

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModel
import com.behnamuix.myapplication04.repository.MusicServiceRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class MusicViewModel(private val repo: MusicServiceRepository) : ViewModel() {
    private val _isPlaying = MutableStateFlow<PlayerState>(PlayerState.None)
    val isPlaying: StateFlow<PlayerState> = _isPlaying.asStateFlow()

    @SuppressLint("NewApi")
    fun toggleMusic() {
        if (_isPlaying.value == PlayerState.Playing) {
            repo.stopService()
            _isPlaying.value = PlayerState.Stoped

        } else {
            repo.startService()
            _isPlaying.value = PlayerState.Playing
        }
    }

    enum class PlayerState {
        Stoped,
        Playing,
        None
    }
}