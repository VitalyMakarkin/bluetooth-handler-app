package com.example.bluetoothhandlerapp.core.model

import kotlinx.datetime.Instant

data class CachedDevice(
    val address: String,
    val name: String,
    val scannedAt: Instant,
)