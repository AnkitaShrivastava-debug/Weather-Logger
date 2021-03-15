package com.ankita.weather.utils;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.ankita.weather.R;
import com.ankita.weather.ui.WeatherListActivity;

public class AppWidget extends AppWidgetProvider {

    private Context context;
    private AppWidgetManager appWidgetManager;
    private int[] appWidgetIds;

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        this.context = context;
        this.appWidgetManager = appWidgetManager;
        this.appWidgetIds = appWidgetIds;
        for (int appWidgetId : appWidgetIds) {
            Intent intent = new Intent(context, WeatherListActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.home_widget);
            views.setOnClickPendingIntent(R.id.img_widget, pendingIntent);
            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
    }

}
