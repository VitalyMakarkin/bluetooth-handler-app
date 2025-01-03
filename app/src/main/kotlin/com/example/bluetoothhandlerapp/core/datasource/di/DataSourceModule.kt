package com.example.bluetoothhandlerapp.core.datasource.di

import com.example.bluetoothhandlerapp.core.datasource.InMemoryScannedDevicesDataSource
import com.example.bluetoothhandlerapp.core.datasource.ScannedDevicesDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Binds
    @Singleton
    abstract fun bindScannedDevicesDataSource(
        dataSource: InMemoryScannedDevicesDataSource,
    ): ScannedDevicesDataSource
}