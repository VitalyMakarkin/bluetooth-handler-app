package com.example.bluetoothhandlerapp.core.datasource

import com.example.bluetoothhandlerapp.core.datasource.model.ScannedService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import javax.inject.Inject

class InMemoryScannedServicesDataSource @Inject constructor() : ScannedServicesDataSource {

    private val _servicesFlow: MutableStateFlow<Map<String, Set<ScannedService>>> = MutableStateFlow(emptyMap())
    private val mutex = Mutex()

    override fun observeAllByDeviceAddress(deviceAddress: String): Flow<List<ScannedService>> {
        return _servicesFlow.map { chars ->
            chars[deviceAddress]?.toList() ?: emptyList()
        }
    }

    override suspend fun addOrIgnore(deviceAddress: String, service: ScannedService) {
        mutex.withLock {
            _servicesFlow.update { services ->
                val servicesByAddress = services[deviceAddress] ?: emptySet()
                val updatedServices = servicesByAddress + service
                services.plus(deviceAddress to updatedServices)
            }
        }
    }

    override suspend fun clearAllByDeviceAddress(deviceAddress: String) {
        mutex.withLock {
            _servicesFlow.update { services ->
                services.toMutableMap().apply {
                    this[deviceAddress] = emptySet()
                }
            }
        }
    }

    override suspend fun clearAll() {
        mutex.withLock { _servicesFlow.update { emptyMap() } }
    }
}