package com.example.bluetoothhandlerapp.core.data.repository

import com.example.bluetoothhandlerapp.core.datasource.ScannedDevicesDataSource
import com.example.bluetoothhandlerapp.core.model.ScannedDevice
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.Instant
import javax.inject.Inject

class DefaultScannedDevicesRepository @Inject constructor(
    private val scannedDevicesDataSource: ScannedDevicesDataSource,
): ScannedDevicesRepository {

    override fun observeAll(maxLastScannedAt: Instant, maxSignalLevel: Int): Flow<List<ScannedDevice>> {
        return scannedDevicesDataSource.observeAll(maxLastScannedAt, maxSignalLevel)
    }

    override suspend fun add(device: ScannedDevice) {
        scannedDevicesDataSource.add(device)
    }

    override suspend fun clearAll() {
        scannedDevicesDataSource.clearAll()
    }
}