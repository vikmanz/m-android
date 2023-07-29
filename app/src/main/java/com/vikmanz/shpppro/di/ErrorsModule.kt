package com.vikmanz.shpppro.di

import com.vikmanz.shpppro.data.result.ApiSafeCaller
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Suppress("unused")
@Module
@InstallIn(SingletonComponent::class)
object ErrorsModule {

    @Singleton
    @Provides
    fun bindApiSafeCall(): ApiSafeCaller = ApiSafeCaller

}