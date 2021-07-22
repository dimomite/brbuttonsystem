package org.dimomite.brbuttonsystem.presentation.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.disposables.CompositeDisposable
import org.dimomite.brbuttonsystem.databinding.FragmentSettingsBinding
import org.dimomite.brbuttonsystem.databinding.SettingsContentBinding
import org.dimomite.brbuttonsystem.domain.usecases.AppSettingsUseCase
import javax.inject.Inject

@AndroidEntryPoint
class SettingsFragment : Fragment() {

    @Inject
    lateinit var settingsUseCase: AppSettingsUseCase

    private var subs = CompositeDisposable()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val bindings = FragmentSettingsBinding.inflate(inflater, container, false)
        bindings.lifecycleOwner = this
        val rootView = bindings.root

        val settings = SettingsContentBinding.inflate(inflater, bindings.settingsScroll, true)
        settings.lifecycleOwner = this
        val settingsModel = SettingsViewModel()
        settings.model = settingsModel

        subs.add(settingsUseCase.outFlow().subscribe { settingsModel.updateFromModel(it) })

        return rootView
    }

    override fun onPause() {
        subs.clear()

        super.onPause()
    }

}
