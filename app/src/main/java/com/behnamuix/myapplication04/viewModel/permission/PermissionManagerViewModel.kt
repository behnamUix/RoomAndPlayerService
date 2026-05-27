package com.behnamuix.myapplication04.viewModel.permission

import androidx.lifecycle.ViewModel
import com.behnamuix.myapplication04.base.permissionManager.PermissionManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class PermissionManagerViewModel(private val permManager: PermissionManager) : ViewModel() {
    private val _state = MutableStateFlow<PermissionState>(PermissionState.LOADING)
    val state: StateFlow<PermissionState> = _state.asStateFlow()

    fun checkPerm(perm: String) {
        _state.value = if (permManager.isGranted(perm)) {
            PermissionState.GRANTED
        } else {
            PermissionState.NEED_REQ
        }
    }

}

enum class PermissionState {
    LOADING, GRANTED, NEED_REQ
}