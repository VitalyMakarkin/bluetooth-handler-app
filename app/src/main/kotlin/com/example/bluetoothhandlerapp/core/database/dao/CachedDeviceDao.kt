package com.example.bluetoothhandlerapp.core.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.bluetoothhandlerapp.core.database.model.CachedDeviceEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CachedDeviceDao {

    @Upsert
    fun upsert(entity: CachedDeviceEntity)

    @Query("SELECT * FROM cached_devices WHERE localId = :localId")
    fun observeByLocalId(localId: Int): Flow<CachedDeviceEntity>

    @Query("DELETE FROM cached_devices")
    fun clearTable()
}