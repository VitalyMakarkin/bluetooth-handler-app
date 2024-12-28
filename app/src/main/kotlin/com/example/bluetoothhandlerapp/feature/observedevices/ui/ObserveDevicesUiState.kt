package com.example.bluetoothhandlerapp.feature.observedevices.ui

data class ObserveDevicesUiState(
    val devices: List<String> = emptyList(),
    val isLoading: Boolean = false,
)