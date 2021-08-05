package org.dimomite.brbuttonsystem.presentation.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import org.dimomite.brbuttonsystem.R
import org.dimomite.brbuttonsystem.databinding.FragmentGamesBinding

class GamesFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val bindings = FragmentGamesBinding.inflate(inflater, container, false)
        bindings.gamesDbgToGameActivityBtn.setOnClickListener { findNavController().navigate(R.id.action_gamesFragment_to_gamesActivity) }

        return bindings.root
    }

}
