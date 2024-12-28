package com.example.bluetoothhandlerapp.feature.observedevices.ui

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ObserveDevicesViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(ObserveDevicesUiState())
    val uiState: StateFlow<ObserveDevicesUiState> = _uiState.asStateFlow()
}