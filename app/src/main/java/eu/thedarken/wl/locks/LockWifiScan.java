package eu.thedarken.wl.locks;

import android.content.Context;
import android.net.wifi.WifiManager;

public class LockWifiScan implements Lock {

	private WifiManager.WifiLock wl;
	private Context mContext;

	public LockWifiScan(Context c) {
		mContext = c;
	}

	@Override
	public void aquire() {
		if (wl == null) {
			WifiManager wm = (WifiManager) mContext.getSystemService(Context.WIFI_SERVICE);
			wl = wm.createWifiLock(WifiManager.WIFI_MODE_SCAN_ONLY, LockWifiScan.class.getCanonicalName());
		}
		wl.acquire();
	}

	@Override
	public void release() {
		wl.release();
	}

	@Override
	public String getType() {
		return "WIFI_MODE_SCAN_ONLY";
	}

	@Override
	public String getShortType() {
		return "Wifi Scan";
	}

	@Override
	public String getDescription() {
		return "Wi-Fi will be kept active, but the only operation that will be supported"
				+ " is initiation of scans, and the subsequent reporting of scan results."
				+ " No attempts will be made to automatically connect to remembered access points,"
				+ " nor will periodic scans be automatically performed looking for remembered access points."
				+ " Scans must be explicitly requested by an application in this mode.";
	}
}
