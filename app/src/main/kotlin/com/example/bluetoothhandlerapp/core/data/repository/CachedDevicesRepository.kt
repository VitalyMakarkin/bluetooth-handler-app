package com.example.bluetoothhandlerapp.core.data.repository

import com.example.bluetoothhandlerapp.core.model.CachedDevice
import kotlinx.coroutines.flow.Flow

interface CachedDevicesRepository {
    fun observeByLocalId(localId: Int): Flow<CachedDevice>
    suspend fun upsert(device: CachedDevice)
    suspend fun clearAll()
}