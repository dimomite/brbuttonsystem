package org.dimomite.brbuttonsystem.presentation.connection

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.Flowable
import org.dimomite.brbuttonsystem.databinding.RemoteDeviceListItemBinding
import org.dimomite.brbuttonsystem.presentation.DisposingViewModel

class ConnectionViewModel : DisposingViewModel() {
    companion object {
        @JvmStatic
        @BindingAdapter(value = arrayOf("attachList"))
        fun attachList(v: RecyclerView, model: ConnectionViewModel) {
            val adapter = v.adapter as? Adapter ?: Adapter(v.context).also { v.adapter = it }
            model.adapter = adapter
            adapter.updateModel(model.dataModel)
        }
    }

    override fun onCleared() {
        adapter?.updateModel(null)

        super.onCleared()
    }

    val dataModel: LiveData<ConnectionUiModel> = LiveDataReactiveStreams.fromPublisher(
        Flowable.just(ConnectionUiModel(emptyArray(), false, true, false, true))
    )

    private var adapter: Adapter? = null

    fun onStartScanClicked(@Suppress("UNUSED_PARAMETER") v: View?) {}
    fun onStopScanClicked(@Suppress("UNUSED_PARAMETER") v: View?) {}
    fun onConnectClicked(@Suppress("UNUSED_PARAMETER") v: View?) {}
    fun onDisconnectClicked(@Suppress("UNUSED_PARAMETER") v: View?) {}

    //    data class RemoteDeviceViewHolder(val name: String, val version: String, val address: String, val serialNumber: String)
    private class RemoteDeviceViewHolder(val binding: RemoteDeviceListItemBinding) : RecyclerView.ViewHolder(binding.root)

    private class Adapter(private val context: Context) : RecyclerView.Adapter<RemoteDeviceViewHolder>() {
        private val inflater: LayoutInflater = LayoutInflater.from(context)
        internal fun updateModel(model: LiveData<ConnectionUiModel>?) {
            notifyDataSetChanged()
        }

        override fun getItemCount(): Int {
            return 2 // TODO hardcoded just for test
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RemoteDeviceViewHolder {
            val binding = RemoteDeviceListItemBinding.inflate(inflater, parent, false)
            return RemoteDeviceViewHolder(binding)
        }

        override fun onBindViewHolder(holder: RemoteDeviceViewHolder, position: Int) {
            holder.binding.model = RemoteDeviceUiModel("Name $position", "Serial ${100 * position}")
        }
    }

}