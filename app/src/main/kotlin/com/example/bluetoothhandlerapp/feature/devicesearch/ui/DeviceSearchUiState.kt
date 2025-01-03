package com.example.bluetoothhandlerapp.feature.devicesearch.ui

import com.example.bluetoothhandlerapp.core.model.ScannedDevice

data class DeviceSearchUiState(
    val scannedDevices: List<ScannedDevice> = emptyList(),
    val isLoading: Boolean = false,
)