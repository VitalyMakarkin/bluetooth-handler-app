package com.example.bluetoothhandlerapp.feature.devicesearch.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bluetoothhandlerapp.feature.devicesearch.domain.DeviceSearchInteractor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.minus
import javax.inject.Inject

@HiltViewModel
class DeviceSearchViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val interactor: DeviceSearchInteractor,
) : ViewModel() {

    val uiState: StateFlow<DeviceSearchUiState> = combine(
        interactor.observeCachedDevices(),
        interactor.observeScannedDevices(maxLastUpdatedAt = Clock.System.now().minus(4, DateTimeUnit.SECOND)),
    ) { cachedDevices, scannedDevices ->
        DeviceSearchUiState(
            cachedDevices = cachedDevices,
            scannedDevices = scannedDevices,
            isLoading = false,
        )
    }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = DeviceSearchUiState(isLoading = true)
        )
}