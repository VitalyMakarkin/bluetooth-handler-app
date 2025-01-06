package com.example.bluetoothhandlerapp.feature.devicedetails.domain

import com.example.bluetoothhandlerapp.core.data.repository.CachedDevicesRepository
import com.example.bluetoothhandlerapp.core.data.repository.ScannedDevicesRepository
import com.example.bluetoothhandlerapp.core.model.CachedDevice
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DeviceDetailsInteractor @Inject constructor(
    private val scannedDevicesRepository: ScannedDevicesRepository,
    private val cachedDevicesRepository: CachedDevicesRepository,
) {
    fun observeByAddress(address: String): Flow<CachedDevice> {
        return cachedDevicesRepository.observeByAddress(address)
    }

    suspend fun updateByAddress(address: String) = runCatching {
        val device = scannedDevicesRepository.getByAddressOrNull(address)
            ?: throw IllegalStateException("Not found scanned device by address = $address")
        val cachedDevice = device.let {
            CachedDevice(
                address = it.address,
                name = it.name,
                scannedAt = it.scannedAt,
            )
        }
        cachedDevicesRepository.upsert(cachedDevice)
    }
}