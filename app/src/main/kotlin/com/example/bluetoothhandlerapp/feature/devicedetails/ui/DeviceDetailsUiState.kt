package com.example.bluetoothhandlerapp.feature.devicedetails.ui

import com.example.bluetoothhandlerapp.core.model.CachedDevice

data class DeviceDetailsUiState(
    val device: CachedDevice? = null,
    val isLoading: Boolean = false,
)