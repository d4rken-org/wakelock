package eu.thedarken.wl.locks;

import android.content.Context;
import android.os.PowerManager;

public class LockBright implements Lock {

    private PowerManager.WakeLock wl;
    private Context mContext;

    public LockBright(Context c) {
        mContext = c;
    }

    @Override
    public void aquire() {
        if (wl == null) {
            PowerManager pm = (PowerManager) mContext.getSystemService(Context.POWER_SERVICE);
            wl = pm.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK, LockBright.class.getCanonicalName());
        }
        wl.acquire();
    }

    @Override
    public void release() {
        wl.release();
    }

    @Override
    public String getType() {
        return "SCREEN_BRIGHT_WAKE_LOCK";
    }

    @Override
    public String getShortType() {
        return "Screen Bright";
    }

    @Override
    public String getDescription() {
        return "Ensures that the CPU is on and the screen on and at full current brightness, the keyboard backlight will be allowed to go off.";
    }


}
