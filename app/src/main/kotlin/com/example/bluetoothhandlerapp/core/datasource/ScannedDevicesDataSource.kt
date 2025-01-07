package com.example.bluetoothhandlerapp.core.datasource

import com.example.bluetoothhandlerapp.core.model.ScannedDevice
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.Instant

interface ScannedDevicesDataSource {
    fun observeAll(maxLastUpdatedAt: Instant): Flow<List<ScannedDevice>>
    suspend fun getByAddressOrNull(address: String): ScannedDevice?
    suspend fun addOrUpdate(device: ScannedDevice)
    suspend fun clearAll()
}