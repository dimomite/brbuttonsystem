package org.dimomite.brbuttonsystem.presentation.main

import android.view.View
import android.widget.CheckBox
import android.widget.CompoundButton
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import org.dimomite.brbuttonsystem.domain.common.DataContainer
import org.dimomite.brbuttonsystem.domain.models.AppSettingsModel
import org.dimomite.brbuttonsystem.presentation.DisposingViewModel
import timber.log.Timber

class SettingsViewModel : DisposingViewModel() {
    private val fcData = MutableLiveData<Boolean>()
    val floatingControl: LiveData<Boolean> = fcData

    private val lwData = MutableLiveData<Boolean>(false)
    val launcherWidget: LiveData<Boolean> = lwData

    init {
        floatingControl.observeForever { Timber.d("Floating control enabled: $it") }
        launcherWidget.observeForever { Timber.d("Launcher widget enabled: $it") }

        fcData.value = false
        lwData.value = true
    }

    fun updateFromModel(mc: DataContainer<AppSettingsModel>) {
        when (mc) {
            is DataContainer.Ok -> {
                val m = mc.data
                fcData.postValue(m.isFloatingControlEnabled)
                lwData.postValue(m.isWidgetControlEnabled)
            }
        }
    }

    override fun onCleared() {
        Timber.d("DBG: Cleared")
        super.onCleared()
    }

    fun updateFloatingControl(v: View) {
        fcData.value = (v as CheckBox).isChecked
    }

    fun updateLauncherWidget(v: View) {
        lwData.value = (v as CheckBox).isChecked
    }

    fun launcherUpdate(v: CompoundButton, isChecked: Boolean) {
        lwData.value = isChecked
    }

}
