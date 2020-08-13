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

    /**
     * Subscribes callback to remote device list updates.
     * If scanning is not running it will be initiated with this call.
     * Implementation guarantees that method call is thread-safe.
     * Non blocking call.
     */
    fun subscribeRemoteDevicesListUpdates(callback: RemoteDevicesListCallback): Result<DeviceListSubscriptionResult, Exception>

    /**
     * Unsubscribe callback associated with provided subId.
     * Implementation guarantees that method call is thread-safe.
     * Non blocking call.
     */
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