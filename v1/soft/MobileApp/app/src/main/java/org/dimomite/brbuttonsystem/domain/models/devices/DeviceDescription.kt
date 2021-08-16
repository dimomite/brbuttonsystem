package org.dimomite.brbuttonsystem.domain.models.devices

/**
 * This class represent device not connected to the Application.
 * Device can be connected/paired to smartphone or PC.
 * However it still haven't received any message from the Application.
 */
sealed class DeviceDescription(val type: DeviceType, val history: DeviceHistory) {
    interface VisitorR<R> {
        fun visitVirtual(v: VirtualDeviceDescription): R
        fun visitUsb(v: UsbDeviceDescription): R
        fun visitBluetooth(v: BtDeviceDescription): R
    }

    abstract fun <R> exec(v: VisitorR<R>): R

    class VirtualDeviceDescription(history: DeviceHistory) : DeviceDescription(DeviceType.Virtual, history) {
        override fun <R> exec(v: VisitorR<R>): R = v.visitVirtual(this)
    }

    class UsbDeviceDescription(val serial: String, history: DeviceHistory) : DeviceDescription(DeviceType.Usb, history) {
        override fun <R> exec(v: VisitorR<R>): R = v.visitUsb(this)
    }

    class BtDeviceDescription(val name: String, history: DeviceHistory) : DeviceDescription(DeviceType.Bluetooth, history) {
        override fun toString(): String = "BtDeviceDescription: name: \"$name\""

        override fun <R> exec(v: VisitorR<R>): R = v.visitBluetooth(this)
    }

}

data class DeviceHistory(val canAutoconnect: Boolean)
