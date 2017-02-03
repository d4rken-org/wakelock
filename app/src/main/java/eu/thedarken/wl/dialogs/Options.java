package eu.thedarken.wl.dialogs;

import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

import eu.thedarken.wl.R;
import eu.thedarken.wl.receivers.ReceiverAutostart;
import eu.thedarken.wl.receivers.ReceiverCall;

public class Options extends Dialog {
    private SharedPreferences settings;
    private SharedPreferences.Editor prefEditor;

    public Options(Context context) {
        super(context);

        setContentView(R.layout.dialog_options);
        setTitle("Options");
        setCancelable(true);
        setCanceledOnTouchOutside(true);

        settings = PreferenceManager.getDefaultSharedPreferences(getContext());
        prefEditor = settings.edit();

        CheckBox cbNotification = (CheckBox) findViewById(R.id.notification);
        cbNotification.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((CheckBox) v).isChecked()) {
                    prefEditor.putBoolean("notifaction.enabled", true);
                    prefEditor.commit();
                    Toast.makeText(getContext(), "Notification enabled", Toast.LENGTH_SHORT).show();
                } else {
                    prefEditor.putBoolean("notifaction.enabled", false);
                    prefEditor.commit();
                    Toast.makeText(getContext(), "Notification disabled", Toast.LENGTH_SHORT).show();
                }
            }
        });
        cbNotification.setChecked(settings.getBoolean("notifaction.enabled", true));

        final PackageManager packageManager = getContext().getPackageManager();

        final ComponentName autostart = new ComponentName(getContext(), ReceiverAutostart.class);
        CheckBox cbOnboot = (CheckBox) findViewById(R.id.autostart);
        cbOnboot.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (((CheckBox) v).isChecked()) {
                    packageManager.setComponentEnabledSetting(autostart, PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
                    Toast.makeText(getContext(), "Autostart enabled", Toast.LENGTH_SHORT).show();
                } else {
                    packageManager.setComponentEnabledSetting(autostart, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
                    Toast.makeText(getContext(), "Autostart disabled", Toast.LENGTH_SHORT).show();
                }
            }
        });
        cbOnboot.setChecked((packageManager.getComponentEnabledSetting(autostart) == PackageManager.COMPONENT_ENABLED_STATE_ENABLED));

        final ComponentName oncall = new ComponentName(getContext(), ReceiverCall.class);
        CheckBox cbOnCall = (CheckBox) findViewById(R.id.oncall);
        cbOnCall.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (((CheckBox) v).isChecked()) {
                    packageManager.setComponentEnabledSetting(oncall, PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
                    Toast.makeText(getContext(), "OnCallLocking enabled", Toast.LENGTH_SHORT).show();
                } else {
                    packageManager.setComponentEnabledSetting(oncall, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
                    Toast.makeText(getContext(), "OnCallLocking disabled", Toast.LENGTH_SHORT).show();
                }
            }
        });
        cbOnCall.setChecked((packageManager.getComponentEnabledSetting(oncall) == PackageManager.COMPONENT_ENABLED_STATE_ENABLED));
    }

}
