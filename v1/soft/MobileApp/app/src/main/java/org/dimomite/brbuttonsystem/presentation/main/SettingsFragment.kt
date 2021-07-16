package org.dimomite.brbuttonsystem.presentation.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.settings_content.view.*
import org.dimomite.brbuttonsystem.R
import org.dimomite.brbuttonsystem.databinding.FragmentSettingsBinding
import org.dimomite.brbuttonsystem.databinding.SettingsContentBinding

class SettingsFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val bindings = FragmentSettingsBinding.inflate(inflater, container, false)
        bindings.lifecycleOwner = this
        val rootView = bindings.root

        val settings = SettingsContentBinding.inflate(inflater, bindings.settingsScroll, true)
        settings.lifecycleOwner = this
        settings.model = SettingsViewModel()

        return rootView
    }

}
