package org.dimomite.brbuttonsystem.presentation.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import org.dimomite.brbuttonsystem.R

@AndroidEntryPoint
class StartActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_start)
        setSupportActionBar(findViewById(R.id.start_nav_toolbar))
    }

}
