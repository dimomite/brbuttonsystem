package org.dimomite.brbuttonsystem.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AppSettingsModel(
    val isNotificationControlEnabled: Boolean,
    val isFloatingControlEnabled: Boolean,
    val isWidgetControlEnabled: Boolean,

    val controlOrientation: ControlOrientation = ControlOrientation.RightHanded,
) : Parcelable

enum class ControlOrientation { LeftHanded, RightHanded }
