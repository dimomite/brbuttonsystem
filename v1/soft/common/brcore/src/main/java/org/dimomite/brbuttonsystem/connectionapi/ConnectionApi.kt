package org.dimomite.brbuttonsystem.connectionapi

import core.Result

interface ConnectionApi {
    interface RemoteDevicesListCallback {
        fun onDevicesListUpdate(remoteDevices: Array<out RemoteDevice>)
    }

    data class DeviceListSubscriptionResult(val subscriptionId: Long)

    fun subscribeRemoteDevicesListUpdates(callback: RemoteDevicesListCallback): Result<DeviceListSubscriptionResult, Exception>
    fun unsubscribeRemoteDevicesListUpdates(subId: Long)

    fun connectToRemoteDevice(remoteDevice: RemoteDevice)


}