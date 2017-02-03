package eu.thedarken.wl.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import eu.thedarken.wl.BuildConfig;
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
        TextView version = (TextView) findViewById(R.id.version);
        version.setText(context.getString(R.string.version_x, versName));

        setTitle(R.string.app_name);
        setCancelable(true);
        setCanceledOnTouchOutside(true);

        TextView about = (TextView) findViewById(R.id.about);
        about.setText(R.string.about_text);

        Button donate = (Button) findViewById(R.id.donate);
        donate.setVisibility(BuildConfig.FLAVOR.equals("free") ? View.VISIBLE : View.GONE);
        donate.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent marketIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=eu.thedarken.wldonate"));
                getContext().startActivity(marketIntent);
            }
        });

        Button home = (Button) findViewById(R.id.home);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent marketIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://darken.eu"));
                getContext().startActivity(marketIntent);
            }
        });
    }

}
