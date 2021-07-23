package org.dimomite.brbuttonsystem.presentation.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.disposables.CompositeDisposable
import org.dimomite.brbuttonsystem.databinding.FragmentSettingsBinding
import org.dimomite.brbuttonsystem.databinding.SettingsContentBinding

@AndroidEntryPoint
class SettingsFragment : Fragment() {

    private val settingsViewModel: SettingsViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val bindings = FragmentSettingsBinding.inflate(inflater, container, false)
        bindings.lifecycleOwner = this
        val rootView = bindings.root

        val settings = SettingsContentBinding.inflate(inflater, bindings.settingsScroll, true)
        settings.lifecycleOwner = this
        settings.model = settingsViewModel

        return rootView
    }

}
