package com.example.bluetoothhandlerapp.feature.observedevices.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bluetoothhandlerapp.core.model.Device
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ObserveDevicesViewModel @Inject constructor() : ViewModel() {
    private val _uiState = MutableStateFlow(ObserveDevicesUiState())
    val uiState: StateFlow<ObserveDevicesUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            val devices = listOf(
                Device(id = 1, macAddress = "", name = "Device 1"),
                Device(id = 2, macAddress = "", name = "Device 2"),
            )
            _uiState.emit(ObserveDevicesUiState(devices = devices))
        }
    }
}