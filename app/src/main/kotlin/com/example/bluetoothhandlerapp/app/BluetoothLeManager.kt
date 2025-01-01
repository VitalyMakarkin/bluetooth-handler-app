package com.example.bluetoothhandlerapp.app

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.bluetooth.le.BluetoothLeScanner
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanResult
import android.content.Context
import android.os.Handler
import android.util.Log
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@SuppressLint("MissingPermission")
class BluetoothLeManager @Inject constructor(
    @ApplicationContext private val context: Context,
) {

    private val bluetoothAdapter: BluetoothAdapter
    private val bluetoothScanner: BluetoothLeScanner
    private var scanning = false
    private val handler = Handler()

    // Stops scanning after 10 seconds.
    private val scanPeriod: Long = 10000

    // Device scan callback.
    private val leScanCallback: ScanCallback = object : ScanCallback() {
        override fun onScanResult(callbackType: Int, result: ScanResult) {
            super.onScanResult(callbackType, result)
            Log.d("BluetoothAppManager", result.toString())
        }
    }

    fun scanDevice() {
        if (!scanning) { // Stops scanning after a pre-defined scan period.
            handler.postDelayed({
                scanning = false
                bluetoothScanner.stopScan(leScanCallback)
            }, scanPeriod)
            scanning = true
            bluetoothScanner.startScan(leScanCallback)
        } else {
            scanning = false
            bluetoothScanner.stopScan(leScanCallback)
        }
    }

    init {
        val bluetoothManager = requireNotNull(context.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager)
        bluetoothAdapter = requireNotNull(bluetoothManager.adapter)
        bluetoothScanner = bluetoothAdapter.bluetoothLeScanner
    }
}