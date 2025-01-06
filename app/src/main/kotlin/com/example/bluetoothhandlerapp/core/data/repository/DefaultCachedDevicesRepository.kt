package com.example.bluetoothhandlerapp.core.data.repository

import com.example.bluetoothhandlerapp.core.data.mapper.mapToCachedDevice
import com.example.bluetoothhandlerapp.core.data.mapper.mapToCachedDeviceEntity
import com.example.bluetoothhandlerapp.core.database.dao.CachedDeviceDao
import com.example.bluetoothhandlerapp.core.database.model.CachedDeviceEntity
import com.example.bluetoothhandlerapp.core.model.CachedDevice
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DefaultCachedDevicesRepository @Inject constructor(
    private val cachedDeviceDao: CachedDeviceDao,
) : CachedDevicesRepository {

    override fun observeByAddress(address: String): Flow<CachedDevice> {
        return cachedDeviceDao.observeByAddress(address).map(CachedDeviceEntity::mapToCachedDevice)
    }

    override suspend fun upsert(device: CachedDevice) {
        val deviceEntity = device.mapToCachedDeviceEntity()
        cachedDeviceDao.upsert(deviceEntity)
    }

    override suspend fun clearAll() {
        cachedDeviceDao.clearTable()
    }
}