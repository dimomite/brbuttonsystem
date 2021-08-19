package org.dimomite.brbuttonsystem.data.devices

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import dagger.hilt.android.qualifiers.ApplicationContext
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import org.dimomite.brbuttonsystem.data.channels.BluetoothNoPermission
import org.dimomite.brbuttonsystem.data.channels.BluetoothNotActive
import org.dimomite.brbuttonsystem.domain.channels.ChannelDataHandler
import org.dimomite.brbuttonsystem.domain.channels.DataChannel
import org.dimomite.brbuttonsystem.domain.channels.UnhandledState
import org.dimomite.brbuttonsystem.domain.common.BehaviorDataProvider
import org.dimomite.brbuttonsystem.domain.common.DataProvider
import org.dimomite.brbuttonsystem.domain.common.DataRepository
import org.dimomite.brbuttonsystem.domain.models.devices.DeviceDescription
import org.dimomite.brbuttonsystem.domain.models.devices.DeviceHistory
import org.dimomite.brbuttonsystem.domain.models.devices.DevicesList
import org.dimomite.brbuttonsystem.domain.models.devices.DevicesSettings
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BluetoothDevicesRepository @Inject constructor(
    @ApplicationContext private val ctx: Context, // TODO replace with abstraction
    private val devicesSettingsRepo: DataRepository<DevicesSettings>,
) {
    companion object {
        private const val btPerm: String = Manifest.permission.BLUETOOTH
    }

    private val btEnabledFlow: Flowable<Boolean> = devicesSettingsRepo.provider().outFlow().map { devicesSettings ->
        devicesSettings.data.execR(object : ChannelDataHandler<DevicesSettings, Boolean> {
            override fun onData(data: DevicesSettings): Boolean = data.btDevicesEnabled
            override fun onNothing(): Boolean = false
        })
    }.distinctUntilChanged()

    private val provider = BehaviorDataProvider<DevicesList>(DataChannel.createPending())
    fun btDevices(): DataProvider<DevicesList> = provider

    private val subs = CompositeDisposable()

    init {
        subs.add(btEnabledFlow.subscribe({
            if (it) {
                enableBtDevices()
            } else {
                disableBtDevices()
            }
        }, {
            Timber.w("Error in bluetooth enabled data flow: $it")
        }))
    }

    fun changeBluetoothDevicesEnabled(enabled: Boolean) {
        devicesSettingsRepo.modifier().modifyData { if (it.btDevicesEnabled == enabled) it else it.copy(btDevicesEnabled = enabled) }
    }

    fun release() {
        subs.clear()
    }

    private fun enableBtDevices() {
        Timber.d("Enabling access to Bluetooth devices")
        if (hasBtPermission()) {
            if (btEnabled()) {
                provider.subj.onNext(DataChannel.createPending()) // notify that devices list scanning is in progress
                val devices = readListOfDevices()
                provider.subj.onNext(DataChannel.create(devices))
            } else {
                provider.subj.onNext(DataChannel.createError(UnhandledState.NotAvailable(BluetoothNotActive::class.java)))
            }
        } else {
            provider.subj.onNext(DataChannel.createError(UnhandledState.NotAllowed(BluetoothNoPermission::class.java)))
        }
    }

    private fun disableBtDevices() {
        Timber.d("Disabling usage of Bluetooth devices")

        provider.subj.onNext(DataChannel.create(DevicesList(emptyList())))
    }

    private fun hasBtPermission(): Boolean = ContextCompat.checkSelfPermission(ctx, btPerm) == PackageManager.PERMISSION_GRANTED // TODO wrap
    private fun btEnabled(): Boolean = BluetoothAdapter.getDefaultAdapter().isEnabled // TODO wrap // TODO should also check if BT is not available in this device

    private fun readListOfDevices(): DevicesList { // TODO wrap
        val adapter = BluetoothAdapter.getDefaultAdapter()
        val devices = adapter.bondedDevices
        val history = DeviceHistory(canAutoconnect = false)
        val asList: List<DeviceDescription> = devices
            .filter { true } // TODO filter only matching devices
            .map {
                DeviceDescription.BtDeviceDescription(
                    name = it.name,
                    history = history
                )
            }

        return DevicesList(asList)
    }

}
