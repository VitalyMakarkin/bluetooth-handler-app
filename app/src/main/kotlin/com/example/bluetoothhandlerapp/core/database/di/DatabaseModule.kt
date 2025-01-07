package com.example.bluetoothhandlerapp.core.database.di

import android.content.Context
import androidx.room.Room
import com.example.bluetoothhandlerapp.core.database.AppDatabase
import com.example.bluetoothhandlerapp.core.database.dao.CachedDeviceDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext context: Context,
    ): AppDatabase = Room.databaseBuilder(
        context,
        AppDatabase::class.java,
        "database",
    ).build()

    @Provides
    @Singleton
    fun provideCachedDeviceDao(database: AppDatabase): CachedDeviceDao {
        return database.cachedDeviceDao()
    }
}