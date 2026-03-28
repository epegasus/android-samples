package com.sohaib.collectionwidget.widget

import android.appwidget.AppWidgetManager
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import com.sohaib.collectionwidget.R
import com.sohaib.collectionwidget.retrofit.Product
import com.sohaib.collectionwidget.retrofit.RetrofitClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class MyWidgetService : RemoteViewsService() {

    override fun onGetViewFactory(intent: Intent): RemoteViewsFactory {
        return MyRemoteViewsFactory(this.applicationContext, intent)
    }
}

class MyRemoteViewsFactory(private val context: Context, intent: Intent) : RemoteViewsService.RemoteViewsFactory {

    private val appWidgetId: Int = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID)

    private val productList = arrayListOf<Product>()

    override fun onCreate() = createList()

    override fun onDataSetChanged() {}

    override fun onDestroy() {}

    private fun createList() {
        Log.d(TAG, "createList: called")
        CoroutineScope(Dispatchers.Main).launch {
            val list = CoroutineScope(Dispatchers.IO).async {
                RetrofitClient.api.getProducts().execute().body()?.products ?: emptyList()
            }.await()
            productList.clear()
            productList.addAll(list)

            Log.d(TAG, "createList: created: ${productList.size}")
            // Notify dataset changed
            AppWidgetManager.getInstance(context).notifyAppWidgetViewDataChanged(appWidgetId, R.id.list_view)
        }
    }

    override fun getCount(): Int {
        return productList.size
    }

    override fun getViewAt(position: Int): RemoteViews {
        val item = productList[position]
        val fillInIntent = Intent().apply {
            putExtra(EXTRA_ITEM, item.thumbnail)
        }

        Log.d(TAG, "getViewAt: $position) $item")
        return RemoteViews(context.packageName, R.layout.widget_item).apply {
            setTextViewText(R.id.widget_item, item.title)
            setOnClickFillInIntent(R.id.widget_item, fillInIntent)
        }
    }

    override fun getLoadingView(): RemoteViews? = null

    override fun getViewTypeCount(): Int {
        return 1
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun hasStableIds(): Boolean {
        return false
    }
}