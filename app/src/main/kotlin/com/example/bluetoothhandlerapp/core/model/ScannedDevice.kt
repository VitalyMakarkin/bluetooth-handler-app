package com.example.bluetoothhandlerapp.core.model

import kotlinx.datetime.Instant

data class ScannedDevice(
    val address: String,
    val name: String,
    val updatedAt: Instant,
    val rssi: Int,
)
