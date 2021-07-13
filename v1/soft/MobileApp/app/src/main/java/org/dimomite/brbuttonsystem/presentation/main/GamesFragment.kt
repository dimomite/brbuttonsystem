package org.dimomite.brbuttonsystem.presentation.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import org.dimomite.brbuttonsystem.databinding.FragmentGamesBinding

class GamesFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val bindings = FragmentGamesBinding.inflate(inflater, container, false)

        return bindings.root
    }
}
