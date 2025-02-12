package com.example.bluetoothhandlerapp.feature.devicedetails.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeviceDetailsScreen(
    viewModel: DeviceDetailsViewModel = hiltViewModel(),
    onReadClick: (address: String) -> Unit, // TODO: Remove from parameter, change to composable Effect
    onOpenService: (address: String, serviceUuid: String) -> Unit,
    onOpenLogs: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsState()
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Device Details",
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
            uiState.device?.let {
                FloatingActionButton(onClick = { onReadClick(it.address) }) {
                    Text("READ")
                }
            }
        }
    ) { paddingValues ->
        uiState.device?.let {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(paddingValues),
            ) {
                Text(
                    text = "Name: ${it.name}",
                    modifier = Modifier.padding(horizontal = 16.dp),
                )
                Text(
                    text = "Address: ${it.address}",
                    modifier = Modifier.padding(horizontal = 16.dp),
                )
                Text(
                    text = "UpdatedAt: ${it.updatedAt}",
                    modifier = Modifier.padding(horizontal = 16.dp),
                )
                if (uiState.services.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(16.dp))
                    HorizontalDivider()
                    uiState.services.forEach { service ->
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { onOpenService(it.address, service.uuid) }
                                .padding(horizontal = 16.dp, vertical = 4.dp),
                        ) {
                            Text(
                                text = service.name,
                                fontSize = 17.sp,
                                fontWeight = FontWeight.Normal,
                                overflow = TextOverflow.Ellipsis,
                                maxLines = 1,
                            )
                            Text(
                                text = service.uuid,
                                color = Color.Gray.copy(alpha = 0.5f),
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Medium,
                                overflow = TextOverflow.Ellipsis,
                                maxLines = 1,
                            )
                        }
                        HorizontalDivider()
                    }
                }
            }
        }
    }

    val lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                viewModel.updateDevice()
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }
}
