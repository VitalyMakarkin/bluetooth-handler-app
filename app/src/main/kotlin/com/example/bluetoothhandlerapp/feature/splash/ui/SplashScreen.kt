package com.example.bluetoothhandlerapp.feature.splash.ui

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.bluetoothhandlerapp.core.ui.theme.BHTheme

@Composable
fun SplashScreen() {
    Text("Splash Screen")
}

@Preview
@Composable
fun SplashScreenPreview() {
    BHTheme {
        SplashScreen()
    }
}