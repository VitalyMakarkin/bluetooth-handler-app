package com.example.bluetoothhandlerapp.app.bluetooth

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice.BOND_BONDED
import android.bluetooth.BluetoothDevice.BOND_BONDING
import android.bluetooth.BluetoothDevice.BOND_NONE
import android.bluetooth.BluetoothDevice.TRANSPORT_LE
import android.bluetooth.BluetoothGatt
import android.bluetooth.BluetoothGatt.GATT_SUCCESS
import android.bluetooth.BluetoothGattCallback
import android.bluetooth.BluetoothGattCharacteristic
import android.bluetooth.BluetoothGattDescriptor
import android.bluetooth.BluetoothManager
import android.bluetooth.BluetoothProfile
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanResult
import android.bluetooth.le.ScanSettings
import android.content.Context
import android.os.Build
import android.os.Handler
import android.os.Looper
import com.example.bluetoothhandlerapp.core.data.repository.ScannedDevicesRepository
import com.example.bluetoothhandlerapp.core.datasource.model.ScannedService
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

    companion object {
        const val SCAN_PERIOD_IN_MILLIS: Long = 10_000L
        const val GATT_ERROR = 0x0081
    }

    override fun startScan() {
        val settings = ScanSettings.Builder()
            .setScanMode(ScanSettings.SCAN_MODE_BALANCED)
            .build()

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
        bluetoothScanner.startScan(null, settings, scanCallback)
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

    override fun connect(address: String) {
        Napier.d { "Connecting... address = $address" }
        val device = bluetoothAdapter.getRemoteDevice(address)
        val gatt = device.connectGatt(context, true, gattCallback, TRANSPORT_LE)
        Napier.d { "Connected... gatt = $gatt" }
    }

    private val scanCallback: ScanCallback = object : ScanCallback() {
        override fun onScanResult(callbackType: Int, result: ScanResult) {
            super.onScanResult(callbackType, result)
            Napier.d { "Scanning... result = $result" }
            with(result) {
                device.address?.let { address ->
                    val device = ScannedDevice(
                        address = address,
                        name = device.name ?: "Unknown",
                        updatedAt = Clock.System.now(),
                        rssi = rssi,
                    )
                    scope.launch { scannedDevicesRepository.addOrUpdateDevice(device) }
                }
            }
        }
    }

    @OptIn(ExperimentalStdlibApi::class)
    private val gattCallback: BluetoothGattCallback = object : BluetoothGattCallback() {
        override fun onConnectionStateChange(gatt: BluetoothGatt?, status: Int, newState: Int) {
            Napier.d { "Connection state changed... gatt = $gatt, status = $status, newState = $newState" }
            val device = gatt?.device ?: return

            Napier.d { "Device: $device" }
            if (status == GATT_SUCCESS) {
                if (newState == BluetoothProfile.STATE_CONNECTED) {
                    val bondState = device.bondState
                    if (bondState == BOND_NONE || bondState == BOND_BONDED) {
                        val delay = when {
                            bondState == BOND_BONDED && Build.VERSION.SDK_INT <= Build.VERSION_CODES.N -> 1000L
                            else -> 0L
                        }
                        handler.postDelayed(
                            {
                                Napier.d { "Try discovering services... [delay = $delay]" }
                                val isStarted = gatt.discoverServices()
                                if (!isStarted) {
                                    Napier.d { "Start discovering services is failed..." }
                                }
                            },
                            delay,
                        )
                    } else if (bondState == BOND_BONDING) {
                        Napier.d { "Bonding in process..." }
                    }
                } else if (newState == BluetoothProfile.STATE_DISCONNECTED) {
                    Napier.d { "Disconnecting device..." }
                    gatt.close()
                } else {
                    Napier.d { "Gatt callback with another state... state = $newState" }
                }
            } else {
                Napier.d { "Gatt callback error... status = $status" }
                gatt.close()
            }
        }

        override fun onServicesDiscovered(gatt: BluetoothGatt?, status: Int) {
            super.onServicesDiscovered(gatt, status)
            Napier.d { "Services discovered on device... status = $status" }
            if (status == GATT_ERROR) {
                Napier.d { "Discovering service failed" }
                gatt?.disconnect()
            } else {
                val services = gatt?.services ?: emptyList()
                Napier.d { "Found ${services.size} services" }
                gatt?.device?.address?.let { deviceAddress ->
                    services.forEach { s ->
                        Napier.w { "Service: ${GattServices.getNameByUuid(s.uuid.toString())} [${s.uuid.toString()}]" }
                        s.characteristics.forEach { c ->
                            Napier.w { "Characteristic: ${GattCharacteristics.getNameByUuid(c.uuid.toString())} [${c.uuid.toString()}]" }

                        }
                    }
                    services.forEach { service ->
                        scope.launch {
                            val characteristicUuids = service.characteristics.map { it.uuid.toString() }.toSet()
                            val scannedService = ScannedService(uuid = service.uuid.toString(), characteristicUuids = characteristicUuids)
                            scannedDevicesRepository.addOrIgnoreScannedService(deviceAddress = deviceAddress, service = scannedService)
                        }
                    }
                }
//                deviceAddress?.let {
//                    service.characteristics.map { characteristic ->
//                        if (service.uuid.toString() == "0000180d-0000-1000-8000-00805f9b34fb" && characteristic.uuid.toString() == "00002a37-0000-1000-8000-00805f9b34fb") {
//                            Napier.i { "serviceUuid = ${service.uuid} characteristic = ${characteristic.uuid} props = ${characteristic.properties}" }
//                            Napier.w { "Heart Rate found" }
//                            val isRead = gatt?.setCharacteristicNotification(characteristic, true)
//                            Napier.w { "Heart Rate try read: $isRead" }
//                            val descriptor = characteristic.getDescriptor(UUID.fromString("00002902-0000-1000-8000-00805f9b34fb"))
//                            descriptor.value = BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE
//                            gatt?.writeDescriptor(descriptor)
//                            Napier.w { "Heart Rate descriptor updated" }
//                        }
//                    }
//                }
            }
        }

        override fun onCharacteristicRead(gatt: BluetoothGatt, characteristic: BluetoothGattCharacteristic, value: ByteArray, status: Int) {
            super.onCharacteristicRead(gatt, characteristic, value, status)
            Napier.i { "onCharacteristicRead: characteristic = ${characteristic.uuid} & value = $value " }
        }

        override fun onCharacteristicChanged(gatt: BluetoothGatt?, characteristic: BluetoothGattCharacteristic?) {
            super.onCharacteristicChanged(gatt, characteristic)
            val value = characteristic?.value?.let { byteArrayToInt(it) }
            Napier.i { "onCharacteristicChanged [deprecated]: characteristic = ${characteristic?.uuid},  value = $value" }
        }

        override fun onCharacteristicChanged(gatt: BluetoothGatt, characteristic: BluetoothGattCharacteristic, value: ByteArray) {
            super.onCharacteristicChanged(gatt, characteristic, value)
            Napier.i { "onCharacteristicChanged: characteristic = ${characteristic.uuid} & value = $value" }
        }

        override fun onDescriptorWrite(gatt: BluetoothGatt?, descriptor: BluetoothGattDescriptor?, status: Int) {
            super.onDescriptorWrite(gatt, descriptor, status)
            Napier.i { "onDescriptorWrite: descriptor = ${descriptor?.uuid}, value = ${descriptor?.value?.toHexString()} & status = $status" }
        }
    }
}