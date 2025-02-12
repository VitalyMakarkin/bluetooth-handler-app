package com.example.bluetoothhandlerapp.core.datasource

import kotlinx.coroutines.flow.Flow

interface DeviceLogsDataSource {
    fun observeAll(): Flow<List<String>>
    suspend fun insert(log: String)
    suspend fun clearAll()
}