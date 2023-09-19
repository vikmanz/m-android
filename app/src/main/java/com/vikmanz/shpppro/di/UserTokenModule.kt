package com.vikmanz.shpppro.di

import com.vikmanz.shpppro.data.user_token.UserTokenHandler
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Suppress("unused")
@Module
@InstallIn(SingletonComponent::class)
object UserTokenModule {

    @Singleton
    @Provides
    fun providesUserTokenModule(): UserTokenHandler = UserTokenHandler

}