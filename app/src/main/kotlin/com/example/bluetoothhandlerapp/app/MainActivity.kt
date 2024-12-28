package com.example.bluetoothhandlerapp.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.bluetoothhandlerapp.core.ui.theme.BHTheme
import com.example.bluetoothhandlerapp.feature.observedevices.ui.ObserveDevicesScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BHTheme {
                ObserveDevicesScreen()
            }
        }
    }
}

