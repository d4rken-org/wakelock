package eu.thedarken.wl;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import eu.thedarken.wl.dialogs.About;
import eu.thedarken.wl.dialogs.Options;
import eu.thedarken.wl.locks.LockBright;
import eu.thedarken.wl.locks.LockDim;
import eu.thedarken.wl.locks.LockFull;
import eu.thedarken.wl.locks.LockNone;
import eu.thedarken.wl.locks.LockPartial;
import eu.thedarken.wl.locks.LockWifiFull;
import eu.thedarken.wl.locks.LockWifiFullPerf;
import eu.thedarken.wl.locks.LockWifiScan;
import eu.thedarken.wl.widget.WidgetProvider;

public class MainActivity extends Activity {
    private SharedPreferences settings;
    private SharedPreferences.Editor prefEditor;
    private LockListBackend listbackend;
    private ListView list;
    private TextView locktype, description;
    private TextView level0, level1, level2, level3, level4, level5, level6, level7;
    private Intent svc;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        settings = PreferenceManager.getDefaultSharedPreferences(this);
        prefEditor = settings.edit();
        svc = new Intent(this, WakeLockService.class);
        listbackend = new LockListBackend(this, R.layout.lockline);

        list = (ListView) findViewById(R.id.locklist);
        list.setAdapter(listbackend);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                if (listbackend.isSelected(position)) {
                    if (WakeLockService.isMyServiceRunning(MainActivity.this)) {
                        listbackend.setAquired(position, false);
                        setLevel(0);
                        stopService(svc);
                    } else {
                        if (position > 0) {
                            listbackend.setAquired(position, true);
                            startService(svc);
                        }
                        setLevel(position);

                    }
                } else {
                    listbackend.setSelected(position);
                    if (WakeLockService.isMyServiceRunning(MainActivity.this))
                        stopService(svc);

                    setLevel(position);

                    if (position > 0) {
                        listbackend.setAquired(position, true);
                        startService(svc);
                    } else {
                        listbackend.setAquired(position, false);
                        Intent i = new Intent();
                        i.setAction(WidgetProvider.UPDATE_WIDGET);
                        i.putExtra("destroying", true);
                        i.putExtra("locktype", new LockNone().getShortType());
                        sendBroadcast(i);
                    }

                }
                listbackend.notifyDataSetChanged();
            }
        });
        locktype = (TextView) findViewById(R.id.locktype);
        description = (TextView) findViewById(R.id.description);

        level0 = (TextView) findViewById(R.id.level0);
        level1 = (TextView) findViewById(R.id.level1);
        level2 = (TextView) findViewById(R.id.level2);
        level3 = (TextView) findViewById(R.id.level3);
        level4 = (TextView) findViewById(R.id.level4);
        level5 = (TextView) findViewById(R.id.level5);
        level6 = (TextView) findViewById(R.id.level6);
        level7 = (TextView) findViewById(R.id.level7);

        if (WakeLockService.isMyServiceRunning(MainActivity.this)) {
            setLevel(listbackend.getSelected());
            listbackend.setAquired(listbackend.getSelected(), true);
            listbackend.notifyDataSetChanged();
        } else {
            setLevel(0);
        }

        int current_version = 0;
        try {
            current_version = getPackageManager().getPackageInfo(getPackageName(), 0).versionCode;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        if (settings.getInt("previous_version", 0) < current_version) {
            About about = new About(this);
            about.show();
            prefEditor.putInt("previous_version", current_version).commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.options:
                Options options = new Options(this);
                options.show();
                break;
            case R.id.about:
                About about = new About(this);
                about.show();
                break;
        }
        return true;
    }

    private void setLevel(int level) {
        if (level == 7) {
            int cnt = 0;
            if (!isVisible(level0)) fadeIn(level0, 150 * cnt++);
            if (!isVisible(level1)) fadeIn(level1, 150 * cnt++);
            if (!isVisible(level2)) fadeIn(level2, 150 * cnt++);
            if (!isVisible(level3)) fadeIn(level3, 150 * cnt++);
            if (!isVisible(level4)) fadeIn(level4, 150 * cnt++);
            if (!isVisible(level5)) fadeIn(level5, 150 * cnt++);
            if (!isVisible(level6)) fadeIn(level6, 150 * cnt++);
            if (!isVisible(level7)) fadeIn(level7, 150 * cnt++);
            locktype.setText(new LockFull(this).getShortType());
            description.setText(new LockFull(this).getDescription());
        } else if (level == 6) {
            int cnt = 0;
            if (isVisible(level7)) fadeOut(level7, 150 * cnt++);
            if (!isVisible(level0)) fadeIn(level0, 150 * cnt++);
            if (!isVisible(level1)) fadeIn(level1, 150 * cnt++);
            if (!isVisible(level2)) fadeIn(level2, 150 * cnt++);
            if (!isVisible(level3)) fadeIn(level3, 150 * cnt++);
            if (!isVisible(level4)) fadeIn(level4, 150 * cnt++);
            if (!isVisible(level5)) fadeIn(level5, 150 * cnt++);
            if (!isVisible(level6)) fadeIn(level6, 150 * cnt++);
            locktype.setText(new LockBright(this).getShortType());
            description.setText(new LockBright(this).getDescription());
        } else if (level == 5) {
            int cnt = 0;
            if (isVisible(level7)) fadeOut(level7, 150 * cnt++);
            if (isVisible(level6)) fadeOut(level6, 150 * cnt++);
            if (!isVisible(level0)) fadeIn(level0, 150 * cnt++);
            if (!isVisible(level1)) fadeIn(level1, 150 * cnt++);
            if (!isVisible(level2)) fadeIn(level2, 150 * cnt++);
            if (!isVisible(level3)) fadeIn(level3, 150 * cnt++);
            if (!isVisible(level4)) fadeIn(level4, 150 * cnt++);
            if (!isVisible(level5)) fadeIn(level5, 150 * cnt++);
            locktype.setText(new LockDim(this).getShortType());
            description.setText(new LockPartial(this).getDescription());
        } else if (level == 4) {
            int cnt = 0;
            if (isVisible(level7)) fadeOut(level7, 150 * cnt++);
            if (isVisible(level6)) fadeOut(level6, 150 * cnt++);
            if (isVisible(level5)) fadeOut(level5, 150 * cnt++);
            if (!isVisible(level0)) fadeIn(level0, 150 * cnt++);
            if (!isVisible(level1)) fadeIn(level1, 150 * cnt++);
            if (!isVisible(level2)) fadeIn(level2, 150 * cnt++);
            if (!isVisible(level3)) fadeIn(level3, 150 * cnt++);
            if (!isVisible(level4)) fadeIn(level4, 150 * cnt++);
            locktype.setText(new LockPartial(this).getShortType());
            description.setText(new LockPartial(this).getDescription());
        } else if (level == 3) {
            int cnt = 0;
            if (isVisible(level7)) fadeOut(level7, 150 * cnt++);
            if (isVisible(level6)) fadeOut(level6, 150 * cnt++);
            if (isVisible(level5)) fadeOut(level5, 150 * cnt++);
            if (isVisible(level4)) fadeOut(level4, 150 * cnt++);
            if (!isVisible(level0)) fadeIn(level0, 150 * cnt++);
            if (!isVisible(level1)) fadeIn(level1, 150 * cnt++);
            if (!isVisible(level2)) fadeIn(level2, 150 * cnt++);
            if (!isVisible(level3)) fadeIn(level3, 150 * cnt++);
            locktype.setText(new LockWifiFullPerf(this).getShortType());
            description.setText(new LockWifiFullPerf(this).getDescription());
        } else if (level == 2) {
            int cnt = 0;
            if (isVisible(level7)) fadeOut(level7, 150 * cnt++);
            if (isVisible(level6)) fadeOut(level6, 150 * cnt++);
            if (isVisible(level5)) fadeOut(level5, 150 * cnt++);
            if (isVisible(level4)) fadeOut(level4, 150 * cnt++);
            if (isVisible(level3)) fadeOut(level3, 150 * cnt++);
            if (!isVisible(level0)) fadeIn(level0, 150 * cnt++);
            if (!isVisible(level1)) fadeIn(level1, 150 * cnt++);
            if (!isVisible(level2)) fadeIn(level2, 150 * cnt++);
            locktype.setText(new LockWifiFull(this).getShortType());
            description.setText(new LockWifiFull(this).getDescription());
        } else if (level == 1) {
            int cnt = 0;
            if (isVisible(level7)) fadeOut(level7, 150 * cnt++);
            if (isVisible(level6)) fadeOut(level6, 150 * cnt++);
            if (isVisible(level5)) fadeOut(level5, 150 * cnt++);
            if (isVisible(level4)) fadeOut(level4, 150 * cnt++);
            if (isVisible(level3)) fadeOut(level3, 150 * cnt++);
            if (isVisible(level2)) fadeOut(level2, 150 * cnt++);
            if (!isVisible(level0)) fadeIn(level0, 150 * cnt++);
            if (!isVisible(level1)) fadeIn(level1, 150 * cnt++);
            locktype.setText(new LockWifiScan(this).getShortType());
            description.setText(new LockWifiScan(this).getDescription());
        } else if (level == 0) {
            int cnt = 0;
            if (isVisible(level7)) fadeOut(level7, 150 * cnt++);
            if (isVisible(level6)) fadeOut(level6, 150 * cnt++);
            if (isVisible(level5)) fadeOut(level5, 150 * cnt++);
            if (isVisible(level4)) fadeOut(level4, 150 * cnt++);
            if (isVisible(level3)) fadeOut(level3, 150 * cnt++);
            if (isVisible(level2)) fadeOut(level2, 150 * cnt++);
            if (isVisible(level1)) fadeOut(level1, 150 * cnt++);
            if (!isVisible(level0)) fadeIn(level0, 150 * cnt++);
            locktype.setText(new LockNone().getShortType());
            description.setText(new LockNone().getDescription());
        }

    }

    private void fadeOut(final View view, int delay) {
        Animation fadeout = new AlphaAnimation(1.f, 0.f);
        fadeout.setDuration(150);
        fadeout.setStartOffset(delay);
        view.startAnimation(fadeout);
        view.postDelayed(new Runnable() {
            @Override
            public void run() {
                view.setVisibility(View.INVISIBLE);
            }
        }, 150 + delay);
    }

    private void fadeIn(final View view, int delay) {
        Animation fadein = new AlphaAnimation(0.f, 1.f);
        fadein.setDuration(150);
        fadein.setStartOffset(delay);
        view.startAnimation(fadein);
        view.postDelayed(new Runnable() {
            @Override
            public void run() {
                view.setVisibility(View.VISIBLE);
            }
        }, 150 + delay);
    }

    private boolean isVisible(View view) {
        return view.getVisibility() == View.VISIBLE;
    }
}