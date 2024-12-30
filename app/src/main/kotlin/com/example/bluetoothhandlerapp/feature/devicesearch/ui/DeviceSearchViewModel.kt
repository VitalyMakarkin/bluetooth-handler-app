package com.example.bluetoothhandlerapp.feature.devicesearch.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bluetoothhandlerapp.core.data.repository.ScannedDevicesRepository
import com.example.bluetoothhandlerapp.core.model.ScannedDevice
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.minus
import javax.inject.Inject

@HiltViewModel
class DeviceSearchViewModel @Inject constructor(
    private val repository: ScannedDevicesRepository,
) : ViewModel() {

    val uiState: StateFlow<DeviceSearchUiState> = repository.observeAll(
        maxLastScannedAt = Clock.System.now().minus(4, DateTimeUnit.SECOND),
        maxSignalLevel = 6,
    )
        .map { devices ->
            DeviceSearchUiState(
                scannedDevices = devices,
                isLoading = false,
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = DeviceSearchUiState(isLoading = true)
        )

    init {
        viewModelScope.launch {
            listOf(
                ScannedDevice(
                    id = 1,
                    macAddress = "1",
                    name = "Device 1",
                    scannedAt = Clock.System.now().minus(5, DateTimeUnit.SECOND),
                    signalLevel = 4
                ),
                ScannedDevice(
                    id = 2,
                    macAddress = "2",
                    name = "Device 2",
                    scannedAt = Clock.System.now().minus(3, DateTimeUnit.SECOND),
                    signalLevel = 3
                ),
                ScannedDevice(
                    id = 3,
                    macAddress = "3",
                    name = "Device 3",
                    scannedAt = Clock.System.now().minus(4, DateTimeUnit.SECOND),
                    signalLevel = 6
                ),
                ScannedDevice(
                    id = 4,
                    macAddress = "4",
                    name = "Device 4",
                    scannedAt = Clock.System.now().minus(1, DateTimeUnit.SECOND),
                    signalLevel = 1
                ),
            ).forEach { repository.add(it) }
        }
    }
}