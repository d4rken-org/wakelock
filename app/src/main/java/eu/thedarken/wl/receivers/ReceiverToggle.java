package eu.thedarken.wl.receivers;

import eu.thedarken.wl.MainActivity;
import eu.thedarken.wl.WakeLockService;
import eu.thedarken.wl.locks.Lock.LockType;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

public class ReceiverToggle extends BroadcastReceiver {
	private final String TAG = ReceiverToggle.class.getCanonicalName();
	public static final String TOGGLE_SERVICE = "eu.thedarken.wl.action.TOGGLE_SERVICE";

	@Override
	public void onReceive(Context context, Intent intent) {
		Log.d(TAG, "Receiving toggle...");
		Intent svc = new Intent(context, WakeLockService.class);
		if (WakeLockService.isMyServiceRunning(context)) {
			Log.d(TAG, "Service is running, stopping it...");
			context.stopService(svc);
		} else {
			Log.d(TAG, "Service is not running, starting it...");
			SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
			if (!settings.getString("current_lock", LockType.NO_LOCK.name()).equals(LockType.NO_LOCK.name())) {
				context.startService(svc);
			} else {
		        Intent i = new Intent(context, MainActivity.class);  
		        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);  
		        context.startActivity(i);
			}
		}
	}
}
