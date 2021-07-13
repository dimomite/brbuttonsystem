package org.dimomite.brbuttonsystem.presentation.devices

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import org.dimomite.brbuttonsystem.databinding.FragmentDevicesBinding

class DevicesFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val bindings = FragmentDevicesBinding.inflate(inflater, container, false)

        return bindings.root
    }
}