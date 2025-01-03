package com.example.bluetoothhandlerapp.app.bluetooth

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanResult
import android.content.Context
import android.os.Handler
import android.os.Looper
import com.example.bluetoothhandlerapp.core.data.repository.ScannedDevicesRepository
import com.example.bluetoothhandlerapp.core.model.ScannedDevice
import io.github.aakira.napier.Napier
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock

@SuppressLint("MissingPermission") // TODO: Move check permissions
class AndroidBluetoothLeHandler(
    private val context: Context,
    private val scannedDevicesRepository: ScannedDevicesRepository,
) : BluetoothLeHandler {

    private val bluetoothManager: BluetoothManager = context.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
    private val bluetoothAdapter: BluetoothAdapter = bluetoothManager.adapter
    private val bluetoothScanner = bluetoothAdapter.bluetoothLeScanner
    private var _isScanning = false

    private val handler = Handler(Looper.getMainLooper())
    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    private val scanCallback: ScanCallback = object : ScanCallback() {
        override fun onScanResult(callbackType: Int, result: ScanResult) {
            super.onScanResult(callbackType, result)
            with(result) {
                device.address?.let { address ->
                    val device = ScannedDevice(
                        address = address,
                        name = device.name ?: "Unknown",
                        scannedAt = Clock.System.now(),
                        rssi = rssi,
                    )
                    scope.launch { scannedDevicesRepository.addOrUpdate(device) }
                }
            }
        }
    }

    companion object {
        const val SCAN_PERIOD_IN_MILLIS: Long = 10_000L
    }

    override fun startScan() {
        if (_isScanning) throw IllegalStateException("BLE devices are already scanning!")
        handler.postDelayed(
            {
                _isScanning = false
                bluetoothScanner.stopScan(scanCallback)
                Napier.d { "Scanning stopped... [delayed, $SCAN_PERIOD_IN_MILLIS millis]" }
            },
            SCAN_PERIOD_IN_MILLIS,
        )
        _isScanning = true
        bluetoothScanner.startScan(scanCallback)
        Napier.d { "Scanning started..." }
    }

    override fun stopScan() {
        if (!_isScanning) throw IllegalStateException("BLE devices are not scanning now!")
        _isScanning = false
        bluetoothScanner.stopScan(scanCallback)
        Napier.d { "Scanning stopped..." }
    }

    override fun getIsScanned(): Boolean {
        return _isScanning
    }
}