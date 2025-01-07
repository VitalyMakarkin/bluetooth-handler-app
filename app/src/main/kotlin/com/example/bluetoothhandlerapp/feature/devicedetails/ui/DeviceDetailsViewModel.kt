package com.example.bluetoothhandlerapp.feature.devicedetails.ui

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
import kotlinx.coroutines.flow.map
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
    val uiState: StateFlow<DeviceDetailsUiState> = interactor.observeByAddress(address = deviceDetails.address)
        .map { device ->
            DeviceDetailsUiState(
                device = device,
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