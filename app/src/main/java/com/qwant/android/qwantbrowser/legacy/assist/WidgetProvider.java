package com.qwant.android.qwantbrowser.legacy.assist;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;

import com.qwant.android.qwantbrowser.R;

import java.net.URI;

public class WidgetProvider extends AppWidgetProvider {
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.assist_initial_layout);

        final int requestCode = 0;
        final int flags = PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_UPDATE_CURRENT;

        final Intent intent = new Intent("com.qwant.android.intent.action.WIDGET");
        final PendingIntent pendingIntent = PendingIntent.getActivity(context, requestCode, intent, flags);
        views.setOnClickPendingIntent(R.id.custom_notification_widget_layout, pendingIntent);

        ComponentName watchWidget = new ComponentName(context, WidgetProvider.class);
        appWidgetManager.updateAppWidget(watchWidget, views);
    }
}