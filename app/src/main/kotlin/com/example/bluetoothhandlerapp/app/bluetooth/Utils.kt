package com.example.bluetoothhandlerapp.app.bluetooth

import java.nio.ByteOrder

@Deprecated("Don't use java api")
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