package org.dimomite.brbuttonsystem.domain.models.devices

import org.dimomite.brbuttonsystem.domain.common.DataContainer

data class DevicesList(
    val virtualDeviceAllowed: DataContainer<Boolean>,
    val bluetoothDevicesReachable: DataContainer<Boolean>,
    val usbDevicesReachable: DataContainer<Boolean>,
    val devices: List<DeviceDescription>,
)
