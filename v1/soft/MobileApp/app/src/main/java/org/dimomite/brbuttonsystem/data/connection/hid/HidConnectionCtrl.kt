package org.dimomite.brbuttonsystem.data.connection.hid

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.hardware.usb.*
import android.os.Build
import androidx.annotation.RequiresApi
import core.Result
import dagger.hilt.android.qualifiers.ApplicationContext
import org.dimomite.brbuttonsystem.connectionapi.ConnectionApi
import org.dimomite.brbuttonsystem.connectionapi.RemoteDevice
import org.slf4j.LoggerFactory
import java.nio.ByteBuffer
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@RequiresApi(Build.VERSION_CODES.M) // to use device.version call
class HidConnectionCtrl @Inject constructor(@ApplicationContext private val context: Context) : ConnectionApi {
    companion object {
        val logger = LoggerFactory.getLogger(HidConnectionCtrl::class.java)

        private const val brSystemVid = 0x0483
        private const val brSystemPid = 0x5750
        private const val ACTION_USB_PERMISSION = "com.android.example.USB_PERMISSION"

        fun logDevice(device: UsbDevice) {
            val cfgCount = device.configurationCount
            logger.debug("Total number of configurations: $cfgCount")
            for (cfgIdx in 0 until cfgCount) {
                val cfg = device.getConfiguration(cfgIdx)
                logger.debug("Configuration #$cfgIdx: id: ${cfg.id}, name: \"${cfg.name}\", maxPower: ${cfg.maxPower}")

                val itfCount = cfg.interfaceCount
                logger.debug("\tTotal number of interfaces for configuration: $itfCount")
                for (itfIdx in 0 until itfCount) {
                    val itf = cfg.getInterface(itfIdx)
                    logger.debug("\tInterface #$itfIdx: name: \"${itf.name}\", class: ${itf.interfaceClass}, subclass: ${itf.interfaceSubclass}, protocol: ${itf.interfaceProtocol}")

                    val epCount = itf.endpointCount

                    logger.debug("\t\tTotal number of endpoints: $epCount")
                    for (epIdx in 0 until epCount) {
                        val ep = itf.getEndpoint(epIdx)
                        logger.debug("\t\tEndpoint #$epIdx: ${ep}, packetSize: ${ep.maxPacketSize}")
                    }
                }
            }
        }

        private fun dirToString(dir: Int) = when (dir) {
            UsbConstants.USB_DIR_OUT -> "OUT"
            UsbConstants.USB_DIR_IN -> "IN"
            else -> "<Invalid: ${String.format("0x02X", dir)}>"
        }
    }

    private data class HidRemoteDevice(internal val device: UsbDevice, internal val hidDeviceAddress: String) : RemoteDevice {
        override fun getName(): String = device.productName ?: "<Name>"
        override fun getVersion(): String = device.version // FIXME this requires API23
        override fun getSerialNumber(): String = device.serialNumber ?: "<>"
        override fun getAddress(): String = hidDeviceAddress
    }

    override fun subscribeRemoteDevicesListUpdates(callback: ConnectionApi.RemoteDevicesListCallback): Result<ConnectionApi.DeviceListSubscriptionResult, Exception> {
        val usbMgr = context.getSystemService(Context.USB_SERVICE) as UsbManager
        val devices = usbMgr.deviceList
        logger.debug("Devices: ${devices.size}")
        val devArray = devices
            .filter { (_, d) -> (d.vendorId == brSystemVid) && (d.productId == brSystemPid) }
            .map { (address, device) -> HidRemoteDevice(device, address) }
            .toTypedArray()
        callback.onDevicesListUpdate(devArray)

        devArray.forEach { logDevice(it.device) }
        if (devArray.isNotEmpty()) connectToDevice(devArray[0].device, usbMgr)

        return Result.Ok(ConnectionApi.DeviceListSubscriptionResult(1)) // TODO remove hardcoded value and do proper subscription handling
    }

    private fun connectToDevice(device: UsbDevice, usbMgr: UsbManager) {
        val pi = PendingIntent.getBroadcast(context, 0, Intent(ACTION_USB_PERMISSION), 0)
        val filter = IntentFilter(ACTION_USB_PERMISSION)
        context.registerReceiver(usbReceiver, filter) // TODO need to unregister

        usbMgr.requestPermission(device, pi)
    }

    private val usbReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            if (ACTION_USB_PERMISSION == intent.action) {
                synchronized(this) {
                    val device: UsbDevice? = intent.getParcelableExtra(UsbManager.EXTRA_DEVICE)

                    if (intent.getBooleanExtra(UsbManager.EXTRA_PERMISSION_GRANTED, false)) {
                        logger.debug("Permission was granted, device: $device")
                        device?.apply {
                            val usbMgr = context.getSystemService(Context.USB_SERVICE) as UsbManager
                            val connection = usbMgr.openDevice(this)
                            logger.debug("Connection: $connection")

                            // TODO do proper endpoint connection, without so many hardcoded numbers
                            val itf = device.getInterface(0);
                            val claimed = connection.claimInterface(itf, true)
                            logger.debug("Interface claimed: $claimed")
                            if (claimed) {
                                try {
                                    val epOut = itf.getEndpoint(1)
                                    logger.debug("epOut: direction: ${dirToString(epOut.direction)}")

                                    doSend(1, connection, epOut)
                                    Thread.sleep(500)
                                    doSend(2, connection, epOut)
                                    Thread.sleep(500)
                                    doSend(4, connection, epOut)
                                    Thread.sleep(500)

                                    doSend(8, connection, epOut)
                                    Thread.sleep(500)
                                    doSend(10, connection, epOut)
                                } finally {
                                    connection.releaseInterface(itf)
                                }
                            }

                            Thread {
                                val claimedForRead = connection.claimInterface(itf, true)
                                logger.debug("Interface claimed for reading: $claimedForRead")
                                if (claimedForRead) {
                                    try {
                                        val epIn = itf.getEndpoint(0)
                                        logger.debug("epIn: direction: ${dirToString(epIn.direction)}")

                                        while (true) {
                                            val buff = ByteArray(64) { 0 }
                                            connection.bulkTransfer(epIn, buff, 64, 0)
                                            if (buff[0] == 1.toByte()) { // 1 is reportId for IN
                                                logger.debug("Data received: ${buff.contentToString()}")
                                            }
                                        }
                                    } finally {
                                        connection.releaseInterface(itf)
                                    }
                                }

                                connection.close()
                            }.start()

                        }
                    } else {
                        logger.warn("Permission denied for device $device")
                    }
                }
            }
        }
    }

    private fun doSend(b: Byte, connection: UsbDeviceConnection, ep: UsbEndpoint) {
        val payload = ByteBuffer.allocate(64)
        payload.put(2) // reportId
        payload.put(b) // data
        payload.put(ByteArray(62) { i: Int -> i.toByte() })

        val buffer = ByteArray(64) { i -> payload[i] }
        // I don't know why Android uses bulkTransfer() to send data to HID which uses Interrupt for communication. However it works.
        val transferred = connection.bulkTransfer(ep, buffer, 64, 0)
        logger.debug("Transferred: $transferred")
    }

    override fun unsubscribeRemoteDevicesListUpdates(subId: Long) {
    }

    override fun connectToRemoteDevice(remoteDevice: RemoteDevice, callback: ConnectionApi.DeviceConnectionCallback) {
    }

    override fun disconnectFromRemoteDevice(connection: ConnectionApi.RemoteDeviceConnection) {
    }

}