package org.dimomite.brbuttonsystem.connectionapi

import core.Result
import org.dimomite.brbuttonsystem.core.AsyncResult
import java.io.InputStream
import java.io.OutputStream

interface ConnectionApi {
    interface RemoteDevicesListCallback {
        fun onDevicesListUpdate(remoteDevices: Array<out RemoteDevice>)
    }

    data class DeviceListSubscriptionResult(val subscriptionId: Long)

    fun subscribeRemoteDevicesListUpdates(callback: RemoteDevicesListCallback): Result<DeviceListSubscriptionResult, Exception>
    fun unsubscribeRemoteDevicesListUpdates(subId: Long)

    interface RemoteDeviceConnection {
        fun getInputStream(): InputStream
        fun getOutputStream(): OutputStream

        fun disconnect()
    }

    data class ConnectionResult(val connection: RemoteDeviceConnection)

    interface DeviceConnectionCallback {
        fun onConnectionChanged(connectionResult: AsyncResult<ConnectionResult, Exception>)
    }

    fun connectToRemoteDevice(remoteDevice: RemoteDevice, callback: DeviceConnectionCallback)

}