package org.dimomite.brbuttonsystem.presentation.main

import android.view.View
import android.widget.CompoundButton
import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.FlowableTransformer
import io.reactivex.rxjava3.functions.Consumer
import io.reactivex.rxjava3.functions.Function
import org.dimomite.brbuttonsystem.domain.common.DataContainer
import org.dimomite.brbuttonsystem.domain.common.DataRepository
import org.dimomite.brbuttonsystem.domain.common.OkOnlyPassingTransformer
import org.dimomite.brbuttonsystem.domain.models.AppSettingsModel
import org.dimomite.brbuttonsystem.presentation.DisposingViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(private val settingRepo: DataRepository<AppSettingsModel>) : DisposingViewModel() {
    val floatingControl: LiveData<Boolean>
    val launcherWidget: LiveData<Boolean>

    init {
        val transformer: FlowableTransformer<DataContainer<AppSettingsModel>, AppSettingsModel> = OkOnlyPassingTransformer()
        val flow: Flowable<AppSettingsModel> = settingRepo.provider().outFlow().compose(transformer).replay(1).refCount()
        floatingControl = LiveDataReactiveStreams.fromPublisher(flow.map { sett -> sett.isFloatingControlEnabled })
        launcherWidget = LiveDataReactiveStreams.fromPublisher(flow.map { sett -> sett.isWidgetControlEnabled })

        floatingControl.observeForever { Timber.d("Floating control enabled: $it") }
        launcherWidget.observeForever { Timber.d("Launcher widget enabled: $it") }
    }

    fun updateFloatingControl(v: View) {
    }

    fun launcherUpdate(v: CompoundButton, isChecked: Boolean) {
        event {
            settingRepo.modifier().modifyData { it.copy(isWidgetControlEnabled = isChecked) }.subscribe(
                { Timber.d("Completed checked change") }, { Timber.w("Checked change processing failed") })
        }
    }

}
