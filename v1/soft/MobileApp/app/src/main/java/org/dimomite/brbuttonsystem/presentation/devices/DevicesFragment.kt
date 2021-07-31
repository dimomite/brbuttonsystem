package org.dimomite.brbuttonsystem.presentation.devices

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import org.dimomite.brbuttonsystem.R
import org.dimomite.brbuttonsystem.databinding.FragmentDevicesBinding
import org.dimomite.brbuttonsystem.databinding.RemoteDeviceListItemBinding
import org.dimomite.brbuttonsystem.presentation.connection.RemoteDeviceUiModel
import timber.log.Timber

class DevicesFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val bindings = FragmentDevicesBinding.inflate(inflater, container, false)
        val adapter = DevicesAdapter(inflater)
        bindings.devicesList.adapter = adapter

        return bindings.root
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



