package org.dimomite.brbuttonsystem.data.devices

import org.dimomite.brbuttonsystem.domain.channels.DataChannel
import org.dimomite.brbuttonsystem.domain.common.BehaviorDataRepository
import org.dimomite.brbuttonsystem.domain.models.devices.DevicesSettings
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DevicesSettingsRepository @Inject constructor() : BehaviorDataRepository<DevicesSettings>(DataChannel.create(initialDevicesSettings)) {
    companion object {
        private val initialDevicesSettings = DevicesSettings(
            btDevicesEnabled = true,
            usbDevicesEnabled = false,
            virtualDevicesEnabled = true,
        )
    }

}
