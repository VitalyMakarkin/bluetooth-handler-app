package com.example.bluetoothhandlerapp.core.data.di

import com.example.bluetoothhandlerapp.core.data.repository.CachedDevicesRepository
import com.example.bluetoothhandlerapp.core.data.repository.DefaultCachedDevicesRepository
import com.example.bluetoothhandlerapp.core.data.repository.DefaultScannedDevicesRepository
import com.example.bluetoothhandlerapp.core.data.repository.ScannedDevicesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    internal abstract fun bindScannedDevicesRepository(
        repository: DefaultScannedDevicesRepository,
    ): ScannedDevicesRepository

    @Binds
    abstract fun bindCachedDevicesRepository(
        repository: DefaultCachedDevicesRepository,
    ): CachedDevicesRepository
}