package com.example.bluetoothhandlerapp.core.datasource

import com.example.bluetoothhandlerapp.core.datasource.model.ScannedService
import kotlinx.coroutines.flow.Flow

interface ScannedServicesDataSource {
    fun observeAllByDeviceAddress(deviceAddress: String): Flow<List<ScannedService>>
    suspend fun addOrIgnore(deviceAddress: String, service: ScannedService)
    suspend fun clearAllByDeviceAddress(deviceAddress: String)
    suspend fun clearAll()
}