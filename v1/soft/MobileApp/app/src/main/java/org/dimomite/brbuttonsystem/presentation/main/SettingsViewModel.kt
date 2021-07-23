package org.dimomite.brbuttonsystem.presentation.main

import android.view.View
import android.widget.CompoundButton
import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.FlowableTransformer
import org.dimomite.brbuttonsystem.domain.common.DataContainer
import org.dimomite.brbuttonsystem.domain.common.DataRepository
import org.dimomite.brbuttonsystem.domain.common.OkOnlyPassingTransformer
import org.dimomite.brbuttonsystem.domain.models.AppSettingsModel
import org.dimomite.brbuttonsystem.presentation.DisposingViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(settingRepo: DataRepository<AppSettingsModel>) : DisposingViewModel() {
//    private val fcData = MutableLiveData<Boolean>()
    val floatingControl: LiveData<Boolean>

//    private val lwData = MutableLiveData<Boolean>(false)
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
    }

}
