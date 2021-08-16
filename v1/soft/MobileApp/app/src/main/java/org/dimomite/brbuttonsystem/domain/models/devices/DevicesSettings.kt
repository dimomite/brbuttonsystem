package org.dimomite.brbuttonsystem.domain.models.devices

data class DevicesSettings(
    val btDevicesEnabled: Boolean,
    val usbDevicesEnabled: Boolean,
    val virtualDevicesEnabled: Boolean,
)
