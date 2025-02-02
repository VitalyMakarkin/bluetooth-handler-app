package com.example.bluetoothhandlerapp.feature.servicedetails.presentation

import com.example.bluetoothhandlerapp.core.model.DeviceCharacteristic
import com.example.bluetoothhandlerapp.core.model.DeviceService

data class ServiceDetailsUiState(
    val serviceDetails: DeviceService? = null,
    val characteristics: List<DeviceCharacteristic> = emptyList(),
    val isLoading: Boolean = false,
)