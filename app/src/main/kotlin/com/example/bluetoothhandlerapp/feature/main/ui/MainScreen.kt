package com.example.bluetoothhandlerapp.feature.main.ui

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.bluetoothhandlerapp.core.ui.theme.BHTheme

@Composable
fun MainScreen() {
    Text("Main Screen")
}

@Preview
@Composable
fun MainScreenPreview() {
    BHTheme {
        MainScreen()
    }
}