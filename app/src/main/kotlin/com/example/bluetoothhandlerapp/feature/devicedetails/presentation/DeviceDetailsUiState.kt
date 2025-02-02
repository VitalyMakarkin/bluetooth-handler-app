package com.example.bluetoothhandlerapp.feature.devicedetails.presentation

import com.example.bluetoothhandlerapp.core.model.CachedDevice
import com.example.bluetoothhandlerapp.core.model.DeviceService

data class DeviceDetailsUiState(
    val device: CachedDevice? = null,
    val services: List<DeviceService> = emptyList(),
    val isLoading: Boolean = false,
)
