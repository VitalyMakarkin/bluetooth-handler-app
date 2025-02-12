package com.example.bluetoothhandlerapp.core.data.repository

import com.example.bluetoothhandlerapp.app.bluetooth.GattCharacteristics
import com.example.bluetoothhandlerapp.app.bluetooth.GattServices
import com.example.bluetoothhandlerapp.core.datasource.ScannedServicesDataSource
import com.example.bluetoothhandlerapp.core.datasource.ScannedDevicesDataSource
import com.example.bluetoothhandlerapp.core.datasource.model.ScannedService
import com.example.bluetoothhandlerapp.core.model.DeviceCharacteristic
import com.example.bluetoothhandlerapp.core.model.DeviceService
import com.example.bluetoothhandlerapp.core.model.ScannedDevice
import io.github.aakira.napier.Napier
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DefaultScannedDevicesRepository @Inject constructor(
    private val scannedDevicesDataSource: ScannedDevicesDataSource,
    private val scannedServicesDataSource: ScannedServicesDataSource,
) : ScannedDevicesRepository {

    override fun observeAllDevices(): Flow<List<ScannedDevice>> {
        Napier.d { "observeAllDevices..." }
        return scannedDevicesDataSource.observeAll()
    }

    override suspend fun getDeviceByAddressOrNull(address: String): ScannedDevice? {
        Napier.d { "getDeviceByAddressOrNull... address = $address" }
        return scannedDevicesDataSource.getByAddressOrNull(address)
    }

    override suspend fun addOrUpdateDevice(device: ScannedDevice) {
        Napier.d { "addOrUpdateDevice... device = $device" }
        scannedDevicesDataSource.addOrUpdate(device)
    }

    override fun observeServices(address: String): Flow<List<DeviceService>> {
        Napier.d { "observeServices... address = $address" }
        return scannedServicesDataSource.observeAllByDeviceAddress(address).map { services ->
            services.map { service ->
                DeviceService(
                    uuid = service.uuid,
                    name = GattServices.getNameByUuid(service.uuid),
                )
            }
        }
    }

    override fun observeService(address: String, uuid: String): Flow<DeviceService> {
        Napier.d { "observeService... address = $address, uuid = $uuid" }
        return scannedServicesDataSource.observeAllByDeviceAddress(address)
            .map { list -> list.first { it.uuid == uuid } }
            .map {
                DeviceService(
                    uuid = it.uuid,
                    name = GattServices.getNameByUuid(it.uuid),
                )
            }
    }

    override fun observeCharacteristics(address: String, uuid: String): Flow<List<DeviceCharacteristic>> {
        Napier.d { "observeCharacteristics... address = $address, uuid = $uuid" }
        return scannedServicesDataSource.observeAllByDeviceAddress(address).map { services ->
            services.first { it.uuid == uuid }
                .characteristicUuids
                .map { DeviceCharacteristic(uuid = it, name = GattCharacteristics.getNameByUuid(it)) }
        }
    }

    override suspend fun addOrIgnoreScannedService(deviceAddress: String, service: ScannedService) {
        Napier.d { "addOrIgnoreScannedService... deviceAddress = $deviceAddress, service = $service" }
        scannedServicesDataSource.addOrIgnore(deviceAddress, service)
    }

    override suspend fun clearServicesByDeviceAddress(address: String) {
        Napier.d { "clearServicesByDeviceAddress... address = $address" }
        scannedServicesDataSource.clearAllByDeviceAddress(address)
    }

    override suspend fun clearAllDevices() {
        Napier.d { "clearAllDevices..." }
        scannedDevicesDataSource.clearAll()
        scannedServicesDataSource.clearAll()
    }
}