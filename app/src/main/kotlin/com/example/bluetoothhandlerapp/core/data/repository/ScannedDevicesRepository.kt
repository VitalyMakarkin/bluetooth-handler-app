package com.example.bluetoothhandlerapp.core.data.repository

import com.example.bluetoothhandlerapp.core.model.ScannedDevice
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.Instant

interface ScannedDevicesRepository {
    fun observeAll(maxLastUpdatedAt: Instant): Flow<List<ScannedDevice>>
    suspend fun getByAddressOrNull(address: String): ScannedDevice?
    suspend fun addOrUpdate(device: ScannedDevice)
    suspend fun clearAll()
}