package org.dimomite.brbuttonsystem.presentation.devices

import android.app.PictureInPictureParams
import android.app.RemoteAction
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.disposables.CompositeDisposable
import org.dimomite.brbuttonsystem.GlobalConfigs
import org.dimomite.brbuttonsystem.R
import org.dimomite.brbuttonsystem.data.system.SettingsRepository
import org.dimomite.brbuttonsystem.domain.common.DataContainer
import org.dimomite.brbuttonsystem.domain.common.convertDataContainer
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
                    Timber.i("DBG: onUserLeaveHint(): before entering pip mode: max actions: $maxActionsCount")
                    val params = PictureInPictureParams.Builder()
                        .setActions(listOf()) // TODO
                        .build()
                    enterPictureInPictureMode(params)
                }
            }
        }
    }

    override fun onDestroy() {
        subs.clear()
        super.onDestroy()
    }

}
