package org.dimomite.brbuttonsystem.remotecontrol

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.pm.ServiceInfo
import android.os.Build
import android.os.IBinder
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.disposables.CompositeDisposable
import org.dimomite.brbuttonsystem.R
import org.dimomite.brbuttonsystem.domain.common.DataContainer
import org.dimomite.brbuttonsystem.domain.common.DataRepository
import org.dimomite.brbuttonsystem.domain.common.convertDataContainer
import org.dimomite.brbuttonsystem.domain.models.AppSettingsModel
import org.dimomite.brbuttonsystem.presentation.main.SettingsViewModel
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class RemoteControlService : Service() {
    companion object {
        private const val ACTION_START = "ActionStart"
        private const val ACTION_STOP = "ActionStop"
        private const val ACTION_BUTTON_1 = "ActionButton1"
        private const val ACTION_BUTTON_2 = "ActionButton2"
        private const val ACTION_BUTTON_3 = "ActionButton3"

        private val icons: Map<String, Int> = mapOf(
            Pair(ACTION_BUTTON_1, 0),
            Pair(ACTION_BUTTON_2, 0),
            Pair(ACTION_BUTTON_3, 0),
        )

        private val names: Map<String, Int> = mapOf(
            Pair(ACTION_BUTTON_1, R.string._button_text_stop),
            Pair(ACTION_BUTTON_2, R.string._button_text_20),
            Pair(ACTION_BUTTON_3, R.string._button_text_60),
        )


        private const val NOTIFICATION_CHANNEL_ID = "RemoteControlNotificationChannel"
        private const val NOTIFICATION_ID = 1

        fun createStartIntent(ctx: Context): Intent = Intent(ctx, RemoteControlService::class.java).setAction(ACTION_START)
        fun createStopIntent(ctx: Context): Intent = Intent(ctx, RemoteControlService::class.java).setAction(ACTION_STOP)
    }

    @Inject
    lateinit var settRepo: DataRepository<AppSettingsModel>
    private val subs = CompositeDisposable()

    private lateinit var floatingWindow: FloatingRemoteWindow

    override fun onBind(intent: Intent?): IBinder? {
        throw IllegalStateException("onBind() is not implemented for RemoteControlService")
    }

    override fun onCreate() {
        super.onCreate()

        if (!this::floatingWindow.isInitialized) {
            floatingWindow = FloatingRemoteWindow(this)
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Timber.d("Creating notification channel: \"$NOTIFICATION_CHANNEL_ID\"")
            val mgr = NotificationManagerCompat.from(this)
            val ch = NotificationChannel(
                NOTIFICATION_CHANNEL_ID,
                getString(R.string.Remote_control_notification_channel),
                NotificationManager.IMPORTANCE_DEFAULT
            )
            mgr.createNotificationChannel(ch)
        }

        subs.add(settRepo.provider().outFlow().map { sett -> convertDataContainer(sett) { it.isFloatingControlEnabled } }.subscribe({
            it.exec(object : DataContainer.Visitor<Boolean, Unit> {
                override fun visitOk(v: DataContainer.Ok<Boolean>) {
                    val show = v.data
                    Timber.i("DBG: ===> create floating window: show: $show")
                    if (show) {
                        floatingWindow.initFloatingWindow()
                    } else {
                        floatingWindow.destroyFloatingWindow()
                    }

                    Timber.i("DBG: ===> floating window is created")
                }

                override fun visitPending(v: DataContainer.Pending<Boolean>) {
                    Timber.i("DBG: Settings flow is pended: ${v.progress}")
                }

                override fun visitError(v: DataContainer.Error<Boolean>) {
                    Timber.w("DBG: Error in settings flow: ${v.er}")
                }
            })
        }, {
            Timber.e("DBG: Error with setting data flow for floating control window: ${it.printStackTrace()}")
        }))
    }

    override fun onDestroy() {
        floatingWindow.destroyFloatingWindow()
        subs.clear()

        super.onDestroy()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (intent == null) return START_NOT_STICKY

        val action = intent.action
        Timber.d("onStartCommand(): action: \"$action\"")
        when (action) {
            ACTION_START -> startInForeground()
            ACTION_STOP -> stopThisService()
            ACTION_BUTTON_1, ACTION_BUTTON_2, ACTION_BUTTON_3 -> handleButtonAction(action, intent)
            else -> throw IllegalStateException("Unhandled action: \"$action\"")
        }

        return START_STICKY
    }

    private fun startInForeground() {
        val builder = NotificationCompat.Builder(this.applicationContext, NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_remote_control_icon)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentTitle(getString(R.string.BR_control))
            .setContentText(getString(R.string.Remote_control_from_BR_system))
            .addAction(createButtonAction(ACTION_BUTTON_1, this))
            .addAction(createButtonAction(ACTION_BUTTON_2, this))
            .addAction(createButtonAction(ACTION_BUTTON_3, this))

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            // added in SDK 29
            startForeground(NOTIFICATION_ID, builder.build(), ServiceInfo.FOREGROUND_SERVICE_TYPE_CONNECTED_DEVICE)
        } else {
            startForeground(NOTIFICATION_ID, builder.build())
        }
    }

    private fun createButtonAction(action: String, ctx: Context): NotificationCompat.Action {
        @DrawableRes val icon = icons[action] ?: throw IllegalArgumentException("No icon for action: \"$action\"")
        @StringRes val text = names[action] ?: throw IllegalArgumentException("No name for action: \"$action\"")
        val intent = Intent(ctx, RemoteControlService::class.java).setAction(action)
        val cb = PendingIntent.getService(ctx, 1, intent, 0)
        val builder = NotificationCompat.Action.Builder(icon, ctx.getString(text), cb)
        return builder.build()
    }

    private fun stopThisService() {
        stopForeground(true)
        stopSelf()
    }

    private fun handleButtonAction(button: String, intent: Intent) {
        Timber.d("DBG: handleButtonAction(): button: \"$button\", intent: $intent")
    }

}
