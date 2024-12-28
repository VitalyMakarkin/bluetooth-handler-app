package com.example.bluetoothhandlerapp.feature.observedevices.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.bluetoothhandlerapp.core.ui.theme.BHTheme

@Composable
fun ObserveDevicesScreen(viewModel: ObserveDevicesViewModel = viewModel()) {
    val uiState by viewModel.uiState.collectAsState()
    Column {
        Text("Observe Devices Screen")
        Text("Devices: ${uiState.devices.joinToString(separator = ", ") { it.name }}")
    }
}

@Preview
@Composable
fun ObserveDevicesScreenPreview() {
    BHTheme {
        ObserveDevicesScreen()
    }
}