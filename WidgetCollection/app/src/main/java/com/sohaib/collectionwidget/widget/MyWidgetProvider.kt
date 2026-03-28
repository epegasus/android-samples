package com.sohaib.collectionwidget.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import androidx.core.net.toUri
import com.sohaib.collectionwidget.MainActivity
import com.sohaib.collectionwidget.R

const val ACTION_TOAST = "dev.pegasus.collectionwidget.widget.ACTION_TOAST"
const val EXTRA_ITEM = "extra_item"
const val TAG = "TAG_MyTag"

class MyWidgetProvider : AppWidgetProvider() {

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == ACTION_TOAST) {
            val url = intent.getStringExtra(EXTRA_ITEM) ?: "https://tiktok.com"
            val browsingIntent = Intent(context, MainActivity::class.java)
            browsingIntent.putExtra(EXTRA_ITEM, url)
            browsingIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(browsingIntent)
        }
        super.onReceive(context, intent)
    }

    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        appWidgetIds.forEach { appWidgetId ->
            val remoteViews = RemoteViews(context.packageName, R.layout.widget_layout).apply {
                val intent = Intent(context, MyWidgetService::class.java).apply {
                    putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
                    data = toUri(Intent.URI_INTENT_SCHEME).toUri()
                }
                setRemoteAdapter(R.id.list_view, intent)
                setEmptyView(R.id.list_view, R.id.empty_view)
            }

            remoteViews.setOnClickPendingIntent(R.id.mtvTitleWidget, getPendingIntentApp(context))
            remoteViews.setPendingIntentTemplate(R.id.list_view, getPendingIntentItem(context, appWidgetId))

            appWidgetManager.updateAppWidget(appWidgetId, remoteViews)
        }
        super.onUpdate(context, appWidgetManager, appWidgetIds)
    }

    private fun getPendingIntentApp(context: Context): PendingIntent {
        val intent = Intent(context, MainActivity::class.java)

        val flags = PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        return PendingIntent.getActivity(context, 0, intent, flags)
    }

    private fun getPendingIntentItem(context: Context, appWidgetId: Int): PendingIntent {
        val intent = Intent(context, MyWidgetProvider::class.java).apply {
            action = ACTION_TOAST
            putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
            data = toUri(Intent.URI_INTENT_SCHEME).toUri()
        }

        val flags = PendingIntent.FLAG_MUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        return PendingIntent.getBroadcast(context, 1, intent, flags)
    }
}