package com.example.bluetoothhandlerapp.feature.devicelogs.domain

import com.example.bluetoothhandlerapp.core.data.repository.DeviceLogsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DeviceLogsInteractor @Inject constructor(
    private val deviceLogsRepository: DeviceLogsRepository,
) {

    fun observeLogs(): Flow<List<String>> {
        return deviceLogsRepository.observeAll()
    }
}