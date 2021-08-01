package org.dimomite.brbuttonsystem

import android.os.Build

data class GlobalConfigs(
    val pictureInPictureAvailable: Boolean = Build.VERSION.SDK_INT > Build.VERSION_CODES.O,
) {
    companion object {
        const val MIN_NUMBER_OF_ACTIONS_FOR_PIP_MODE: Int = 3
    }
}