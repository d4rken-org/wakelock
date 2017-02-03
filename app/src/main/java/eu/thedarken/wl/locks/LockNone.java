package eu.thedarken.wl.locks;

import android.util.Log;

public class LockNone implements Lock {
    private final String TAG = LockNone.class.getCanonicalName();

    @Override
    public void aquire() {
        Log.d(TAG, "LockNone acquired");
    }

    @Override
    public void release() {
        Log.d(TAG, "LockNone released");
    }

    @Override
    public String getType() {
        return "NO_LOCK";
    }

    @Override
    public String getShortType() {
        return "No Lock";
    }

    @Override
    public String getDescription() {
        return "No lock aquired";
    }

}
