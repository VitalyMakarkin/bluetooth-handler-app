package com.example.bluetoothhandlerapp.feature.devicesearch.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bluetoothhandlerapp.core.data.repository.ScannedDevicesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.minus
import javax.inject.Inject

@HiltViewModel
class DeviceSearchViewModel @Inject constructor(
    private val repository: ScannedDevicesRepository,
) : ViewModel() {

    val uiState: StateFlow<DeviceSearchUiState> = repository.observeAll(
        maxLastScannedAt = Clock.System.now().minus(4, DateTimeUnit.SECOND),
        maxSignalLevel = 6,
    )
        .map { devices ->
            DeviceSearchUiState(
                scannedDevices = devices,
                isLoading = false,
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = DeviceSearchUiState(isLoading = true)
        )
}