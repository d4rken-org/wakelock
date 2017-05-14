package eu.thedarken.wl;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

class LockListBackend extends BaseAdapter {
    private final List<Entry> entries;

    private SharedPreferences settings;

    LockListBackend(Context c, List<Entry> entries) {
        this.entries = entries;
        settings = PreferenceManager.getDefaultSharedPreferences(c);
    }

    void setSelected(int position) {
        if (position < getCount()) {
            for (int i = 0; i < getCount(); i++) getItem(i).isSelected = false;
            getItem(position).isSelected = true;
            settings.edit().putString("current_lock", getItem(position).name).commit();
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

    @Override
    public int getCount() {
        return entries.size();
    }

    @Override
    public Entry getItem(int i) {
        return entries.get(i);
    }

    @Override
    public long getItemId(int i) {
        return entries.get(i).hashCode();
    }

    static class ViewHolder {
        TextView lock;
        TextView marker;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_lockline, null);
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