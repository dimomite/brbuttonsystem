package org.dimomite.brbuttonsystem.data.connection.bt

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import core.Result
import dagger.hilt.android.qualifiers.ApplicationContext
import org.dimomite.brbuttonsystem.connectionapi.ConnectionApi
import org.dimomite.brbuttonsystem.connectionapi.RemoteDevice
import org.slf4j.LoggerFactory
import java.util.*
import java.util.concurrent.locks.ReentrantReadWriteLock
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.collections.HashMap

@Singleton
class BtConnectionCtrl @Inject constructor(@ApplicationContext private val appContext: Context) : ConnectionApi {
    companion object {
        val logger = LoggerFactory.getLogger(BtConnectionCtrl::class.java)
        val serialPortUuid = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb")
        val rfcommUuid = UUID.fromString("00000003-0000-1000-8000-00805f9b34fb")
    }

    private val subscriptionLock = ReentrantReadWriteLock()
    private val devicesListSubscribers = HashMap<ConnectionApi.DeviceListSubscriptionResult, ConnectionApi.RemoteDevicesListCallback>()
    private var subIdsCounter = 1L;

    private data class BtRemoteDevice(val btDeviceName: String, val btDeviceAddress: String, val btDeviceSerialNumber: String, val btDeviceVersion: String) : RemoteDevice {
        override fun getName(): String = btDeviceName
        override fun getVersion(): String = btDeviceVersion
        override fun getSerialNumber(): String = btDeviceSerialNumber
        override fun getAddress(): String = btDeviceAddress
    }

    override fun subscribeRemoteDevicesListUpdates(callback: ConnectionApi.RemoteDevicesListCallback): Result<ConnectionApi.DeviceListSubscriptionResult, Exception> {
        val lock = subscriptionLock.writeLock()
        val needSub: Boolean
        val result: Result<ConnectionApi.DeviceListSubscriptionResult, Exception>

        lock.lock()
        try {
            for ((subId, oldCb) in devicesListSubscribers) {
                if (callback == oldCb) {
                    return Result.Error(RuntimeException("Duplicated subscription attempt. Already subscribe with $subId. Callback $callback"))
                }
            }
            val rr = ConnectionApi.DeviceListSubscriptionResult(subIdsCounter++)
            devicesListSubscribers[rr] = callback
            result = Result.Ok(rr)
            needSub = devicesListSubscribers.size == 1 // first subscription
        } finally {
            lock.unlock()
        }

        if (needSub) {
            subscribe()
        }

        return result
    }

    override fun unsubscribeRemoteDevicesListUpdates(subId: Long) {
        val lock = subscriptionLock.writeLock()
        lock.lock()
        val needUnsub: Boolean
        try {
            devicesListSubscribers.forEach { (subResult, _) ->
                if (subResult.subscriptionId == subId) {
                    devicesListSubscribers.remove(subResult)
                }
            }

            needUnsub = devicesListSubscribers.isEmpty()
        } finally {
            lock.unlock()
        }

        if (needUnsub) {
            unsubscribe()
        }
    }

    private fun subscribe() {
        // TODO add permission request
        val permission = ContextCompat.checkSelfPermission(appContext, Manifest.permission.BLUETOOTH)
        if (permission != PackageManager.PERMISSION_GRANTED) {
            logger.warn("${Manifest.permission.BLUETOOTH} permission is not granted. Please check app manifest")
            return
        }

        val btAdapter = BluetoothAdapter.getDefaultAdapter()
        val devices = btAdapter.bondedDevices

        // TODO update devices list once serial number and version can be read from BR System
        val devicesWithSerialPort = devices
            .filter {
                for (u in it.uuids) {
                    if (serialPortUuid.equals(u.uuid)) return@filter true
                }
                false
            }
            .map { BtRemoteDevice(it.name, it.address, "SN: ###", "Ver: ###") }.toTypedArray()

        val callbacks: Array<ConnectionApi.RemoteDevicesListCallback>
        val lock = subscriptionLock.readLock()
        lock.lock()
        try {
            callbacks = devicesListSubscribers.values.toTypedArray() // copy, to avoid callbacks invocation from critical section
        } finally {
            lock.unlock()
        }

        callbacks.forEach { it.onDevicesListUpdate(devicesWithSerialPort) }
    }

    private fun unsubscribe() {
    }

    override fun connectToRemoteDevice(remoteDevice: RemoteDevice, callback: ConnectionApi.DeviceConnectionCallback) {
    }
}