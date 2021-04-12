import javax.bluetooth.DiscoveryListener

class BtHelper {
    companion object {
        fun descTypeToString(discType: Int) = when (discType) {
            DiscoveryListener.INQUIRY_COMPLETED -> "INQUIRY_COMPLETED"
            DiscoveryListener.INQUIRY_TERMINATED -> "INQUIRY_TERMINATED"
            DiscoveryListener.INQUIRY_ERROR -> "INQUIRY_ERROR"
            DiscoveryListener.SERVICE_SEARCH_COMPLETED -> "SERVICE_SEARCH_COMPLETED"
            DiscoveryListener.SERVICE_SEARCH_TERMINATED -> "SERVICE_SEARCH_TERMINATED"
            DiscoveryListener.SERVICE_SEARCH_ERROR -> "SERVICE_SEARCH_ERROR"
            DiscoveryListener.SERVICE_SEARCH_NO_RECORDS -> "SERVICE_SEARCH_NO_RECORDS"
            DiscoveryListener.SERVICE_SEARCH_DEVICE_NOT_REACHABLE -> "SERVICE_SEARCH_DEVICE_NOT_REACHABLE"
            else -> "<Undefined: $discType {0x${Integer.toHexString(discType)}}>"
        }

    }

}