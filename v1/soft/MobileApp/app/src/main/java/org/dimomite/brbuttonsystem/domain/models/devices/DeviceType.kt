package org.dimomite.brbuttonsystem.domain.models.devices

enum class DeviceType {
    /**
     * Used did not select any device to connect.
     */
    NotSelected,

    /**
     * Virtual device fully emulated by the App.
     * For testing and debugging.
     */
    Virtual,

    /**
     * Device connected via USB interface.
     */
    Usb,

    /**
     * Device connected over Bluetooth.
     */
    Bluetooth,
}
