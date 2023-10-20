package com.vikmanz.shpppro.di

import com.vikmanz.shpppro.data.api.ShPPApi
import com.vikmanz.shpppro.data.api.api_result.ApiSafeCaller
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Suppress("unused")
@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Singleton
    @Provides
    fun provideShPPServerApi (
        retrofitClient: Retrofit
    ): ShPPApi {
        return retrofitClient
            .create(ShPPApi::class.java)
    }

    @Singleton
    @Provides
    fun bindApiSafeCall(): ApiSafeCaller = ApiSafeCaller

}