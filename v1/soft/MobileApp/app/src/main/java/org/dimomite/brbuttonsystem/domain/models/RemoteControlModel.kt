package org.dimomite.brbuttonsystem.domain.models

enum class RemoteControlEvent {
    ButtonPress1,
    ButtonPress2,
    ButtonPress3,
}

data class RemoteControlModel(val event: RemoteControlEvent)
