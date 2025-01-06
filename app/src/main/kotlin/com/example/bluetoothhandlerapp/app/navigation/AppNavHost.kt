package com.example.bluetoothhandlerapp.app.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.bluetoothhandlerapp.feature.devicedetails.ui.DeviceDetailsScreen
import com.example.bluetoothhandlerapp.feature.devicesearch.ui.DeviceSearchScreen
import kotlinx.serialization.Serializable

@Serializable
object DeviceSearch

@Serializable
data class DeviceDetails(val address: String)

@Composable
fun AppNavHost(
    onScanClick: () -> Unit,
    onDeviceClick: (String) -> Unit,
) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = DeviceSearch,
    ) {
        composable<DeviceSearch> {
            DeviceSearchScreen(
                onScanClick = onScanClick,
                onDeviceClick = onDeviceClick,
            )
        }

        composable<DeviceDetails> {
            DeviceDetailsScreen()
        }
    }
}

