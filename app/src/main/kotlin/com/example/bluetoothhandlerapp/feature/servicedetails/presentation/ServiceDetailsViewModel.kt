package com.example.bluetoothhandlerapp.feature.servicedetails.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.bluetoothhandlerapp.app.navigation.Screen
import com.example.bluetoothhandlerapp.feature.servicedetails.domain.ServiceDetailsInteractor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class ServiceDetailsViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val interactor: ServiceDetailsInteractor,
) : ViewModel() {
    private val serviceDetails = savedStateHandle.toRoute<Screen.ServiceDetails>()
    val uiState: StateFlow<ServiceDetailsUiState> = combine(
        interactor.observeService(serviceDetails.address, serviceDetails.uuid),
        interactor.observeCharacteristics(serviceDetails.address, serviceDetails.uuid),
    ) { service, characteristics ->
        ServiceDetailsUiState(
            serviceDetails = service,
            characteristics = characteristics,
        )
    }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = ServiceDetailsUiState(isLoading = true)
        )
}