import purejavahidapi.*
import java.io.IOException
import java.util.concurrent.atomic.AtomicBoolean

class ConnectionCtrl(deviceInfo: HidDeviceInfo, callback: Callback) {
    companion object {
        fun list() = PureJavaHidApi.enumerateDevices()
    }

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

    fun send(reportId: Byte, data: ByteArray): Boolean {
        if (!connected.get()) return false
        if (data.size != 63) return false

        device?.setOutputReport(reportId, data, data.size) ?: return false

        return true
    }

    fun disconnect() {
        if (connected.get()) {
            device?.close()
        }
    }
}