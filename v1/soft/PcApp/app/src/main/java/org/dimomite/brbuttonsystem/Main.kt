package org.dimomite.brbuttonsystem

import core.Result
import org.dimomite.brbuttonsystem.connectionapi.ConnectionApi
import org.dimomite.brbuttonsystem.connectionapi.RemoteDevice

fun main(args: Array<String>) {
    println("Stating BrButtonSystem control PC app")

    val app = MainApp()
    app.doLaunch(args)

    println("App is shutting down")
}

class Some:  ConnectionApi {
    override fun subscribeRemoteDevicesListUpdates(callback: ConnectionApi.RemoteDevicesListCallback): Result<ConnectionApi.DeviceListSubscriptionResult, Exception> {
        return Result.Ok(ConnectionApi.DeviceListSubscriptionResult(1L))
    }

    override fun unsubscribeRemoteDevicesListUpdates(subId: Long) {
    }

    override fun connectToRemoteDevice(remoteDevice: RemoteDevice) {
    }
}
