package com.example.bluetoothhandlerapp.app.di

import android.content.Context
import com.example.bluetoothhandlerapp.app.bluetooth.BluetoothLeHandler
import com.example.bluetoothhandlerapp.app.bluetooth.AndroidBluetoothLeHandler
import com.example.bluetoothhandlerapp.core.data.repository.ScannedDevicesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideBluetoothLeHandler(
        @ApplicationContext context: Context,
        scannedDevicesRepository: ScannedDevicesRepository,
    ): BluetoothLeHandler {
        return AndroidBluetoothLeHandler(
            context,
            scannedDevicesRepository,
        )
    }
}