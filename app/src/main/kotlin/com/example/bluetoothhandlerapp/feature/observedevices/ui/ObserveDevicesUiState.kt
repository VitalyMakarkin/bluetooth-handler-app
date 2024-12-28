package com.example.bluetoothhandlerapp.feature.observedevices.ui

import com.example.bluetoothhandlerapp.core.model.Device

data class ObserveDevicesUiState(
    val devices: List<Device> = emptyList(),
    val isLoading: Boolean = false,
)