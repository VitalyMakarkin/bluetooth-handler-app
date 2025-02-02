package com.example.bluetoothhandlerapp.feature.devicelogs.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class DeviceLogsViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
): ViewModel() {
    val uiState: StateFlow<DeviceLogsUiState> = emptyFlow<DeviceLogsUiState>()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = DeviceLogsUiState(),
        )
}