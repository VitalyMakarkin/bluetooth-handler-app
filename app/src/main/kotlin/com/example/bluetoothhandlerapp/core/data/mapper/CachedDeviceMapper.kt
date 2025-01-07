package com.example.bluetoothhandlerapp.core.data.mapper

import com.example.bluetoothhandlerapp.core.database.model.CachedDeviceEntity
import com.example.bluetoothhandlerapp.core.model.CachedDevice

fun CachedDeviceEntity.mapToCachedDevice() = with(this) {
    CachedDevice(
        address = address,
        name = name,
        updatedAt = updatedAt,
    )
}

fun CachedDevice.mapToCachedDeviceEntity(localId: Int = 0) = with(this) {
    CachedDeviceEntity(
        localId = localId,
        address = address,
        name = name,
        updatedAt = updatedAt,
    )
}