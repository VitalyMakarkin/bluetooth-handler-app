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
import com.example.bluetoothhandlerapp.core.model.ScannedDevice
import io.github.aakira.napier.Napier
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import java.nio.ByteOrder
import java.nio.charset.Charset
import java.util.UUID

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
                    scope.launch { scannedDevicesRepository.addOrUpdate(device) }
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
            if (status == 0x0081) { // TODO: Check code
                Napier.d { "Discovering service failed" }
                gatt?.disconnect()
            } else {
                val services = gatt?.services ?: emptyList()
                Napier.d { "Found ${services.size} services" }
                services.forEach { service ->
                    service.characteristics.map { characteristic ->
                        if (service.uuid.toString() == "0000180d-0000-1000-8000-00805f9b34fb" && characteristic.uuid.toString() == "00002a37-0000-1000-8000-00805f9b34fb") {
                            Napier.i { "serviceUuid = ${service.uuid} characteristic = ${characteristic.uuid} props = ${characteristic.properties}" }
                            Napier.w { "Heart Rate found" }
                            val isRead = gatt?.setCharacteristicNotification(characteristic, true)
                            Napier.w { "Heart Rate try read: $isRead" }
                            val descriptor = characteristic.getDescriptor(UUID.fromString("00002902-0000-1000-8000-00805f9b34fb"))
                            descriptor.value = BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE
                            gatt?.writeDescriptor(descriptor)
                            Napier.w { "Heart Rate descriptor updated" }
                        }
                    }
                }
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

    fun byteArrayToInt(bytes: ByteArray, byteOrder: ByteOrder = ByteOrder.BIG_ENDIAN): Int {
        if (bytes.size > 4) {
            throw IllegalArgumentException("Byte array size cannot be greater than 4 for Int")
        }

        var result = 0
        val actualBytes = if (bytes.size < 4) {
            val padded = ByteArray(4)
            System.arraycopy(bytes, 0, padded, 4 - bytes.size, bytes.size)
            padded
        } else {
            bytes
        }


        if (byteOrder == ByteOrder.BIG_ENDIAN) {
            for (i in 0 until 4) {
                result = (result shl 8) or (actualBytes[i].toInt() and 0xFF)
            }
        } else {
            for (i in 3 downTo 0) {
                result = (result shl 8) or (actualBytes[i].toInt() and 0xFF)
            }
        }
        return result
    }

}