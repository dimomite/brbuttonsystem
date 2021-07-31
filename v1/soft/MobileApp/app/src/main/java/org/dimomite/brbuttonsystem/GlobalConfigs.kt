package org.dimomite.brbuttonsystem

import android.os.Build

data class GlobalConfigs(
    val pictureInPictureAvailable: Boolean = Build.VERSION.SDK_INT > Build.VERSION_CODES.O,
)