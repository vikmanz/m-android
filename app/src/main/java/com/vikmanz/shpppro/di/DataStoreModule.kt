package com.vikmanz.shpppro.di

import com.vikmanz.shpppro.data.datastore.DatastoreImpl
import com.vikmanz.shpppro.data.datastore.Datastore
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
        datastore: DatastoreImpl
    ): Datastore

}