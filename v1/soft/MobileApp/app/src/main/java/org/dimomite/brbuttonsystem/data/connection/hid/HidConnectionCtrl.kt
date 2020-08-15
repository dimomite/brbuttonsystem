package org.dimomite.brbuttonsystem.data.connection.hid

import android.content.Context
import android.hardware.usb.UsbDevice
import android.hardware.usb.UsbManager
import core.Result
import dagger.hilt.android.qualifiers.ApplicationContext
import org.dimomite.brbuttonsystem.connectionapi.ConnectionApi
import org.dimomite.brbuttonsystem.connectionapi.RemoteDevice
import org.slf4j.LoggerFactory
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HidConnectionCtrl @Inject constructor(@ApplicationContext private val context: Context) : ConnectionApi {
    companion object {
        val logger = LoggerFactory.getLogger(HidConnectionCtrl::class.java)

        val brSystemVid = 0x0483
        val brSystemPid = 0x5750
    }

    private data class HidRemoteDevice(private val device: UsbDevice, private val hidDeviceAddress: String) : RemoteDevice {
        override fun getName(): String = device.productName ?: "<Name>"
        override fun getVersion(): String = device.version
        override fun getSerialNumber(): String = device.serialNumber ?: "<>"
        override fun getAddress(): String = hidDeviceAddress
    }

    override fun subscribeRemoteDevicesListUpdates(callback: ConnectionApi.RemoteDevicesListCallback): Result<ConnectionApi.DeviceListSubscriptionResult, Exception> {
        val usbMgr = context.getSystemService(UsbManager::class.java)
        val devices = usbMgr.deviceList
        logger.debug("Devices: ${devices.size}")
        val devArray = devices
            .filter { (_, d) -> (d.vendorId == brSystemVid) && (d.productId == brSystemPid) }
            .map { (address, device) -> HidRemoteDevice(device, address) }
            .toTypedArray()
        callback.onDevicesListUpdate(devArray)

        return Result.Ok(ConnectionApi.DeviceListSubscriptionResult(1)) // TODO remove hadrcoded value and do proper subscription handling
    }

    override fun unsubscribeRemoteDevicesListUpdates(subId: Long) {
    }

    override fun connectToRemoteDevice(remoteDevice: RemoteDevice, callback: ConnectionApi.DeviceConnectionCallback) {
    }

    override fun disconnectFromRemoteDevice(connection: ConnectionApi.RemoteDeviceConnection) {
    }

}