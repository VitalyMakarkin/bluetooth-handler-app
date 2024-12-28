package com.example.bluetoothhandlerapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.bluetoothhandlerapp.ui.theme.BluetoothHandlerAppTheme

class MainActivity : ComponentActivity() {

//    private val requestPermissionLauncher =
//        registerForActivityResult(
//            ActivityResultContracts.RequestMultiplePermissions()
//        ) { permission ->
//            permission.map { (permission, isGranted) ->
//                if (!isGranted) {
//                    ActivityCompat.shouldShowRequestPermissionRationale(this, permission)
//                }
//            }
//        }
//
//    private val adapter = BluetoothAdapter.getDefaultAdapter()
//    private val scanCallback = object : ScanCallback() {
//        override fun onScanResult(callbackType: Int, result: ScanResult?) {
//            super.onScanResult(callbackType, result)
//            Log.d(TAG, "scan result: ${result.toString()}")
//        }
//
//        override fun onBatchScanResults(results: MutableList<ScanResult>?) {
//            super.onBatchScanResults(results)
//            Log.d(TAG, "batch scan result: ${results.toString()}")
//        }
//
//        override fun onScanFailed(errorCode: Int) {
//            super.onScanFailed(errorCode)
//            Log.d(TAG, "scan errorCode: $errorCode")
//        }
//    }
//    private val scanner = adapter.bluetoothLeScanner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        if (scanner != null) {
//            val filter = ScanFilter.Builder()
//                // .setDeviceName("---") // Prefix device name
//                // .setDeviceAddress("---") // MAC address
//                .build()
//            val filters = arrayListOf(filter)
//
//            val scanSettings = ScanSettings.Builder()
//                .setScanMode(ScanSettings.SCAN_MODE_BALANCED)
//                .setCallbackType(ScanSettings.CALLBACK_TYPE_ALL_MATCHES)
//                .setMatchMode(ScanSettings.MATCH_MODE_AGGRESSIVE)
//                .setNumOfMatches(ScanSettings.MATCH_NUM_ONE_ADVERTISEMENT)
//                .setReportDelay(0L)
//                .build()
//
//            val notCheckedSelfPermissions = getNotCheckedPermissions(
//                this,
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) Manifest.permission.BLUETOOTH_SCAN else null,
//                Manifest.permission.ACCESS_FINE_LOCATION,
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) Manifest.permission.ACCESS_BACKGROUND_LOCATION else null,
//                Manifest.permission.ACCESS_COARSE_LOCATION,
//            )
//
//            if (notCheckedSelfPermissions.isNotEmpty()) {
//                requestPermissionLauncher.launch(notCheckedSelfPermissions)
//                return
//            }
//
//            scanner.startScan(filters, scanSettings, scanCallback)
//            Log.d(TAG, "scan started")
//        } else {
//            Log.e(TAG, "could not get scanner object")
//        }

        enableEdgeToEdge()
        setContent {
            BluetoothHandlerAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxSize(),
                    ) {
                        Text(
                            text = "Test",
                            modifier = Modifier.padding(innerPadding),
                        )
                    }
                }
            }
        }
    }

//    private fun getNotCheckedPermissions(context: Context, vararg permissions: String?): Array<String> {
//        return permissions
//            .filterNotNull()
//            .mapNotNull { if (ActivityCompat.checkSelfPermission(context, it) != PackageManager.PERMISSION_GRANTED) it else null }
//            .toTypedArray()
//    }
}

