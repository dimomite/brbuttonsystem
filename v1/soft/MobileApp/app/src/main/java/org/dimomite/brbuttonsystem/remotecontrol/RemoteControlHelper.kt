package org.dimomite.brbuttonsystem.remotecontrol

import android.content.Intent
import android.os.Bundle

interface RemoteControlApi {
    companion object {
        const val REMOTE_CONTROL_ACTION = "org.dimomite.brbuttonsystem.REMOTE_CONTROL_ACTION"

        const val FIELD_REMOTE_CONTROL_EXTRAS = "RemoteControlExtras"
        const val FIELD_REMOTE_CONTROL_ACTION = "RemoteControlAction"

        const val UPDATE_REMOTE_CONTROL_TIME_VALUE = "RemoteControlTimeValue"
        const val ACTION_REMOTE_CONTROL_START_60 = "RemoteControlActionStart60"
        const val ACTION_REMOTE_CONTROL_START_20 = "RemoteControlActionStart20"
        const val ACTION_REMOTE_CONTROL_STOP = "RemoteControlActionStop"
    }
}

class RemoteControlHelper {
    companion object {
        fun fillForStart60Action(intent: Intent) {
            intent.putExtra(RemoteControlApi.FIELD_REMOTE_CONTROL_EXTRAS, Bundle().apply {
                putString(RemoteControlApi.FIELD_REMOTE_CONTROL_ACTION, RemoteControlApi.ACTION_REMOTE_CONTROL_START_60)
            })
        }

        fun fillForStart20Action(intent: Intent) {
            intent.putExtra(RemoteControlApi.FIELD_REMOTE_CONTROL_EXTRAS, Bundle().apply {
                putString(RemoteControlApi.FIELD_REMOTE_CONTROL_ACTION, RemoteControlApi.ACTION_REMOTE_CONTROL_START_20)
            })
        }


        fun fillForStopAction(intent: Intent) {
            intent.putExtra(RemoteControlApi.FIELD_REMOTE_CONTROL_EXTRAS, Bundle().apply {
                putString(RemoteControlApi.FIELD_REMOTE_CONTROL_ACTION, RemoteControlApi.ACTION_REMOTE_CONTROL_STOP)
            })
        }

    }
}