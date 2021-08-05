package org.dimomite.brbuttonsystem.presentation.devices

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import org.dimomite.brbuttonsystem.R

@AndroidEntryPoint
class DeviceActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_device)
    }

}
