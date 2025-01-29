package com.example.bluetoothhandlerapp.core.datasource

import com.example.bluetoothhandlerapp.core.model.ScannedDevice
import kotlinx.coroutines.flow.Flow

interface ScannedDevicesDataSource {
    fun observeAll(): Flow<List<ScannedDevice>>
    suspend fun getByAddressOrNull(address: String): ScannedDevice?
    suspend fun addOrUpdate(device: ScannedDevice)
    suspend fun clearAll()
}