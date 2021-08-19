package org.dimomite.brbuttonsystem.presentation.devices

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.disposables.CompositeDisposable
import org.dimomite.brbuttonsystem.R
import org.dimomite.brbuttonsystem.databinding.FragmentDevicesBinding
import org.dimomite.brbuttonsystem.databinding.RemoteDeviceListItemBinding
import org.dimomite.brbuttonsystem.domain.channels.ChannelDataHandler
import org.dimomite.brbuttonsystem.domain.models.devices.DevicesList
import org.dimomite.brbuttonsystem.domain.usecases.devices.GetAllDevicesUseCase
import org.dimomite.brbuttonsystem.presentation.connection.RemoteDeviceUiModel
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class DevicesFragment : Fragment() {

    @Inject
    lateinit var getAllDevicesUseCase: GetAllDevicesUseCase // FIXME move to view model
    private val subs = CompositeDisposable()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val bindings = FragmentDevicesBinding.inflate(inflater, container, false)
        val adapter = DevicesAdapter(inflater)
        bindings.devicesList.adapter = adapter

        return bindings.root
    }

    override fun onStart() {
        super.onStart()

        subs.add(getAllDevicesUseCase.outFlow().subscribe({
            val result: String = it.execOnData(object : ChannelDataHandler<DevicesList, String> {
                override fun onData(data: DevicesList): String = data.toString()
                override fun onNothing(): String = "<no data>"
            })
            Timber.d("DBG: all devices: $result")
        }, {
            Timber.w("DBG: could not get all devices data flow")
        }))
    }

    override fun onStop() {
        subs.clear()

        super.onStop()
    }

    private data class DevicesViewHolder(val bindings: RemoteDeviceListItemBinding) : RecyclerView.ViewHolder(bindings.root)

    private inner class DevicesAdapter(private val inflater: LayoutInflater) : RecyclerView.Adapter<DevicesViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DevicesViewHolder {
            val bindings = RemoteDeviceListItemBinding.inflate(inflater, parent, false)
            bindings.root.setOnClickListener {
                Timber.i("DBG: Navigate to device activity")
                findNavController().navigate(R.id.action_devicesFragment_to_deviceActivity)
            }
            return DevicesViewHolder(bindings)
        }

        override fun onBindViewHolder(holder: DevicesViewHolder, position: Int) {
            holder.bindings.model = RemoteDeviceUiModel("Device #$position", "SN: #1234")
        }

        override fun getItemCount(): Int {
            return 2
        }
    }


}



