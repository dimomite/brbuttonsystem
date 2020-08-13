package org.dimomite.brbuttonsystem.presentation.connection

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import core.Result
import dagger.hilt.android.AndroidEntryPoint
import org.dimomite.brbuttonsystem.connectionapi.ConnectionApi
import org.dimomite.brbuttonsystem.connectionapi.RemoteDevice
import org.dimomite.brbuttonsystem.data.connection.bt.BtConnectionCtrl
import org.dimomite.brbuttonsystem.databinding.FragmentConnectionBinding
import org.slf4j.LoggerFactory
import javax.inject.Inject

@AndroidEntryPoint
class ConnectionFragment : Fragment() {
    companion object {
        val logger = LoggerFactory.getLogger(ConnectionFragment::class.java)
    }

    @Inject
    lateinit var conn: BtConnectionCtrl

    private var subscription: Result<ConnectionApi.DeviceListSubscriptionResult, Exception> = Result.None

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        logger.debug("onCreateView(): DBG")
        return FragmentConnectionBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = this@ConnectionFragment;
            model = ConnectionViewModel()
        }.root
    }

    override fun onResume() {
        super.onResume()

        subscription = conn.subscribeRemoteDevicesListUpdates(object : ConnectionApi.RemoteDevicesListCallback {
            override fun onDevicesListUpdate(remoteDevices: Array<out RemoteDevice>) {
                remoteDevices.forEach { logger.debug("Device: $it") }
            }
        })
        when (subscription) {
            is Result.Ok, Result.None -> {
            }
            is Result.Error -> logger.warn("Device list subscription error: ${(subscription as Result.Error<Exception>).e}")
        }
    }

    override fun onPause() {
        if (subscription is Result.Ok) {
            conn.unsubscribeRemoteDevicesListUpdates((subscription as Result.Ok<ConnectionApi.DeviceListSubscriptionResult>).data.subscriptionId)
            subscription = Result.None
        }

        super.onPause()
    }

}