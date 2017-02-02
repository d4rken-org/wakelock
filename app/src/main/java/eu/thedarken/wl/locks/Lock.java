package eu.thedarken.wl.locks;

public interface Lock {
	enum LockType {
		NO_LOCK, WIFI_MODE_SCAN_ONLY, WIFI_MODE_FULL, WIFI_MODE_FULL_HIGH_PERF, PARTIAL_WAKE_LOCK, SCREEN_DIM_WAKE_LOCK, SCREEN_BRIGHT_WAKE_LOCK, FULL_WAKE_LOCK
	}

	public abstract void aquire();

	public abstract void release();

	public String getType();

	public String getShortType();

	public String getDescription();

}
