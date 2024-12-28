package com.example.bluetoothhandlerapp.feature.observedevices.ui

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.bluetoothhandlerapp.core.ui.theme.BHTheme

@Composable
fun ObserveDevicesScreen() {
    Text("Observe Devices Screen")
}

@Preview
@Composable
fun ObserveDevicesScreenPreview() {
    BHTheme {
        ObserveDevicesScreen()
    }
}