package com.example.bluetoothhandlerapp.feature.devicelogs.presentation

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeviceLogsScreen(
    viewModel: DeviceLogsViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Logs",
                        modifier = Modifier.padding(all = 16.dp),
                    )
                },
                actions = {
                    Button(onClick = {}) {
                        Text(text = "SHARE")
                    }
                },
            )
        },
    ) { paddingValues ->
        LazyColumn(modifier = Modifier.padding(paddingValues)) {
            items(uiState.logs) {
                Text(
                    text = it,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp),
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Normal,
                )
            }
        }
    }
}