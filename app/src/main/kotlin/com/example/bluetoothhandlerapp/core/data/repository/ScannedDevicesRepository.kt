package com.example.bluetoothhandlerapp.core.data.repository

import com.example.bluetoothhandlerapp.core.model.ScannedDevice
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.Instant

interface ScannedDevicesRepository {
    fun observeAll(maxLastScannedAt: Instant, maxSignalLevel: Int): Flow<List<ScannedDevice>>
    suspend fun add(device: ScannedDevice)
    suspend fun clearAll()
}