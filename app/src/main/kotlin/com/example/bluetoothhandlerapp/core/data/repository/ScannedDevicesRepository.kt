package com.example.bluetoothhandlerapp.core.data.repository

import com.example.bluetoothhandlerapp.core.datasource.model.ScannedService
import com.example.bluetoothhandlerapp.core.model.DeviceCharacteristic
import com.example.bluetoothhandlerapp.core.model.DeviceService
import com.example.bluetoothhandlerapp.core.model.ScannedDevice
import kotlinx.coroutines.flow.Flow

interface ScannedDevicesRepository {
    fun observeAllDevices(): Flow<List<ScannedDevice>>
    suspend fun getDeviceByAddressOrNull(address: String): ScannedDevice?
    suspend fun addOrUpdateDevice(device: ScannedDevice)
    fun observeServices(address: String): Flow<List<DeviceService>>
    fun observeService(address: String, uuid: String): Flow<DeviceService>
    fun observeCharacteristics(address: String, uuid: String): Flow<List<DeviceCharacteristic>>
    suspend fun addOrIgnoreScannedService(deviceAddress: String, service: ScannedService)
    suspend fun clearServicesByDeviceAddress(address: String)
    suspend fun clearAllDevices()
}