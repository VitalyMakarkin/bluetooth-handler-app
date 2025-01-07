package com.example.bluetoothhandlerapp.core.data.repository

import com.example.bluetoothhandlerapp.core.data.mapper.mapToCachedDevice
import com.example.bluetoothhandlerapp.core.data.mapper.mapToCachedDeviceEntity
import com.example.bluetoothhandlerapp.core.database.dao.CachedDeviceDao
import com.example.bluetoothhandlerapp.core.model.CachedDevice
import io.github.aakira.napier.Napier
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DefaultCachedDevicesRepository @Inject constructor(
    private val cachedDeviceDao: CachedDeviceDao,
) : CachedDevicesRepository {

    override fun observeByAddress(address: String): Flow<CachedDevice?> {
        Napier.d { "observeByAddress... address = $address" }
        return cachedDeviceDao.observeByAddress(address)
            .map { device -> device?.mapToCachedDevice() }
    }

    override suspend fun upsert(device: CachedDevice) {
        Napier.d { "upsert... device = $device" }
        val deviceEntity = device.mapToCachedDeviceEntity()
        cachedDeviceDao.upsert(deviceEntity)
    }

    override suspend fun clearAll() {
        Napier.d { "clearAll..." }
        cachedDeviceDao.clearTable()
    }
}