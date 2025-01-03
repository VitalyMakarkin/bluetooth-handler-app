package com.example.bluetoothhandlerapp.feature.devicesearch.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.bluetoothhandlerapp.core.ui.theme.AppTheme

@Composable
fun DeviceSearchScreen(viewModel: DeviceSearchViewModel = viewModel(), onClick: () -> Unit) {
    val uiState by viewModel.uiState.collectAsState()
    Scaffold(
        topBar = {
            Text(
                text = "Device Search Screen",
                modifier = Modifier.padding(all = 16.dp),
            )
            HorizontalDivider()
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onClick) {
                Text("SCAN")
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier.padding(paddingValues)
        ) {
            items(uiState.scannedDevices) {
                Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)) {
                    Text(text = "Name: ${it.name}")
                    Text(text = "Address: ${it.address}")
                    Text(text = "RSSI: ${it.rssi}")
                }
                HorizontalDivider()
            }
        }
    }
}

@Preview
@Composable
fun DeviceSearchScreenPreview() {
    AppTheme {
        DeviceSearchScreen(onClick = {})
    }
}