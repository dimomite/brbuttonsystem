package org.dimomite.brbuttonsystem

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import core.Result
import org.dimomite.brbuttonsystem.connectionapi.ConnectionApi
import org.dimomite.brbuttonsystem.connectionapi.RemoteDevice

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onResume() {
        super.onResume()
    }
}

class Some : ConnectionApi {
    override fun subscribeRemoteDevicesListUpdates(callback: ConnectionApi.RemoteDevicesListCallback): Result<ConnectionApi.DeviceListSubscriptionResult, Exception> {
        return Result.Ok(ConnectionApi.DeviceListSubscriptionResult(1L))
    }

    override fun unsubscribeRemoteDevicesListUpdates(subId: Long) {
    }

    override fun connectToRemoteDevice(remoteDevice: RemoteDevice) {
    }
}

