package com.example.bluetoothhandlerapp.core.datasource.model

data class ScannedService(
    val uuid: String,
    val characteristicUuids: Set<String>,
)