package com.example.bluetoothhandlerapp.feature.devicesearch.domain

import com.example.bluetoothhandlerapp.core.data.repository.ScannedDevicesRepository
import com.example.bluetoothhandlerapp.core.model.ScannedDevice
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.Instant
import javax.inject.Inject

class DeviceSearchInteractor @Inject constructor(
    private val scannedDevicesRepository: ScannedDevicesRepository,
) {
    fun observeAll(maxLastUpdatedAt: Instant): Flow<List<ScannedDevice>> {
        return scannedDevicesRepository.observeAll(maxLastUpdatedAt)
    }
}