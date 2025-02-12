package com.example.bluetoothhandlerapp.feature.devicelogs.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bluetoothhandlerapp.feature.devicelogs.domain.DeviceLogsInteractor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class DeviceLogsViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val interactor: DeviceLogsInteractor,
) : ViewModel() {
    val uiState: StateFlow<DeviceLogsUiState> = interactor.observeLogs()
        .map { DeviceLogsUiState(logs = it) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = DeviceLogsUiState(),
        )
}