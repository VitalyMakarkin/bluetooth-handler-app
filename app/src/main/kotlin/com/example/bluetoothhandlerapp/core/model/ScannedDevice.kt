package com.example.bluetoothhandlerapp.core.model

import kotlinx.datetime.Instant

data class ScannedDevice(
    val id: Long,
    val macAddress: String,
    val name: String,
    val scannedAt: Instant,
    val signalLevel: Int,
)
