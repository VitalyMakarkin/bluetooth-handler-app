package com.example.bluetoothhandlerapp.core.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.bluetoothhandlerapp.core.database.converter.InstantConverter
import kotlinx.datetime.Instant

@Entity(
    tableName = "cached_devices",
    indices = [
        Index(
            value = [CachedDeviceEntity.COLUMN_ADDRESS],
            unique = true,
        )
    ],
)
@TypeConverters(InstantConverter::class)
data class CachedDeviceEntity(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = COLUMN_LOCAL_ID)
    val localId: Int,

    @ColumnInfo(name = COLUMN_ADDRESS)
    val address: String,

    @ColumnInfo(name = COLUMN_NAME)
    val name: String,

    @ColumnInfo(name = COLUMN_SCANNED_AT)
    val scannedAt: Instant,
) {
    companion object {
        const val COLUMN_LOCAL_ID = "localId"
        const val COLUMN_ADDRESS = "address"
        const val COLUMN_NAME = "name"
        const val COLUMN_SCANNED_AT = "scannedAt"
    }
}