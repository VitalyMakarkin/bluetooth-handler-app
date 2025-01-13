package com.example.bluetoothhandlerapp.core.data.repository

import com.example.bluetoothhandlerapp.core.model.CachedDevice
import kotlinx.coroutines.flow.Flow

interface CachedDevicesRepository {
    fun observeAll(): Flow<List<CachedDevice>>
    fun observeByAddress(address: String): Flow<CachedDevice?>
    suspend fun upsert(device: CachedDevice)
    suspend fun clearAll()
}