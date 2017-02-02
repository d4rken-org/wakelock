package eu.thedarken.wl.locks;

import android.content.Context;
import android.net.wifi.WifiManager;

public class LockWifiFullPerf implements Lock {

	private WifiManager.WifiLock wl;
	private Context mContext;

	public LockWifiFullPerf(Context c) {
		mContext = c;
	}

	@Override
	public void aquire() {
		if (wl == null) {
			WifiManager wm = (WifiManager) mContext.getSystemService(Context.WIFI_SERVICE);
			wl = wm.createWifiLock(3, LockWifiFullPerf.class.getCanonicalName());
		}
		wl.acquire();
	}

	@Override
	public void release() {
		wl.release();
	}

	@Override
	public String getType() {
		return "WIFI_MODE_FULL_HIGH_PERF";
	}

	@Override
	public String getShortType() {
		return "Wifi FullPerf";
	}

	@Override
	public String getDescription() {
		return "Wi-Fi will be kept active as in mode WIFI_MODE_FULL but it"
				+ " operates at high performance with minimum packet loss and low packet latency even when the device screen is off.";
	}

}
