package com.example.bluetoothhandlerapp.core.data.repository

import com.example.bluetoothhandlerapp.core.datasource.DeviceLogsDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DefaultDeviceLogsRepository @Inject constructor(
    private val deviceLogsDataSource: DeviceLogsDataSource,
) : DeviceLogsRepository {
    override fun observeAll(): Flow<List<String>> {
        return deviceLogsDataSource.observeAll()
    }

    override suspend fun insert(log: String) {
        deviceLogsDataSource.insert(log)
    }

    override suspend fun clearAll() {
        deviceLogsDataSource.clearAll()
    }
}