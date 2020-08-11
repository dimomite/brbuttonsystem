package org.dimomite.brbuttonsystem.presentation.connection

data class ConnectionUiModel(
    val devices: Array<DeviceUiModel>,
    val connectEnabled: Boolean,
    val disconnectEnabled: Boolean,
    val startScanEnabled: Boolean,
    val stopScanEnabled: Boolean
)