package com.example.bluetoothhandlerapp.app.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.bluetoothhandlerapp.feature.devicedetails.presentation.DeviceDetailsScreen
import com.example.bluetoothhandlerapp.feature.devicelogs.presentation.DeviceLogsScreen
import com.example.bluetoothhandlerapp.feature.devicesearch.presentation.DeviceSearchScreen
import com.example.bluetoothhandlerapp.feature.servicedetails.presentation.ServiceDetailsScreen

@Composable
fun AppNavHost(
    onScanClick: () -> Unit, // TODO: Remove
    onReadClick: (String) -> Unit, // TODO: Remove
    onListenClick: () -> Unit, // TODO: Remove
) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screen.DeviceSearch,
    ) {
        composable<Screen.DeviceSearch> {
            DeviceSearchScreen(
                onScanClick = onScanClick,
                onDeviceClick = { address -> navController.navigate(route = Screen.DeviceDetails(address)) },
                onOpenLogs = { navController.navigate(route = Screen.DeviceLogs) },
            )
        }

        composable<Screen.DeviceDetails> {
            DeviceDetailsScreen(
                onReadClick = onReadClick,
                onOpenService = { address, serviceUuid -> navController.navigate(route = Screen.ServiceDetails(address, serviceUuid)) },
                onOpenLogs = { navController.navigate(route = Screen.DeviceLogs) },
            )
        }

        composable<Screen.ServiceDetails> {
            ServiceDetailsScreen(
                onListenClick = onListenClick,
                onOpenLogs = { navController.navigate(route = Screen.DeviceLogs) },
            )
        }

        composable<Screen.DeviceLogs> {
            DeviceLogsScreen()
        }
    }
}

