package org.dimomite.brbuttonsystem.data.devices

import io.reactivex.rxjava3.core.BackpressureStrategy
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.functions.BiFunction
import io.reactivex.rxjava3.functions.Function
import io.reactivex.rxjava3.subjects.BehaviorSubject
import org.dimomite.brbuttonsystem.domain.common.DataContainer
import org.dimomite.brbuttonsystem.domain.common.DataProvider
import org.dimomite.brbuttonsystem.domain.common.PendingProgress
import org.dimomite.brbuttonsystem.domain.common.extractBoolOrFalse
import org.dimomite.brbuttonsystem.domain.models.devices.DeviceConnection
import org.dimomite.brbuttonsystem.domain.models.devices.DeviceDescription
import org.dimomite.brbuttonsystem.domain.models.devices.DevicesList
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DeviceConnectionRepository @Inject constructor() {
    private val connectedDevice: BehaviorSubject<DataContainer<DeviceConnection>> = BehaviorSubject.createDefault(DataContainer.Ok(DeviceConnection.notSelected()))
    private val connectionFlow = connectedDevice.toFlowable(BackpressureStrategy.LATEST)
    private val connectionProvider = object : DataProvider<DeviceConnection> {
        override fun outFlow(): Flowable<DataContainer<DeviceConnection>> = connectionFlow
    }

    private val devicesList: BehaviorSubject<DataContainer<DevicesList>> = BehaviorSubject.createDefault(DataContainer.Pending(PendingProgress.ins()))
    private val devicesListFlow = devicesList.toFlowable(BackpressureStrategy.LATEST)
    private val devicesListProvider = object : DataProvider<DevicesList> {
        override fun outFlow(): Flowable<DataContainer<DevicesList>> = devicesListFlow
    }

    fun devices(): DataProvider<DevicesList> = devicesListProvider
    fun connection(): DataProvider<DeviceConnection> = connectionProvider


    fun connect(device: DeviceDescription) {

    }

    fun disconnect(device: DeviceConnection) {

    }

    class FieldModifier<D, V>(private val fieldExtractor: Function<D, V>, private val modCreator: BiFunction<D, V, D>) {
        fun update(newField: V, currentValue: DataContainer<D>): DataContainer<D> =
            currentValue.exec(object : DataContainer.Visitor<D, DataContainer<D>> {
                override fun visitOk(v: DataContainer.Ok<D>): DataContainer<D> {
                    val field: V = fieldExtractor.apply(v.data)
                    return if (field != currentValue) {
                        val newValue: D = modCreator.apply(v.data, newField)
                        DataContainer.Ok(newValue)
                    } else {
                        currentValue
                    }
                }

                override fun visitPending(v: DataContainer.Pending<D>): DataContainer<D> = v
                override fun visitError(v: DataContainer.Error<D>): DataContainer<D> = v
            })
    }

//    private val usbEnabledModifier = FieldModifier<DevicesList, Boolean>(
//        fieldExtractor = { it.usbDevicesReachable.exec(extractBoolOrFalse) },
//        modCreator = { dl, enableUsb -> dl.copy(usbDevicesReachable = DataContainer.Ok(enableUsb)) },
//    )
//
//    private val btEnabledModifier = FieldModifier<DevicesList, Boolean>(
//        fieldExtractor = { it.bluetoothDevicesReachable.exec(extractBoolOrFalse) },
//        modCreator = { dl, enableBt -> dl.copy(bluetoothDevicesReachable = DataContainer.Ok(enableBt)) }
//    )
//
//    fun changeBluetoothUseEnabled(btEnabled: Boolean) {
//        devicesList.onNext(btEnabledModifier.update(btEnabled, devicesList.value))
//    }
//
//    fun changeUsbUseEnabled(usbEnabled: Boolean) {
//        devicesList.onNext(usbEnabledModifier.update(usbEnabled, devicesList.value))
//    }

}
