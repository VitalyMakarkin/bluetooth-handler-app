package com.example.bluetoothhandlerapp.app.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.bluetoothhandlerapp.feature.devicedetails.presentation.DeviceDetailsScreen
import com.example.bluetoothhandlerapp.feature.devicelogs.presentation.DeviceLogsScreen
import com.example.bluetoothhandlerapp.feature.devicesearch.presentation.DeviceSearchScreen
import com.example.bluetoothhandlerapp.feature.servicedetails.presentation.ServiceDetailsScreen
import kotlinx.serialization.Serializable

@Serializable
object DeviceSearch

@Serializable
data class DeviceDetails(val address: String)

@Serializable
data class ServiceDetails(val address: String, val uuid: String)

@Serializable
object DeviceLogs

@Composable
fun AppNavHost(
    onScanClick: () -> Unit, // TODO: Remove
    onReadClick: (String) -> Unit, // TODO: Remove
    onListenClick: () -> Unit, // TODO: Remove
) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = DeviceSearch,
    ) {
        composable<DeviceSearch> {
            DeviceSearchScreen(
                onScanClick = onScanClick,
                onDeviceClick = { address -> navController.navigate(route = DeviceDetails(address)) },
                onOpenLogs = { navController.navigate(route = DeviceLogs) },
            )
        }

        composable<DeviceDetails> {
            DeviceDetailsScreen(
                onReadClick = onReadClick,
                onOpenLogs = { navController.navigate(route = DeviceLogs) },
            )
        }

        composable<ServiceDetails> {
            ServiceDetailsScreen(
                onListenClick = onListenClick,
                onOpenLogs = { navController.navigate(route = DeviceLogs) },
            )
        }

        composable<DeviceLogs> {
            DeviceLogsScreen()
        }
    }
}

