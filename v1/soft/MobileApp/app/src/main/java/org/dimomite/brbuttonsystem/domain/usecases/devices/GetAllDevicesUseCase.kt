package org.dimomite.brbuttonsystem.domain.usecases.devices

import org.dimomite.brbuttonsystem.data.devices.BluetoothDevicesRepository
import org.dimomite.brbuttonsystem.domain.common.SubUseCase
import org.dimomite.brbuttonsystem.domain.common.SubscriptionUseCaseParams
import org.dimomite.brbuttonsystem.domain.models.devices.DevicesList
import javax.inject.Inject

class GetAllDevicesUseCase @Inject constructor(
    btDevicesRepo: BluetoothDevicesRepository,
    params: SubscriptionUseCaseParams,
) : SubUseCase<DevicesList>(btDevicesRepo.btDevices().outFlow(), params) {
}