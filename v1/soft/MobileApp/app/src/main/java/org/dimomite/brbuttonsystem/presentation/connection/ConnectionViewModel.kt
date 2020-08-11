package org.dimomite.brbuttonsystem.presentation.connection

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import io.reactivex.Flowable
import org.dimomite.brbuttonsystem.presentation.DisposingViewModel

class ConnectionViewModel : DisposingViewModel() {

    val dataModel: LiveData<ConnectionUiModel> = LiveDataReactiveStreams.fromPublisher(
        Flowable.just(ConnectionUiModel(emptyArray(), false, true, false, true))
    )

    fun onStartScanClicked(v: View?) {}
    fun onStopScanClicked(v: View?) {}
    fun onConnectClicked(v: View?) {}
    fun onDisconnectClicked(v: View?) {}

}