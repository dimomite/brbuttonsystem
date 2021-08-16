package org.dimomite.brbuttonsystem.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.dimomite.brbuttonsystem.data.devices.DevicesSettingsRepository
import org.dimomite.brbuttonsystem.data.system.SettingsRepository
import org.dimomite.brbuttonsystem.domain.common.DataRepository
import org.dimomite.brbuttonsystem.domain.models.AppSettingsModel
import org.dimomite.brbuttonsystem.domain.models.devices.DevicesSettings
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoriesModule {
    @Provides
    @Singleton
    fun bindSettingRepo(repo: SettingsRepository): DataRepository<AppSettingsModel> = repo

    @Provides
    @Singleton
    fun bindDevicesSettingsRepo(repo: DevicesSettingsRepository): DataRepository<DevicesSettings> = repo

}
