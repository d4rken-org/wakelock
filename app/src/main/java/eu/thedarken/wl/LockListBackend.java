package eu.thedarken.wl;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import eu.thedarken.wl.locks.Lock.Type;

class LockListBackend extends ArrayAdapter<Entry> {
    private final String TAG = LockListBackend.class.getCanonicalName();

    private LayoutInflater mInflater;
    private int rowlayout;

    private SharedPreferences settings;
    private SharedPreferences.Editor prefEditor;

    LockListBackend(Context c, int textViewResourceId) {
        super(c, textViewResourceId);
        rowlayout = textViewResourceId;
        mInflater = LayoutInflater.from(c);

        settings = PreferenceManager.getDefaultSharedPreferences(c);
        prefEditor = settings.edit();
        String current_lock = settings.getString("current_lock", Type.NO_LOCK.name());

        for (Type type : Type.values()) {
            add(new Entry(type.name()));
            if (type.name().equals(current_lock)) getItem(getCount() - 1).isSelected = true;
        }
        Log.d(TAG, getCount() + " locks added, " + current_lock + " was selected");

    }

    void setSelected(int position) {
        if (position < getCount()) {
            for (int i = 0; i < getCount(); i++) getItem(i).isSelected = false;
            getItem(position).isSelected = true;
            prefEditor.putString("current_lock", getItem(position).name).commit();
        }
    }

    void setAquired(int position, boolean aquired) {
        for (int i = 0; i < getCount(); i++) getItem(i).isAquired = false;
        if (aquired) getItem(position).isAquired = aquired;
    }

    boolean isSelected(int position) {
        return getItem(position).isSelected;
    }

    int getSelected() {
        for (int i = 0; i < getCount(); i++) if (getItem(i).isSelected) return i;
        return 0;
    }

    static class ViewHolder {
        TextView lock;
        TextView marker;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(rowlayout, null);
            holder = new ViewHolder();
            holder.lock = (TextView) convertView.findViewById(R.id.lock);
            holder.marker = (TextView) convertView.findViewById(R.id.marker);
            convertView.setTag(holder);
        } else holder = (ViewHolder) convertView.getTag();

        holder.lock.setText(getItem(position).name);
        if (getItem(position).isSelected) holder.marker.setVisibility(View.VISIBLE);
        else holder.marker.setVisibility(View.INVISIBLE);

        if (getItem(position).isAquired) holder.lock.setTypeface(null, Typeface.BOLD);
        else holder.lock.setTypeface(null, Typeface.NORMAL);
        return convertView;
    }

}