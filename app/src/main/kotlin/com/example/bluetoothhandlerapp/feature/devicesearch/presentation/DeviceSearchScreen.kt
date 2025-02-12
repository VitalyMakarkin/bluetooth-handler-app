package com.example.bluetoothhandlerapp.feature.devicesearch.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.bluetoothhandlerapp.core.ui.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeviceSearchScreen(
    viewModel: DeviceSearchViewModel = hiltViewModel(),
    onDeviceClick: (address: String) -> Unit,
    onScanClick: () -> Unit, // TODO: Remove from parameter, change to composable Effect
    onOpenLogs: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsState()
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Devices",
                        modifier = Modifier.padding(all = 16.dp),
                    )
                },
                actions = {
                    Button(onClick = onOpenLogs) {
                        Text(text = "LOGS")
                    }
                },
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onScanClick) {
                Text("SCAN")
            }
        }
    ) { paddingValues ->
        LazyColumn(modifier = Modifier.padding(paddingValues)) {
            item { HorizontalDivider() }
            if (uiState.cachedDevices.isNotEmpty()) {
                item {
                    Column {
                        Text(
                            text = "Cached devices",
                            fontWeight = FontWeight.Normal,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 4.dp),
                        )
                        HorizontalDivider()
                    }
                }
                items(uiState.cachedDevices) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onDeviceClick(it.address) }
                            .padding(horizontal = 16.dp, vertical = 4.dp),
                    ) {
                        Text(text = "Name: ${it.name}")
                        Text(text = "Address: ${it.address}")
                    }
                    HorizontalDivider()
                }
            }
            if (uiState.scannedDevices.isNotEmpty()) {
                item {
                    Column {
                        Text(
                            text = "Scanned devices",
                            fontWeight = FontWeight.Normal,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 4.dp),
                        )
                        HorizontalDivider()
                    }
                }
                items(uiState.scannedDevices) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onDeviceClick(it.address) }
                            .padding(horizontal = 16.dp, vertical = 4.dp),
                    ) {
                        Text(text = "Name: ${it.name}")
                        Text(text = "Address: ${it.address}")
                        Text(text = "RSSI: ${it.rssi}")
                    }
                    HorizontalDivider()
                }
            }
        }
    }
}

@Preview
@Composable
fun DeviceSearchScreenPreview() {
    AppTheme {
        DeviceSearchScreen(
            onDeviceClick = {},
            onScanClick = {},
            onOpenLogs = {},
        )
    }
}