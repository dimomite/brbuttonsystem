package org.dimomite.brbuttonsystem.presentation.connection

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import org.dimomite.brbuttonsystem.databinding.FragmentConnectionBinding

class ConnectionFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val b = FragmentConnectionBinding.inflate(inflater, container, false);
        return b.root
    }

}