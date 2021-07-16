package org.dimomite.brbuttonsystem.remotecontrol

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import timber.log.Timber

class RemoteControlBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (context == null) return
        if (intent == null) return

        val action = intent.action
        if (RemoteControlApi.REMOTE_CONTROL_ACTION.equals(action)) {
            handleRemoteControlRequest(intent)
        } else {
            Timber.w("Unhandled remote control action: $intent")
        }
    }

    private fun handleRemoteControlRequest(intent: Intent) {
        val extras = intent.getBundleExtra(RemoteControlApi.FIELD_REMOTE_CONTROL_EXTRAS) ?: return
        val controlAction = extras.getString(RemoteControlApi.FIELD_REMOTE_CONTROL_ACTION, "")

        Timber.d("Remote control action: \"$controlAction\"")
    }

}
