import purejavahidapi.*
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.util.*
import java.util.concurrent.atomic.AtomicBoolean
import kotlin.collections.HashMap

class Main {
    companion object {
        val DEVICE_VID: Short = 0x483
        val DEVICE_PID: Short = 0x5750
        val data: ByteArray = byteArrayOf(5, 0, 0, 0) // switch between 2 and 5 to turn on/off LED on PC13
    }
}

class ConnectionCtrl(deviceInfo: HidDeviceInfo, callback: Callback) {
    interface Callback {
        fun onConnectionChange(isConnected: Boolean)
        fun onReport(reportId: Byte, data: ByteArray)
    }

    private var device: HidDevice?
    private val connected = AtomicBoolean(false)

    init {
        device = try {
            PureJavaHidApi.openDevice(deviceInfo)
        } catch (e: IOException) {
            println("Error while opening device: $deviceInfo\n$e")
            null
        }
        if (device != null) connected.set(true)
        callback.onConnectionChange(connected.get())

        device?.inputReportListener =
            InputReportListener { _, reportId, data, _ -> // device and reportLength are not needed here
                callback.onReport(reportId, data)
            }

        device?.deviceRemovalListener =
            DeviceRemovalListener { _ ->
                connected.set(false)
                callback.onConnectionChange(connected.get())
                device?.inputReportListener = null
            }
    }

    fun disconnect() {
        if (connected.get()) {
            device?.close()
        }
    }
}

fun main() {
    val devices: MutableMap<Short, ConnectionCtrl> = HashMap()
    val reader = BufferedReader(InputStreamReader(System.`in`))

    val cmdConnect = "conn "
    val cmdDisconnect = "disconn "

    while (true) {
        println("=== Enter command ===")

        val cmd = reader.readLine()
        if (cmd == null || cmd.isEmpty()) continue

        if (cmd.startsWith("quit") || cmd.startsWith("exit")) {
            devices.forEach { (_, ctrl) -> ctrl.disconnect() }

            println("Quiting")
            break
        }

        if ("list" == cmd) {
            println("List of USB HID devices: ")
            PureJavaHidApi.enumerateDevices().forEach { println(it.path) }
            continue
        }

        if (cmd.startsWith(cmdConnect)) {
            val data = cmd.removePrefix(cmdConnect)
            val vid: Short
            vid = parseVid(data) ?: continue

            if (devices.containsKey(vid)) {
                println("Already connected to device with VID: \"$data\"")
                continue
            }

            for (device in PureJavaHidApi.enumerateDevices()) {
                if (device.vendorId == vid) {
                    devices.put(vid, ConnectionCtrl(device, object : ConnectionCtrl.Callback {
                        override fun onConnectionChange(isConnected: Boolean) {
                            if (isConnected) {
                                println("Connected to device: VID: \"$data\"")
                            } else {
                                devices.remove(vid)
                                println("Device disconnected: VID: \"$data\"")
                            }
                        }

                        override fun onReport(reportId: Byte, data: ByteArray) {
                            println("Got report: id: $reportId, data: ${Arrays.toString(data)}")
                        }
                    }))
                }
            }
            continue
        } // if (cmd.startsWith(cmd_connect)) {

        if ("disc all" == cmd) {
            devices.forEach { (_, ctrl) -> ctrl.disconnect() }
            continue
        }

        if (cmd.startsWith(cmdDisconnect)) {
            val data = cmd.removePrefix(cmdConnect)
            val vid: Short
            vid = parseVid(data) ?: continue

            val ctrl = devices[vid]
            ctrl?.disconnect() ?: println("Not connected to device \"$vid\", can not disconnect")

            continue
        }

        println("Unhandled command: \"$cmd\"")
    }
}

fun parseVid(data: String): Short? {
    try {
        val v = Integer.parseInt(data, 16);

        if (v > Short.MAX_VALUE) {
            println("Illegal VID value: \"$data\", should be less or equal ${Short.MAX_VALUE}")
            return null
        }
        if (v < Short.MIN_VALUE) {
            println("Illegal VID value: \"$data\", should be greater or equal to ${Short.MIN_VALUE}")
            return null
        }

        return v.toShort()
    } catch (e: NumberFormatException) {
        println("Can not parse VID, not a hex number: \"$data\"")
        return null
    }
}

