package com.example.bluetoothhandlerapp.feature.devicedetails.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.bluetoothhandlerapp.app.navigation.DeviceDetails
import com.example.bluetoothhandlerapp.feature.devicedetails.domain.DeviceDetailsInteractor
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.aakira.napier.Napier
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DeviceDetailsViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val interactor: DeviceDetailsInteractor,
) : ViewModel() {
    private val deviceDetails = savedStateHandle.toRoute<DeviceDetails>()
    val uiState: StateFlow<DeviceDetailsUiState> = combine(
        interactor.observeByAddress(address = deviceDetails.address),
        interactor.observeScannedDeviceServices(address = deviceDetails.address)
    ) { device, services ->
            DeviceDetailsUiState(
                device = device,
                services = services,
                isLoading = false,
            )
        }
        .onStart { updateDevice() } // TODO: Move to Resume
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = DeviceDetailsUiState(isLoading = true)
        )

    fun updateDevice() {
        viewModelScope.launch {
            interactor.updateByAddress(address = deviceDetails.address)
                .onFailure { e -> Napier.e(e) { "Error then updating device is DeviceDetails" } }
        }
    }
}