package org.dimomite.brbuttonsystem.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.dimomite.brbuttonsystem.GlobalConfigs

private val configs = GlobalConfigs()

@Module
@InstallIn(SingletonComponent::class)
object GlobalModule {

    @Provides
    fun bindGlobalConfigs(): GlobalConfigs = configs

}