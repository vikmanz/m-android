package com.vikmanz.shpppro.data.di

import com.vikmanz.shpppro.data.source.local.DataStoreManager
import com.vikmanz.shpppro.data.source.local.interfaces.PreferencesDatastore
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Suppress("unused")
@Module
@InstallIn(SingletonComponent::class)
abstract class DataStoreModule {

    @Binds
    @Singleton
    abstract fun bindDataStore(
        navigator: DataStoreManager
    ): PreferencesDatastore

}