package com.example.bluetoothhandlerapp.core.datasource

import com.example.bluetoothhandlerapp.core.model.ScannedDevice
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.datetime.Instant
import javax.inject.Inject

class InMemoryScannedDevicesDataSource @Inject constructor() : ScannedDevicesDataSource {

    private val _scannedDevicesFlow: MutableStateFlow<Map<String, ScannedDevice>> = MutableStateFlow(emptyMap())
    private val mutex = Mutex()

    override fun observeAll(maxLastScannedAt: Instant): Flow<List<ScannedDevice>> {
        return _scannedDevicesFlow.map { devices ->
            devices.filterValues { it.scannedAt >= maxLastScannedAt }.values.toList()
        }
    }

    override suspend fun addOrUpdate(device: ScannedDevice) {
        mutex.withLock {
            _scannedDevicesFlow.update { currentDevices ->
                currentDevices.toMutableMap().apply {
                    this[device.address] = device
                }
            }

        }
    }

    override suspend fun clearAll() {
        mutex.withLock { _scannedDevicesFlow.update { emptyMap() } }
    }
}