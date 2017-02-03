package eu.thedarken.wl.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

import eu.thedarken.wl.BuildConfig;
import eu.thedarken.wl.R;
import eu.thedarken.wl.receivers.ReceiverToggle;

public class WidgetProvider extends AppWidgetProvider {
    public final String TAG = WidgetProvider.class.getCanonicalName();
    public static final String UPDATE_WIDGET = BuildConfig.APPLICATION_ID + ".widget.ACTION_UPDATE_WIDGET_FROM_SERVICE";

    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        final int N = appWidgetIds.length;

        for (int i = 0; i < N; i++) {
            int appWidgetId = appWidgetIds[i];

            Intent intent = new Intent();
            intent.setAction(ReceiverToggle.TOGGLE_SERVICE);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, appWidgetId, intent, PendingIntent.FLAG_UPDATE_CURRENT);

            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
            views.setOnClickPendingIntent(R.id.toggle, pendingIntent);

            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        final String action = intent.getAction();
        Log.d(TAG, "action:" + action);

        // handle other appwidget actions
        if (action.equals(UPDATE_WIDGET)) {
            RemoteViews control = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
            control.setTextViewText(R.id.locktype, intent.getStringExtra("locktype"));
            if (intent.getBooleanExtra("destroying", true))
                control.setInt(R.id.locktype, "setTextColor", android.graphics.Color.RED);
            else
                control.setInt(R.id.locktype, "setTextColor", android.graphics.Color.GREEN);
            ComponentName cn = new ComponentName(context, WidgetProvider.class);
            AppWidgetManager.getInstance(context).updateAppWidget(cn, control);

        }
    }
}