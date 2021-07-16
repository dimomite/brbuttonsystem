package org.dimomite.brbuttonsystem.presentation.main

import android.view.View
import android.widget.CheckBox
import android.widget.CompoundButton
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import org.dimomite.brbuttonsystem.presentation.DisposingViewModel
import timber.log.Timber
import java.util.*
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit

class SettingsViewModel : DisposingViewModel() {
    private val fcData = MutableLiveData<Boolean>()
    val floatingControl: LiveData<Boolean> = fcData

    private val lwData = MutableLiveData<Boolean>(false)
    val launcherWidget: LiveData<Boolean> = lwData

    private val job: ScheduledExecutorService = Executors.newScheduledThreadPool(1)

    init {
        floatingControl.observeForever { Timber.d("Floating control enabled: $it") }
        launcherWidget.observeForever { Timber.d("Launcher widget enabled: $it") }

        fcData.value = false
        lwData.value = true

        job.scheduleAtFixedRate({
            val current = lwData.value ?: false
            Timber.d("DBG: Tick: $current")
            lwData.postValue(!current)
        }, 1000L, 2000L, TimeUnit.MILLISECONDS)
    }

    override fun onCleared() {
        Timber.d("DBG: Cleared")
        job.shutdown()

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
