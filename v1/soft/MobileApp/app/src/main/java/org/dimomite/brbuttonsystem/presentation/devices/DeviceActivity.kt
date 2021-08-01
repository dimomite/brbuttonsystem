package org.dimomite.brbuttonsystem.presentation.devices

import android.app.PictureInPictureParams
import android.app.RemoteAction
import android.content.res.Configuration
import android.graphics.drawable.Icon
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.DrawableRes
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.disposables.CompositeDisposable
import org.dimomite.brbuttonsystem.GlobalConfigs
import org.dimomite.brbuttonsystem.R
import org.dimomite.brbuttonsystem.data.system.SettingsRepository
import org.dimomite.brbuttonsystem.domain.common.DataContainer
import org.dimomite.brbuttonsystem.domain.common.convertDataContainer
import org.dimomite.brbuttonsystem.remotecontrol.RemoteControlWidgetProvider
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class DeviceActivity : AppCompatActivity() {

    @Inject
    lateinit var settRepo: SettingsRepository

    @Inject
    lateinit var globalConfigs: GlobalConfigs

    private val subs = CompositeDisposable()
    private var isPipEnabled: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_device)

        if (globalConfigs.pictureInPictureAvailable) {
            subs.add(settRepo.provider().outFlow().map { convertDataContainer(it) { sett -> sett.isPipControlEnabled } }.distinctUntilChanged().subscribe({
                isPipEnabled = it.exec(object : DataContainer.Visitor<Boolean, Boolean> {
                    override fun visitOk(v: DataContainer.Ok<Boolean>): Boolean = v.data
                    override fun visitPending(v: DataContainer.Pending<Boolean>): Boolean = false
                    override fun visitError(v: DataContainer.Error<Boolean>): Boolean = false
                })
                Timber.i("DBG: isPipEnabled: $isPipEnabled")
            }, {
                Timber.w("Error in data setting flow")
                isPipEnabled = false
            }))
        }
    }

    override fun onUserLeaveHint() {
        super.onUserLeaveHint()

        if (globalConfigs.pictureInPictureAvailable) {
            if (isPipEnabled) {
                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O) {
                    val maxActionsCount = maxNumPictureInPictureActions
                    if (maxActionsCount >= GlobalConfigs.MIN_NUMBER_OF_ACTIONS_FOR_PIP_MODE) {
                        Timber.i("DBG: onUserLeaveHint(): before entering pip mode: max actions: $maxActionsCount")
                    } else {
                        Timber.w("DBG: onUserLeaveHint(): max number of pip action ($maxActionsCount) is less than needed (${GlobalConfigs.MIN_NUMBER_OF_ACTIONS_FOR_PIP_MODE})")
                    }

                    val params = PictureInPictureParams.Builder()
                        .setActions(
                            listOf(
                                createRemoteAction(RemoteControlWidgetProvider.ACTION_1, R.drawable.ic_button_stop_en),
                                createRemoteAction(RemoteControlWidgetProvider.ACTION_2, R.drawable.ic_button_20),
                                createRemoteAction(RemoteControlWidgetProvider.ACTION_3, R.drawable.ic_button_60),
                            )
                        )
                        .build()
                    enterPictureInPictureMode(params)
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createRemoteAction(action: String, @DrawableRes icon: Int) = RemoteAction(
        Icon.createWithResource(this, icon), "", "", RemoteControlWidgetProvider.createActionPendingIntent(this, action)
    )

    override fun onPictureInPictureModeChanged(isInPictureInPictureMode: Boolean, newConfig: Configuration?) {
        Timber.i("DBG: onPictureInPictureModeChanged(): isInPictureInPictureMode: $isInPictureInPictureMode, config: $newConfig")
        super.onPictureInPictureModeChanged(isInPictureInPictureMode, newConfig)

        val visibility = if (isInPictureInPictureMode) View.GONE else View.VISIBLE
        findViewById<View>(R.id.devices_action1_btn).visibility = visibility
        findViewById<View>(R.id.devices_action2_btn).visibility = visibility
        findViewById<View>(R.id.devices_action3_btn).visibility = visibility
    }

    override fun onDestroy() {
        subs.clear()
        super.onDestroy()
    }

}
