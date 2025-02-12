package com.example.bluetoothhandlerapp.core.data.repository

import kotlinx.coroutines.flow.Flow

interface DeviceLogsRepository {
    fun observeAll(): Flow<List<String>>
    suspend fun insert(log: String)
    suspend fun clearAll()
}