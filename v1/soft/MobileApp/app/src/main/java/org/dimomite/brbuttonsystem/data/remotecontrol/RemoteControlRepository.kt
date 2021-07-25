package org.dimomite.brbuttonsystem.data.remotecontrol

import android.content.Context
import android.content.Intent
import android.os.Build
import dagger.hilt.android.qualifiers.ApplicationContext
import io.reactivex.rxjava3.core.BackpressureStrategy
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.subjects.PublishSubject
import org.dimomite.brbuttonsystem.BuildConfig
import org.dimomite.brbuttonsystem.domain.common.DataContainer
import org.dimomite.brbuttonsystem.domain.common.DataProvider
import org.dimomite.brbuttonsystem.domain.common.DataRepository
import org.dimomite.brbuttonsystem.domain.common.convertDataContainer
import org.dimomite.brbuttonsystem.domain.models.AppSettingsModel
import org.dimomite.brbuttonsystem.domain.models.RemoteControlModel
import org.dimomite.brbuttonsystem.remotecontrol.RemoteControlService
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteControlRepository @Inject constructor(
    @ApplicationContext private val ctx: Context,
    private val settingsRepo: DataRepository<AppSettingsModel>
) {
    fun remoteControlEvents(): DataProvider<RemoteControlModel> = remoteEventsProvider

    private val remoteEventsProvider: DataProvider<RemoteControlModel> = object : DataProvider<RemoteControlModel> {
        override fun outFlow(): Flowable<DataContainer<RemoteControlModel>> = eventsFlow
    }

    private val events = PublishSubject.create<RemoteControlModel>()
    private val eventsFlow: Flowable<DataContainer<RemoteControlModel>>

    private val settingsSubs = CompositeDisposable()

    init {
        val f: Observable<DataContainer<RemoteControlModel>> = events.map { DataContainer.Ok(it) } // need temp to set type to DataContainer and not to .Ok
        eventsFlow = f.toFlowable(BackpressureStrategy.LATEST).replay(1).refCount()
    }

    fun start() {
        if (settingsSubs.size() > 0) {
            Timber.w("RemoteControlRepository: start(): already started")
            return
        }

        // TODO later also check device connection status
        // TODO need to listen whether channel is enabled in Android app settings and restart when reenabled
        // probably this broadcast https://developer.android.com/reference/android/app/NotificationManager.html#ACTION_NOTIFICATION_CHANNEL_BLOCK_STATE_CHANGED
        settingsSubs.add(settingsRepo.provider().outFlow()
            .map { sett -> convertDataContainer(sett) { it.isNotificationControlEnabled } }
            .distinctUntilChanged().subscribe { notifEnabled ->
                notifEnabled.exec(object : DataContainer.Visitor<Boolean, Unit> {
                    override fun visitOk(v: DataContainer.Ok<Boolean>) {
                        if (v.data) {
                            startNotificationService()
                        } else {
                            stopNotificationService()
                        }
                    }

                    override fun visitPending(v: DataContainer.Pending<Boolean>) {
                        stopNotificationService()
                    }

                    override fun visitError(v: DataContainer.Error<Boolean>) {
                        stopNotificationService()
                    }
                })
            })

    }

    fun stop() {
        if (settingsSubs.size() > 0) {
            settingsSubs.clear()
            stopNotificationService()
        } else {
            Timber.w("RemoteControlRepository: start(): already stopped")
        }
    }

    private fun startNotificationService() {
        val intent = RemoteControlService.createStartIntent(ctx)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Timber.d("Starting as ctx.startForegroundService(intent) because version code is: ${Build.VERSION.SDK_INT}")
            ctx.startForegroundService(intent)
        } else {
            Timber.d("Starting as ctx.startService(intent) because version code is: ${Build.VERSION.SDK_INT}")
            ctx.startService(intent)
        }
    }

    private fun stopNotificationService() {
        val intent = RemoteControlService.createStopIntent(ctx)
        ctx.startService(intent) // for Stop command we are communicating with service and not starting it in foreground
    }

}
