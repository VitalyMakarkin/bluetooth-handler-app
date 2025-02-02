package com.example.bluetoothhandlerapp.feature.servicedetails.domain

import com.example.bluetoothhandlerapp.core.data.repository.ScannedDevicesRepository
import com.example.bluetoothhandlerapp.core.model.DeviceCharacteristic
import com.example.bluetoothhandlerapp.core.model.DeviceService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ServiceDetailsInteractor @Inject constructor(
    private val scannedDevicesRepository: ScannedDevicesRepository,
) {

    fun observeService(address: String, uuid: String): Flow<DeviceService> {
        return scannedDevicesRepository.observeService(address, uuid)
    }

    fun observeCharacteristics(address: String, uuid: String): Flow<List<DeviceCharacteristic>> {
        return scannedDevicesRepository.observeCharacteristics(address, uuid)
    }
}