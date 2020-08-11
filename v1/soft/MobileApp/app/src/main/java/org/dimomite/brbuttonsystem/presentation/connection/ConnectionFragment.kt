package org.dimomite.brbuttonsystem.presentation.connection

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import org.dimomite.brbuttonsystem.databinding.FragmentConnectionBinding
import org.slf4j.LoggerFactory

class ConnectionFragment : Fragment() {
    companion object {
        val logger = LoggerFactory.getLogger(ConnectionFragment::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        logger.debug("onCreateView(): DBG")
        val b = FragmentConnectionBinding.inflate(inflater, container, false);
        b.lifecycleOwner = this
        b.model = ConnectionViewModel()
        return b.root
    }

}