package com.qwant.android.qwantbrowser.legacy.assist;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.qwant.android.qwantbrowser.R;

public class WidgetProvider extends AppWidgetProvider {
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.assist_initial_layout);

        final Intent intent = new Intent(context, Assist.class);
        final PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_UPDATE_CURRENT);
        views.setOnClickPendingIntent(R.id.custom_notification_widget_layout, pendingIntent);

        ComponentName watchWidget = new ComponentName(context, WidgetProvider.class);
        appWidgetManager.updateAppWidget(watchWidget, views);
    }
}