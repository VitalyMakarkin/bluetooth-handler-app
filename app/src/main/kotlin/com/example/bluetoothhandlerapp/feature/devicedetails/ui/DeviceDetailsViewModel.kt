package com.example.bluetoothhandlerapp.feature.devicedetails.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bluetoothhandlerapp.feature.devicedetails.domain.DeviceDetailsInteractor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DeviceDetailsViewModel @Inject constructor(
    private val interactor: DeviceDetailsInteractor,
) : ViewModel() {

    val uiState: StateFlow<DeviceDetailsUiState> = interactor.observeByAddress(address = "")
        .map { device ->
            DeviceDetailsUiState(
                device = device,
                isLoading = false,
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = DeviceDetailsUiState(isLoading = true)
        )

    fun updateDevice() {
        viewModelScope.launch {
            interactor.updateByAddress(address = "")
                .onFailure {
                    // TODO: Notify user
                }
        }
    }
}