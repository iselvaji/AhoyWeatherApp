package com.ahoy.weatherapp.di

import android.content.Context
import com.ahoy.weatherapp.data.local.preference.DataStoreManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatastoreModule {

    @Provides
    @Singleton
    fun dataStorePreferences(@ApplicationContext context: Context): DataStoreManager =
        DataStoreManager(context = context)
}