package com.example.bluetoothhandlerapp.feature.servicedetails.presentation

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
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ServiceDetailsScreen(
    viewModel: ServiceDetailsViewModel = hiltViewModel(),
    onListenClick: () -> Unit,
    onOpenLogs: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsState()
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Service Details",
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
    ) { paddingValues ->
        uiState.serviceDetails?.let {
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
                    text = "Address: ${it.uuid}",
                    modifier = Modifier.padding(horizontal = 16.dp),
                )
                if (uiState.characteristics.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(16.dp))
                    HorizontalDivider()
                    uiState.characteristics.forEach { characteristic ->
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 4.dp),
                        ) {
                            Text(
                                text = characteristic.name,
                                fontSize = 17.sp,
                                fontWeight = FontWeight.Normal,
                                overflow = TextOverflow.Ellipsis,
                                maxLines = 1,
                            )
                            Text(
                                text = characteristic.uuid,
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

    // Show approve listen characteristic dialog
}