package org.dimomite.brbuttonsystem.remotecontrol

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import org.dimomite.brbuttonsystem.R
import timber.log.Timber

class RemoteControlWidgetProvider : AppWidgetProvider() {
    companion object {
        private const val EMPTY_TEXT = "--"

        const val ACTION_1 = "org.dimomite.brbuttonsystem.action.ActionRemoteControl1"
        const val ACTION_2 = "org.dimomite.brbuttonsystem.action.ActionRemoteControl2"
        const val ACTION_3 = "org.dimomite.brbuttonsystem.action.ActionRemoteControl3"

        fun buildIntent(ctx: Context, action: String) = Intent(ctx, RemoteControlWidgetProvider::class.java).setAction(action)

        // TODO move creation and parsing of remote control intents to central place
        fun createActionPendingIntent(ctx: Context, action: String): PendingIntent = PendingIntent.getBroadcast(ctx, 1, buildIntent(ctx, action), 0)
    }

    override fun onUpdate(context: Context?, appWidgetManager: AppWidgetManager?, appWidgetIds: IntArray?) {
        if (context == null) return
        if (appWidgetManager == null) return
        if (appWidgetIds == null) return

        Timber.d("onUpdate(): update widget with id: ${appWidgetIds.contentToString()}")
        val views = buildWidgetView(context)
        appWidgetManager.updateAppWidget(ComponentName(context, RemoteControlWidgetProvider::class.java.name), views)
    }

    private fun buildWidgetView(context: Context, text: String = EMPTY_TEXT): RemoteViews {
        val topView = RemoteViews(context.packageName, R.layout.widget_remote_control_right)

        topView.setTextViewText(R.id.remote_control_tv, text)

        topView.setTextViewText(R.id.remote_control_top_button, "Stop")
        topView.setOnClickPendingIntent(
            R.id.remote_control_top_button,
            createActionPendingIntent(context, ACTION_1)
        )

        topView.setTextViewText(R.id.remote_control_bot_left_button, "20")
        topView.setOnClickPendingIntent(
            R.id.remote_control_bot_left_button,
            createActionPendingIntent(context, ACTION_2)
        )

        topView.setTextViewText(R.id.remote_control_bot_right_button, "60")
        topView.setOnClickPendingIntent(
            R.id.remote_control_bot_right_button,
            createActionPendingIntent(context, ACTION_3)
        )

        return topView
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        val action = intent?.action ?: ""
        if (ACTION_1.equals(action) || ACTION_2.equals(action) || ACTION_3.equals(action)) {
            if (context != null && intent != null) {
                val text = actionToButtonText(action)
                Timber.d("onReceive(): remote action: \"$action\", button text: \"$text\"")
                val views = buildWidgetView(context, text)
                val mgr = AppWidgetManager.getInstance(context)
                mgr.updateAppWidget(ComponentName(context, RemoteControlWidgetProvider::class.java.name), views)
            }
        } else {
            super.onReceive(context, intent)
        }
    }

    private fun actionToButtonText(action: String): String = when (action) {
        ACTION_1 -> "Stop"
        ACTION_2 -> "20"
        ACTION_3 -> "60"
        else -> EMPTY_TEXT
    }

}
