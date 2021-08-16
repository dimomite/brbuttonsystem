package org.dimomite.brbuttonsystem.data.devices

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import dagger.hilt.android.qualifiers.ApplicationContext
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import org.dimomite.brbuttonsystem.data.DataContainerErrorNames
import org.dimomite.brbuttonsystem.domain.common.*
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
        devicesSettings.exec(ValueExtractingVisitorWithPreConversion(false) { it.btDevicesEnabled })
    }.distinctUntilChanged()

    private val provider = BehaviorDataProvider<DevicesList>(DataContainer.Pending(PendingProgress.ins()))
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
                provider.subj.onNext(DataContainer.Pending(PendingProgress.ins())) // notify that devices list scanning is in progress
                val devices = readListOfDevices()
                provider.subj.onNext(DataContainer.Ok(devices))
            } else {
                provider.subj.onNext(DataContainer.Error(ErrorWrap.NotAvailable(DataContainerErrorNames.BLUETOOTH_NOT_AVAILABLE)))
            }
        } else {
            provider.subj.onNext(DataContainer.Error(ErrorWrap.NoPermission(btPerm)))
        }
    }

    private fun disableBtDevices() {
        Timber.d("Disabling usage of Bluetooth devices")

        provider.subj.onNext(DataContainer.Ok(DevicesList(emptyList())))
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
