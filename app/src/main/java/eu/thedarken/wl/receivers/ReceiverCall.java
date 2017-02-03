package eu.thedarken.wl.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.telephony.TelephonyManager;
import android.util.Log;

import eu.thedarken.wl.WakeLockService;
import eu.thedarken.wl.locks.Lock.Type;

public class ReceiverCall extends BroadcastReceiver {
    private final String TAG = ReceiverCall.class.getCanonicalName();

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "Receiving call...");
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        Intent svc = new Intent(context, WakeLockService.class);

        String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);

        if (TelephonyManager.EXTRA_STATE_RINGING.equals(state) || TelephonyManager.EXTRA_STATE_OFFHOOK.equals(state)) {
            Log.d(TAG, "Service is not running, call incoming, starting it...");
            if (!settings.getString("current_lock", Type.NO_LOCK.name()).equals(Type.NO_LOCK.name()))
                context.startService(svc);
            else
                Log.w(TAG, "No lock was selected!");
        } else if (TelephonyManager.EXTRA_STATE_IDLE.equals(state)) {
            Log.d(TAG, "Service is running, call ended, stopping it...");
            context.stopService(svc);
        }
    }
}
