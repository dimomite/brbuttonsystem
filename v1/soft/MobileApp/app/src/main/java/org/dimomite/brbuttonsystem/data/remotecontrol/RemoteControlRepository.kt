package org.dimomite.brbuttonsystem.data.remotecontrol

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.content.ContextCompat
import dagger.hilt.android.qualifiers.ApplicationContext
import io.reactivex.rxjava3.core.BackpressureStrategy
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.subjects.PublishSubject
import org.dimomite.brbuttonsystem.data.channels.SystemAlertWindowNoPermission
import org.dimomite.brbuttonsystem.domain.channels.ChannelDataHandler
import org.dimomite.brbuttonsystem.domain.channels.DataChannel
import org.dimomite.brbuttonsystem.domain.channels.UnhandledState
import org.dimomite.brbuttonsystem.domain.channels.mapDataChannelData
import org.dimomite.brbuttonsystem.domain.common.DataProvider
import org.dimomite.brbuttonsystem.domain.common.DataRepository
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
        override fun outFlow(): Flowable<DataChannel<RemoteControlModel>> = eventsFlow
    }

    private val events = PublishSubject.create<DataChannel<RemoteControlModel>>()
    private val eventsFlow: Flowable<DataChannel<RemoteControlModel>> = events.toFlowable(BackpressureStrategy.LATEST).replay(1).refCount()

    private val settingsSubs = CompositeDisposable()

    fun start() {
        if (settingsSubs.size() > 0) {
            Timber.w("RemoteControlRepository: start(): already started")
            return
        }

        val settFlow = settingsRepo.provider().outFlow()

        // TODO later also check device connection status
        // TODO need to listen whether channel is enabled in Android app settings and restart when reenabled
        // probably this broadcast https://developer.android.com/reference/android/app/NotificationManager.html#ACTION_NOTIFICATION_CHANNEL_BLOCK_STATE_CHANGED
        settingsSubs.add(settFlow
            .map { sett -> mapDataChannelData(sett) { it.isNotificationControlEnabled } }
            .distinctUntilChanged().subscribe({ notifEnabled ->
                notifEnabled.execOnData(object : ChannelDataHandler<Boolean, Unit> {
                    override fun onData(data: Boolean) {
                        if (data) {
                            startNotificationService()
                        } else {
                            stopNotificationService()
                        }
                    }

                    override fun onNothing() {
                        stopNotificationService()
                    }
                })
            }, {
                Timber.w("Error during subscribing to setting repository: $it")
                stopNotificationService()
            })
        )

        settingsSubs.add(settFlow
            .map { sett -> mapDataChannelData(sett) { it.isFloatingControlEnabled } }
            .distinctUntilChanged().subscribe({ floatingEnabled ->
                floatingEnabled.execOnData(object : ChannelDataHandler<Boolean, Unit> {
                    override fun onData(data: Boolean) {
                        if (data) {
                            startFloatingControl()
                        } else {
                            stopFloatingControl()
                        }
                    }

                    override fun onNothing() {
                        stopFloatingControl()
                    }
                })
            }, {
                Timber.w("Error during subscribing to setting repository for floating remote control")
                stopFloatingControl()
            })
        )
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

    private fun startFloatingControl() {
        val perm: String = Manifest.permission.SYSTEM_ALERT_WINDOW
        val hasPermission = ContextCompat.checkSelfPermission(ctx, perm) == PackageManager.PERMISSION_GRANTED
        if (!hasPermission) {
            val errClass = SystemAlertWindowNoPermission::class.java
            val err = UnhandledState.NotAllowed(errClass) { args, result -> Timber.d("DBG: startFloatingControl(): retry callback: result: $result") }
            events.onNext(DataChannel.createError(err))
        }
        Timber.i("DBG: startFloatingControl(): hasPermission: $hasPermission")
    }

    private fun stopFloatingControl() {
        Timber.i("DBG: stopFloatingControl()")
    }

}
