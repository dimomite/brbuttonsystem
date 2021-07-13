package org.dimomite.brbuttonsystem.presentation.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import org.dimomite.brbuttonsystem.R
import org.dimomite.brbuttonsystem.databinding.FragmentStartBinding

class StartFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val bindings = FragmentStartBinding.inflate(inflater, container, false)

        val nav = findNavController()

        bindings.startDevicesBtn.setOnClickListener { nav.navigate(R.id.action_startFragment_to_devicesFragment) }
        bindings.startGamesBtn.setOnClickListener { nav.navigate(R.id.action_startFragment_to_gamesFragment) }
        bindings.startSettingsBtn.setOnClickListener { nav.navigate(R.id.action_startFragment_to_settingsFragment) }
        bindings.startAboutBtn.setOnClickListener { nav.navigate(R.id.action_startFragment_to_aboutFragment) }

        return bindings.root
    }
}