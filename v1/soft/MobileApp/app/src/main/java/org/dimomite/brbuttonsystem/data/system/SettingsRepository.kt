package org.dimomite.brbuttonsystem.data.system

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import io.reactivex.rxjava3.core.BackpressureStrategy
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.subjects.BehaviorSubject
import org.dimomite.brbuttonsystem.domain.common.DataContainer
import org.dimomite.brbuttonsystem.domain.common.DataProvider
import org.dimomite.brbuttonsystem.domain.common.DataRepository
import org.dimomite.brbuttonsystem.domain.models.AppSettingsModel
import org.dimomite.brbuttonsystem.domain.models.ControlOrientation
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class SettingsRepository @Inject constructor(@ApplicationContext ctx: Context) : DataRepository<AppSettingsModel> {
    companion object {
        val initialSettings = AppSettingsModel(
            isNotificationControlEnabled = true,
            isFloatingControlEnabled = false,
            isWidgetControlEnabled = false,
            controlOrientation = ControlOrientation.RightHanded,
        )
    }

    private val settings = BehaviorSubject.createDefault<DataContainer<AppSettingsModel>>(DataContainer.Ok(initialSettings))
    private val settingsFlow = settings.toFlowable(BackpressureStrategy.LATEST).distinctUntilChanged().replay(1).refCount()
    private val settingsProvider = object : DataProvider<AppSettingsModel> {
        override fun outFlow(): Flowable<DataContainer<AppSettingsModel>> = settingsFlow
    }

    override fun provider(): DataProvider<AppSettingsModel> = settingsProvider

    private val job: ScheduledExecutorService = Executors.newScheduledThreadPool(1)

    init {
        job.scheduleAtFixedRate({
            val current = (settings.value as DataContainer.Ok).data
            val next = AppSettingsModel(
                isNotificationControlEnabled = current.isNotificationControlEnabled,
                isFloatingControlEnabled = current.isFloatingControlEnabled,
                isWidgetControlEnabled = !current.isWidgetControlEnabled,
            )
            settings.onNext(DataContainer.Ok(next))
        }, 1000L, 2000L, TimeUnit.MILLISECONDS)
    }

}
