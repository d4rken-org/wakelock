package eu.thedarken.wl.locks;

import android.content.Context;
import android.net.wifi.WifiManager;

public class LockWifiFull implements Lock {
	private WifiManager.WifiLock wl;
	private Context mContext;

	public LockWifiFull(Context c) {
		mContext = c;
	}

	@Override
	public void aquire() {
		if (wl == null) {
			WifiManager wm = (WifiManager) mContext.getSystemService(Context.WIFI_SERVICE);
			wl = wm.createWifiLock(WifiManager.WIFI_MODE_FULL, LockWifiFull.class.getCanonicalName());
		}
		wl.acquire();
	}

	@Override
	public void release() {
		wl.release();
	}

	@Override
	public String getType() {
		return "WIFI_MODE_FULL";
	}

	@Override
	public String getShortType() {
		return "Wifi Full";
	}

	@Override
	public String getDescription() {
		return "Wi-Fi will be kept active, and will behave normally, i.e., "
				+ "it will attempt to automatically establish a connection to a remembered access point that is within range, "
				+ "and will do periodic scans if there are remembered access points but none are in range.";
	}
}
