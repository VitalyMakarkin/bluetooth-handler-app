package com.example.bluetoothhandlerapp.feature.observedevices.ui

import com.example.bluetoothhandlerapp.core.model.ScannedDevice

data class ObserveDevicesUiState(
    val scannedDevices: List<ScannedDevice> = emptyList(),
    val isLoading: Boolean = false,
)