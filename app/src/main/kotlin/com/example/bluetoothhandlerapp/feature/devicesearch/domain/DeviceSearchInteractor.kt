package com.example.bluetoothhandlerapp.feature.devicesearch.domain

import com.example.bluetoothhandlerapp.core.data.repository.CachedDevicesRepository
import com.example.bluetoothhandlerapp.core.data.repository.ScannedDevicesRepository
import com.example.bluetoothhandlerapp.core.model.CachedDevice
import com.example.bluetoothhandlerapp.core.model.ScannedDevice
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.Instant
import javax.inject.Inject

class DeviceSearchInteractor @Inject constructor(
    private val cachedDevicesRepository: CachedDevicesRepository,
    private val scannedDevicesRepository: ScannedDevicesRepository,
) {
    fun observeCachedDevices(): Flow<List<CachedDevice>> {
        return cachedDevicesRepository.observeAll()
    }

    fun observeScannedDevices(maxLastUpdatedAt: Instant): Flow<List<ScannedDevice>> {
        return scannedDevicesRepository.observeAll(maxLastUpdatedAt)
    }
}