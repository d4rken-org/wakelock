package eu.thedarken.wl.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import eu.thedarken.wl.R;

public class About extends Dialog {

	public About(Context context) {
		super(context);

		setContentView(R.layout.dialog_about);
		String versName = "";
		try {
			versName = getContext().getPackageManager().getPackageInfo(getContext().getPackageName(), 0).versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		setTitle("WakeLock " + versName);
		setCancelable(true);

		TextView about = (TextView) findViewById(R.id.about);
		about.setText("by darken\n\n" + "This app allows you to aquire Wifi- & WakeLocks from Androids Wifi- & PowerManager\n\n"
				+ "This app is free of charge and without ads, if it helped and you would have paid for it, there is 'Donate Version' you can buy on the market.\nThere is no difference to this one.\n\n" + "Reach me under support@thedarken.eu");
	
		Button donate = (Button) findViewById(R.id.donate);
		donate.setOnClickListener(new android.view.View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent marketIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=eu.thedarken.wldonate"));
				getContext().startActivity(marketIntent);
			}
		});
	}

}
