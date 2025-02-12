package com.example.bluetoothhandlerapp.app.navigation

import kotlinx.serialization.Serializable

sealed interface Screen {

    @Serializable
    data object DeviceSearch : Screen

    @Serializable
    data class DeviceDetails(val address: String) : Screen

    @Serializable
    data class ServiceDetails(val address: String, val uuid: String) : Screen

    @Serializable
    data object DeviceLogs : Screen
}