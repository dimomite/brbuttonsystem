package org.dimomite.brbuttonsystem.presentation.main

import android.view.View
import android.widget.CompoundButton
import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.FlowableTransformer
import org.dimomite.brbuttonsystem.GlobalConfigs
import org.dimomite.brbuttonsystem.domain.common.DataContainer
import org.dimomite.brbuttonsystem.domain.common.DataRepository
import org.dimomite.brbuttonsystem.domain.common.OkOnlyPassingTransformer
import org.dimomite.brbuttonsystem.domain.models.AppSettingsModel
import org.dimomite.brbuttonsystem.presentation.DisposingViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val settingRepo: DataRepository<AppSettingsModel>,
    private val conf: GlobalConfigs,
) : DisposingViewModel() {
    val floatingControl: LiveData<Boolean>
    val launcherWidget: LiveData<Boolean>
    val notificationControl: LiveData<Boolean>
    val pipControl: LiveData<Boolean>
    val pipVisible: Int = if (conf.pictureInPictureAvailable) View.VISIBLE else View.GONE

    init {
        val transformer: FlowableTransformer<DataContainer<AppSettingsModel>, AppSettingsModel> = OkOnlyPassingTransformer()
        val flow: Flowable<AppSettingsModel> = settingRepo.provider().outFlow().compose(transformer).replay(1).refCount()
        floatingControl = LiveDataReactiveStreams.fromPublisher(flow.map { sett -> sett.isFloatingControlEnabled })
        launcherWidget = LiveDataReactiveStreams.fromPublisher(flow.map { sett -> sett.isWidgetControlEnabled })
        notificationControl = LiveDataReactiveStreams.fromPublisher(flow.map { sett -> sett.isNotificationControlEnabled })
        pipControl = LiveDataReactiveStreams.fromPublisher(flow.map { sett -> sett.isPipControlEnabled })
    }

    fun launcherUpdate(v: CompoundButton, isChecked: Boolean) {
        event {
            settingRepo.modifier().modifyData { it.copy(isWidgetControlEnabled = isChecked) }.subscribe(
                { Timber.d("Completed checked change") }, { Timber.w("Checked change processing failed") })
        }
    }

    fun notificationUpdate(v: CompoundButton, isChecked: Boolean) {
        event {
            settingRepo.modifier().modifyData { it.copy(isNotificationControlEnabled = isChecked) }.subscribe(
                { Timber.d("Completed checked change") }, { Timber.w("Checked change processing failed") })
        }

    }

    fun floatingUpdate(v: CompoundButton, isChecked: Boolean) {
        event {
            settingRepo.modifier().modifyData { it.copy(isFloatingControlEnabled = isChecked) }.subscribe(
                { Timber.d("Completed checked change") }, { Timber.w("Checked change processing failed") })
        }
    }

    fun pipUpdate(v: CompoundButton, isChecked: Boolean) {
        if (!conf.pictureInPictureAvailable) {
            Timber.w("pipUpdate(): picture-in-picture (pip) is not available. This function should never be called.")
            return
        }

        event {
            settingRepo.modifier().modifyData { it.copy(isPipControlEnabled = isChecked) }.subscribe(
                { Timber.d("Completed checked change") }, { Timber.w("Checked change processing failed") })
        }
    }

}
