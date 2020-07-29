import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.control.Control
import javafx.scene.control.Label
import javafx.scene.input.MouseEvent
import java.net.URL
import java.util.*

class FXMLController : Initializable {

    @FXML
    private lateinit var statusLabel: Label

    private var connectionCtrl: ConnectionCtrl? = null

    override fun initialize(location: URL?, resources: ResourceBundle?) {
        val javaVersion = System.getProperty("java.version")
        val javafxVersion = System.getProperty("javafx.version")

        statusLabel.text = "Hello, JavaFX: $javafxVersion\n" +
                "Running on Java: $javaVersion."
    }

    fun release() {
        connectionCtrl?.disconnect()
    }

    @FXML
    private fun onRequest(mouseEvent: MouseEvent) {
        val id = (mouseEvent.source as Control).id
        val value: Int = when (id) {
            "button_red" -> 2
            "button_green" -> 3
            "button_yellow" -> 0
            "button_blue" -> 1
            else -> return
        }

        println("Button click: data: $value")
        sendData(value)
    }

    private fun sendData(value: Int) {
        val cc = connectionCtrl ?: return

        val data = ByteArray(63)
        data[0] = value.toByte()
        cc.send(2, data)
    }

    @FXML
    private fun onConnect(mouseEvent: MouseEvent) {
        connectionCtrl?.apply {
            disconnect()
            connectionCtrl = null
        }

        val devices = ConnectionCtrl.list()
        for (d in devices) {
            if (d.vendorId == 0x0483.toShort() && d.productId == 0x5750.toShort()) {
                connectionCtrl = ConnectionCtrl(d, object : ConnectionCtrl.Callback {
                    override fun onConnectionChange(isConnected: Boolean) {
                        println("Connection changed: $isConnected")
                    }

                    override fun onReport(reportId: Byte, data: ByteArray) {
                        println("report: reportId: $reportId, data: ${data.contentToString()}")
                    }
                })
            }
        }
    }

    @FXML
    private fun onDisconnect(mouseEvent: MouseEvent) {
        connectionCtrl?.disconnect()
    }

    @FXML
    private fun onListDeivces(mouseEvent: MouseEvent) {
        ConnectionCtrl.list().forEach{ println(it.path) }
    }

}