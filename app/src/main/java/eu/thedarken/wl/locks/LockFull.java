package eu.thedarken.wl.locks;

import android.content.Context;
import android.os.PowerManager;

public class LockFull implements Lock {

	private PowerManager.WakeLock wl;
	private Context mContext;
	public LockFull(Context c) {
		mContext = c;

	}

	@Override
	public void aquire() {
		if(wl == null) {
			PowerManager pm = (PowerManager) mContext.getSystemService(Context.POWER_SERVICE);
			wl = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK, LockFull.class.getCanonicalName());
		}
		wl.acquire();
	}

	@Override
	public void release() {
		wl.release();
	}

	@Override
	public String getType() {
		return "FULL_WAKE_LOCK";
	}

	@Override
	public String getShortType() {
		return "Full Lock";
	}

	@Override
	public String getDescription() {
		return "Ensures that the CPU is on, aswell as screen and keyboard are on at full brightness.";
	}
	
	

}
