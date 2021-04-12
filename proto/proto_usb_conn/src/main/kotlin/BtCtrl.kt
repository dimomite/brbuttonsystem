import java.io.BufferedOutputStream
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.OutputStreamWriter
import javax.bluetooth.*
import javax.microedition.io.Connector
import javax.microedition.io.StreamConnection


class BtCtrl {
    private val uuid = UUID("00001101-0000-1000-8000-00805f9b34fb".replace("-", ""), false)

    fun init() {
        val ld = LocalDevice.getLocalDevice()
        println("LocalDevice: $ld, friendly name: ${ld.friendlyName}, class: ${ld.deviceClass}")
        val mac: String = ld.bluetoothAddress
        println("Address:$mac")
        val agent: DiscoveryAgent = ld.discoveryAgent
        println("agent: $agent")


        agent.startInquiry(DiscoveryAgent.GIAC, object : DiscoveryListener {
            override fun deviceDiscovered(btDevice: RemoteDevice?, cod: DeviceClass?) {
                val devClass = if (cod != null) {
                    "{$cod, serv: ${Integer.toHexString(cod.serviceClasses)}, " +
                            "major: ${Integer.toHexString(cod.majorDeviceClass)}, " +
                            "minor: ${Integer.toHexString(cod.minorDeviceClass)}}"
                } else {
                    "{$cod}"
                }
                val deviceInfo = if (btDevice != null) {
                    "$btDevice - ${btDevice::class.java.simpleName}"
                } else {
                    "$btDevice"
                }

                println("devices => deviceDiscovered(): btDevice: $deviceInfo, device class: $devClass")

                btDevice?.apply { discoverServices(agent, this) }
            }

            override fun inquiryCompleted(discType: Int) {
                println("devices => inquiryCompleted(): discType: ${BtHelper.descTypeToString(discType)}")
            }

            override fun servicesDiscovered(transID: Int, servRecord: Array<out ServiceRecord>?) {
                println("devices => servicesDiscovered(): transID: $transID, servRecord: $servRecord")
            }

            override fun serviceSearchCompleted(transID: Int, respCode: Int) {
                println("devices => serviceSearchCompleted(): transID: $transID, respCode: $respCode")
            }
        })
    }

    private fun discoverServices(agent: DiscoveryAgent, btDevice: RemoteDevice) {
        agent.searchServices(arrayOf(0x0001, 0x0100).toIntArray(), arrayOf(uuid), btDevice, object : DiscoveryListener {
            override fun servicesDiscovered(transID: Int, servRecord: Array<out ServiceRecord>?) {
                println("service => (${btDevice}): servicesDiscovered(): transID: $transID, servRecord: ${servRecord?.contentToString()}")
                servRecord?.forEach { connect(it) }
            }

            override fun serviceSearchCompleted(transID: Int, respCode: Int) {
                println("service => (${btDevice}): serviceSearchCompleted(): transID: $transID, respCode: $respCode")
            }

            override fun deviceDiscovered(btDevice: RemoteDevice?, cod: DeviceClass?) {
                println("service => (${btDevice}): deviceDiscovered(): btDevice: $btDevice, cod: $cod")
            }

            override fun inquiryCompleted(discType: Int) {
                println("service => (${btDevice}): inquiryCompleted(): discType: $discType")
            }
        })
    }

    private fun connect(record: ServiceRecord) {
        println("connect(): record: $record")
        val url = record.getConnectionURL(ServiceRecord.NOAUTHENTICATE_NOENCRYPT, false)
        println("connect(): url: $url")
        val connection = Connector.open(url)
        try {
            if (connection is StreamConnection) {
                val os = connection.openDataOutputStream()
                os.writeByte(15)
                os.flush()
                os.close()
            }
        } finally {
            connection.close()
        }
    }

    // 00001101-0000-1000-8000-00805f9b34fb

    fun release() {

    }

}