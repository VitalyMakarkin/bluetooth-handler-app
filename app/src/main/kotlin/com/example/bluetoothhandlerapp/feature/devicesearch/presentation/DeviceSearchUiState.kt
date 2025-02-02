package com.example.bluetoothhandlerapp.feature.devicesearch.presentation

import com.example.bluetoothhandlerapp.core.model.CachedDevice
import com.example.bluetoothhandlerapp.core.model.ScannedDevice

data class DeviceSearchUiState(
    val scannedDevices: List<ScannedDevice> = emptyList(),
    val cachedDevices: List<CachedDevice> = emptyList(),
    val isLoading: Boolean = false,
)