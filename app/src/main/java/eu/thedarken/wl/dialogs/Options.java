package eu.thedarken.wl.dialogs;

import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.preference.PreferenceManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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

        final CheckBox cbNotification = (CheckBox) findViewById(R.id.notification);
        final CheckBox cbOnboot = (CheckBox) findViewById(R.id.autostart);
        final CheckBox cbOnCall = (CheckBox) findViewById(R.id.oncall);

        cbNotification.setChecked(settings.getBoolean("notifaction.enabled", true));
        cbNotification.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                if (checked) {
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

        final PackageManager packageManager = getContext().getPackageManager();
        final ComponentName autostart = new ComponentName(getContext(), ReceiverAutostart.class);
        cbOnboot.setChecked((packageManager.getComponentEnabledSetting(autostart) == PackageManager.COMPONENT_ENABLED_STATE_ENABLED));
        cbOnboot.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                if (checked) {
                    packageManager.setComponentEnabledSetting(autostart, PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
                    Toast.makeText(getContext(), "Autostart enabled", Toast.LENGTH_SHORT).show();
                    if (cbOnCall.isChecked()) cbOnCall.setChecked(false);
                } else {
                    packageManager.setComponentEnabledSetting(autostart, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
                    Toast.makeText(getContext(), "Autostart disabled", Toast.LENGTH_SHORT).show();
                }
            }
        });

        final ComponentName oncall = new ComponentName(getContext(), ReceiverCall.class);
        cbOnCall.setChecked((packageManager.getComponentEnabledSetting(oncall) == PackageManager.COMPONENT_ENABLED_STATE_ENABLED));
        cbOnCall.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                if (checked) {
                    packageManager.setComponentEnabledSetting(oncall, PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
                    Toast.makeText(getContext(), "OnCallLocking enabled", Toast.LENGTH_SHORT).show();
                    if (cbOnboot.isChecked()) cbOnboot.setChecked(false);
                } else {
                    packageManager.setComponentEnabledSetting(oncall, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
                    Toast.makeText(getContext(), "OnCallLocking disabled", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
