package com.example.bluetoothhandlerapp.feature.devicesearch.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.bluetoothhandlerapp.core.ui.theme.AppTheme

@Composable
fun DeviceSearchScreen(viewModel: DeviceSearchViewModel = viewModel()) {
    val uiState by viewModel.uiState.collectAsState()
    Scaffold(
        topBar = {
            Text("Device Search Screen")
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier.padding(paddingValues)
        ) {
            items(uiState.scannedDevices) {
                Text("Device: $it")
            }
        }
    }
}

@Preview
@Composable
fun DeviceSearchScreenPreview() {
    AppTheme {
        DeviceSearchScreen()
    }
}