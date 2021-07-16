package org.dimomite.brbuttonsystem.remotecontrol

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import org.dimomite.brbuttonsystem.R
import timber.log.Timber

class RemoteControlWidgetProvider : AppWidgetProvider() {
    override fun onUpdate(context: Context?, appWidgetManager: AppWidgetManager?, appWidgetIds: IntArray?) {
        if (context == null) return
        if (appWidgetManager == null) return
        if (appWidgetIds == null) return

        appWidgetIds.forEach {
            Timber.d("DBG: update widget with id: $it")

            val topView = RemoteViews(context.packageName, R.layout.widget_remote_control_right)

            topView.setTextViewText(R.id.remote_control_top_button, "60")
            topView.setOnClickPendingIntent(
                R.id.remote_control_top_button,
                PendingIntent.getBroadcast(context, 1, Intent(RemoteControlApi.REMOTE_CONTROL_ACTION).apply {
                    RemoteControlHelper.fillForStart60Action(this)
                }, 0)
            )

            topView.setTextViewText(R.id.remote_control_top_left_button, "20")
            topView.setOnClickPendingIntent(
                R.id.remote_control_top_left_button,
                PendingIntent.getBroadcast(context, 1, Intent(RemoteControlApi.REMOTE_CONTROL_ACTION).apply {
                    RemoteControlHelper.fillForStart20Action(this)
                }, 0)
            )

            topView.setTextViewText(R.id.remote_control_top_right_button, "Stop")
            topView.setOnClickPendingIntent(
                R.id.remote_control_top_right_button,
                PendingIntent.getBroadcast(context, 1, Intent(RemoteControlApi.REMOTE_CONTROL_ACTION).apply {
                    RemoteControlHelper.fillForStopAction(this)
                }, 0)
            )

            appWidgetManager.updateAppWidget(it, topView)
        }
    }

}
