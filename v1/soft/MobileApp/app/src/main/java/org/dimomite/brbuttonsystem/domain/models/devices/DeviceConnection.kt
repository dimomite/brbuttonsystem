package org.dimomite.brbuttonsystem.domain.models.devices

import java.util.concurrent.atomic.AtomicInteger

class DeviceConnection private constructor(val id: Int, val name: String, val type: DeviceType) {
    companion object {
        private const val notSelectedDeviceId = 0
        private val ids = AtomicInteger(notSelectedDeviceId + 1)

        private val notSelectedInstance = DeviceConnection(notSelectedDeviceId, "---", DeviceType.NotSelected)
        fun notSelected(): DeviceConnection = notSelectedInstance

        fun create(name: String, type: DeviceType): DeviceConnection {
            if (type == DeviceType.NotSelected) return notSelected()

            val instance = DeviceConnection(ids.getAndIncrement(), name, type)
            return instance
        }
    }

}
