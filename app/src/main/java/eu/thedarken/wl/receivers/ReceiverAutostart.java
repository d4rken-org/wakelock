package eu.thedarken.wl.receivers;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.preference.PreferenceManager;
import android.util.Log;

import eu.thedarken.wl.WakeLockService;
import eu.thedarken.wl.locks.Lock.Type;

public class ReceiverAutostart extends BroadcastReceiver {
    private final String TAG = ReceiverAutostart.class.getCanonicalName();

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "Wakelock autostart called");
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        if (!settings.getString("current_lock", Type.NO_LOCK.name()).equals(Type.NO_LOCK.name())) {
            ComponentName oncall = new ComponentName(context, ReceiverCall.class);
            PackageManager packageManager = context.getPackageManager();
            if (!(packageManager.getComponentEnabledSetting(oncall) == PackageManager.COMPONENT_ENABLED_STATE_ENABLED)) {
                Intent svc = new Intent(context, WakeLockService.class);
                context.startService(svc);
            }
        }
    }
}
