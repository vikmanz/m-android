package com.vikmanz.shpppro.di

import com.vikmanz.shpppro.presentation.navigator.AuthNavGetter
import com.vikmanz.shpppro.presentation.navigator.MainNavGetter
import com.vikmanz.shpppro.presentation.navigator.interfaces.AuthNavControllerManager
import com.vikmanz.shpppro.presentation.navigator.interfaces.MainNavControllerManager
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class NavControllerMangersModule {
    @Binds
    @Singleton
    abstract fun bindAuthNav(
        authNavControllerManager: AuthNavGetter //realize
    ): AuthNavControllerManager

    @Binds
    @Singleton
    abstract fun bindMainNav(
        mainNavControllerManager: MainNavGetter //realize
    ): MainNavControllerManager

}