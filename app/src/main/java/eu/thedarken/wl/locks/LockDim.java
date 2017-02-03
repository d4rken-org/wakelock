package eu.thedarken.wl.locks;

import android.content.Context;
import android.os.PowerManager;

public class LockDim implements Lock {

    private PowerManager.WakeLock wl;
    private Context mContext;

    public LockDim(Context c) {
        mContext = c;
    }

    @Override
    public void aquire() {
        if (wl == null) {
            PowerManager pm = (PowerManager) mContext.getSystemService(Context.POWER_SERVICE);
            wl = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, LockDim.class.getCanonicalName());
        }
        wl.acquire();
    }

    @Override
    public void release() {
        wl.release();
    }

    @Override
    public String getType() {
        return "SCREEN_DIM_WAKE_LOCK";
    }

    @Override
    public String getShortType() {
        return "Screen Dim";
    }

    @Override
    public String getDescription() {
        return "Ensures that the CPU is on and the screen is on (but may be dimmed), the keyboard backlight will be allowed to go off.";
    }

}
