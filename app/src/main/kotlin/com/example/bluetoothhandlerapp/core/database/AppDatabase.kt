package com.example.bluetoothhandlerapp.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.bluetoothhandlerapp.core.database.dao.CachedDeviceDao
import com.example.bluetoothhandlerapp.core.database.model.CachedDeviceEntity

@Database(
    entities = [
        CachedDeviceEntity::class,
    ],
    version = 1,
    exportSchema = false,
)
internal abstract class AppDatabase : RoomDatabase() {
    abstract fun cachedDeviceDao(): CachedDeviceDao
}