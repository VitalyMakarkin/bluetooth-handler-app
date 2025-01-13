package com.example.bluetoothhandlerapp.core.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.bluetoothhandlerapp.core.database.model.CachedDeviceEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CachedDeviceDao {

    @Upsert
    suspend fun upsert(entity: CachedDeviceEntity)

    @Query("SELECT * FROM cached_devices ORDER BY updatedAt DESC")
    fun observeAll(): Flow<List<CachedDeviceEntity>>

    @Query("SELECT * FROM cached_devices WHERE address = :address")
    fun observeByAddress(address: String): Flow<CachedDeviceEntity?>

    @Query("DELETE FROM cached_devices")
    suspend fun clearTable()
}