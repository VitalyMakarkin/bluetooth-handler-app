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

class ScannedDevicesDataSource @Inject constructor() {

    private val _scannedDevicesFlow: MutableStateFlow<List<ScannedDevice>> = MutableStateFlow(emptyList())
    private val mutex = Mutex()

    fun observeAll(maxLastScannedAt: Instant, maxSignalLevel: Int): Flow<List<ScannedDevice>> {
        return _scannedDevicesFlow.map { devices ->
            devices.filter { it.scannedAt >= maxLastScannedAt && it.signalLevel <= maxSignalLevel }
        }
    }

    suspend fun add(device: ScannedDevice) {
        mutex.withLock {
            _scannedDevicesFlow.update { it + device }
        }
    }

    suspend fun clearAll() {
        mutex.withLock {
            _scannedDevicesFlow.update { emptyList() }
        }
    }
}