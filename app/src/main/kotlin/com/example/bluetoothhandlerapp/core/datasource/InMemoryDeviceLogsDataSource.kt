package com.example.bluetoothhandlerapp.core.datasource

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import javax.inject.Inject

class InMemoryDeviceLogsDataSource @Inject constructor() : DeviceLogsDataSource {

    private val _logsFlow: MutableStateFlow<List<String>> = MutableStateFlow(emptyList())
    private val mutex = Mutex()

    override fun observeAll(): Flow<List<String>> {
        return _logsFlow
    }

    override suspend fun insert(log: String) {
        mutex.withLock { _logsFlow.update { it.plus(log) } }
    }

    override suspend fun clearAll() {
        mutex.withLock { _logsFlow.update { emptyList() } }
    }
}