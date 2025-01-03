package com.example.bluetoothhandlerapp.core.model

import kotlinx.datetime.Instant

data class ScannedDevice(
    val address: String,
    val name: String,
    val scannedAt: Instant,
    val rssi: Int,
)
