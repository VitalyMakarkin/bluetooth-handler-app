package com.example.bluetoothhandlerapp.feature.devicedetails.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun DeviceDetailsScreen(
    viewModel: DeviceDetailsViewModel = viewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()
    Scaffold(
        topBar = {
            Text(
                text = "Device Details Screen",
                modifier = Modifier.padding(all = 16.dp),
            )
        }
    ) { paddingValues ->
        uiState.device?.let {
            Column(modifier = Modifier.padding(paddingValues)) {
                Text(text = "Name: ${it.name}")
                Text(text = "Address: ${it.address}")
                Text(text = "UpdatedAt: ${it.scannedAt}")
            }
        }
    }
}