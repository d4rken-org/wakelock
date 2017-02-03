package eu.thedarken.wl.locks;

import android.content.Context;
import android.os.PowerManager;

public class LockPartial implements Lock {
    private PowerManager.WakeLock wl;
    private Context mContext;

    public LockPartial(Context c) {
        mContext = c;
    }

    @Override
    public void aquire() {
        if (wl == null) {
            PowerManager pm = (PowerManager) mContext.getSystemService(Context.POWER_SERVICE);
            wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, LockPartial.class.getCanonicalName());
        }
        wl.acquire();
    }

    @Override
    public void release() {
        wl.release();
    }

    @Override
    public String getType() {
        return "PARTIAL_WAKE_LOCK";
    }

    @Override
    public String getShortType() {
        return "Partial";
    }

    @Override
    public String getDescription() {
        return "Ensures that the CPU is running. The screen might not be on. ";
    }

}
