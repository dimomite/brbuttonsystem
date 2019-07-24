import Main.Companion.data
import purejavahidapi.HidDevice
import purejavahidapi.PureJavaHidApi

class Main {
    companion object {
        val DEVICE_VID: Short = 0x483
        val DEVICE_PID: Short = 0x5750
        val data: ByteArray = byteArrayOf(2, 0, 0, 0)
    }
}

fun main() {


    val devices = PureJavaHidApi.enumerateDevices()
    println("$devices")
    data.reverse()

    for (d in devices) {
        val vid = d.vendorId
        val pid = d.productId
        System.out.printf("VID: 0x%04X, PID: 0x%04X. Manufact: \"%s\", product: \"%s\""
            , vid, pid, d.manufacturerString, d.productString)
        if (vid == Main.DEVICE_VID && pid == Main.DEVICE_PID) {
            val hidDevice: HidDevice = PureJavaHidApi.openDevice(d)
            try {
                println("setOutputReport")
                val result = hidDevice.setOutputReport(1, Main.data, 4)
                println("setOutputReport: result: $result")
            } finally {
                hidDevice.close()
            }
        }
    }

}
