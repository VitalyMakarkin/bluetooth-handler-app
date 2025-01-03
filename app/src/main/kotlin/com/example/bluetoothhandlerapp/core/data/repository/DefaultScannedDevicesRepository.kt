package com.example.bluetoothhandlerapp.core.data.repository

import com.example.bluetoothhandlerapp.core.datasource.ScannedDevicesDataSource
import com.example.bluetoothhandlerapp.core.model.ScannedDevice
import io.github.aakira.napier.Napier
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.Instant
import javax.inject.Inject

class DefaultScannedDevicesRepository @Inject constructor(
    private val scannedDevicesDataSource: ScannedDevicesDataSource,
) : ScannedDevicesRepository {

    override fun observeAll(maxLastScannedAt: Instant): Flow<List<ScannedDevice>> {
        Napier.d { "observeAll... maxLastScannedAt = $maxLastScannedAt" }
        return scannedDevicesDataSource.observeAll(maxLastScannedAt)
    }

    override suspend fun addOrUpdate(device: ScannedDevice) {
        Napier.d { "addOrUpdate... device = $device" }
        scannedDevicesDataSource.addOrUpdate(device)
    }

    override suspend fun clearAll() {
        Napier.d { "clearAll..." }
        scannedDevicesDataSource.clearAll()
    }
}