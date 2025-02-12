package com.example.bluetoothhandlerapp.app.bluetooth

interface BluetoothLeHandler {
    fun startScan()
    fun stopScan()
    fun getIsScanned(): Boolean // TODO: remove
    fun connect(address: String)
    fun listen(address: String, serviceUuid: String, characteristicUuid: String)
}