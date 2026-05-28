package com.behnamuix.myapplication04.viewModel.music

import android.annotation.SuppressLint
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.behnamuix.myapplication04.repository.MusicServiceRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class MusicViewModel(private val repo: MusicServiceRepository) : ViewModel() {
    private val _playerState = MutableStateFlow<PlayerState>(PlayerState.None)
    val playerState: StateFlow<PlayerState> = _playerState.asStateFlow()

    var duraton = mutableStateOf("")



    @SuppressLint("NewApi")
     fun toggleMusic() {
        if (_playerState.value == PlayerState.Playing) {
            repo.stopService()
            _playerState.value = PlayerState.Stoped


        } else {
            repo.startService()
            _playerState.value = PlayerState.Playing
            duraton.value = repo.duration.toString()



        }
    }


    enum class PlayerState {
        Stoped,
        Playing,
        None
    }
}