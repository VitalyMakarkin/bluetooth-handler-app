package com.example.bluetoothhandlerapp.app

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.example.bluetoothhandlerapp.core.ui.theme.AppTheme
import com.example.bluetoothhandlerapp.feature.devicesearch.ui.DeviceSearchScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val bluetoothRequestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            if (!permissions.all { it.value }) {
                // TODO: Notify user about not given all required permissions
            }
        }

    private fun getMissingPermissions(): Array<String> {
        val currentTargetSdk = applicationInfo.targetSdkVersion
        val requiredPermissions = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S && currentTargetSdk >= Build.VERSION_CODES.S) {
            arrayOf(Manifest.permission.BLUETOOTH_CONNECT, Manifest.permission.BLUETOOTH_SCAN)
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q && currentTargetSdk >= Build.VERSION_CODES.Q) {
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION)
        } else {
            arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION)
        }

        return requiredPermissions
            .filter { ContextCompat.checkSelfPermission(this, it) != PackageManager.PERMISSION_GRANTED }
            .toTypedArray()

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                DeviceSearchScreen()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        val missingPermissions = getMissingPermissions()
        if (missingPermissions.isNotEmpty()) {
            bluetoothRequestPermissionLauncher.launch(missingPermissions)
        }
    }
}

