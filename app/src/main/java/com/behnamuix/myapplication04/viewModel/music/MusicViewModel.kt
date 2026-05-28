package com.behnamuix.myapplication04.viewModel.music

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.behnamuix.myapplication04.repository.MusicServiceRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class MusicViewModel(private val repo: MusicServiceRepository) : ViewModel() {
    private val _playerState = MutableStateFlow<PlayerState>(PlayerState.None)
    val playerState: StateFlow<PlayerState> = _playerState.asStateFlow()

    var duraton = mutableStateOf("")
    var pose = 0


    @SuppressLint("NewApi")
    fun toggleMusic() {
        if (_playerState.value == PlayerState.Playing) {
            repo.stopService()
            _playerState.value = PlayerState.Stoped

        } else {
            repo.startService()
            _playerState.value = PlayerState.Playing
            duraton.value = repo.duration.toString()
            pose = repo.currentPosition.value


        }
    }


    enum class PlayerState {
        Stoped,
        Playing,
        None
    }
}