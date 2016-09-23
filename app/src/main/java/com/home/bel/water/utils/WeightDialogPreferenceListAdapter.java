package com.home.bel.water.utils;

import android.content.Context;
import android.database.DataSetObserver;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.home.bel.water.R;

/**
 * List Adapter for Weight preference.
 * Changes the number of items into the list depending on the unit.
 */
public class WeightDialogPreferenceListAdapter implements ListAdapter {

    private int count = 201;
    private String value;
    private double unit;

    private final String TAG = "Debug_WeightAdapter";

    WeightDialogPreferenceListAdapter(Context context){
        AppData appData = AppData.getInstance(context);
        unit = appData.getWeightValue();

//        long valueLong = PreferenceManager.getDefaultSharedPreferences(context).getLong(AppConstants.KEY_WEIGHT, 0);
//        double valueDouble = Double.longBitsToDouble(valueLong);
//        value = String.format("%.0f", valueDouble);
    }

    @Override
    public boolean areAllItemsEnabled() {
        return true;
    }

    @Override
    public boolean isEnabled(int position) {
        return true;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public int getCount() {
        return count * (int)unit;
    }

    @Override
    public Object getItem(int position) {
        Log.d(TAG, "getItem() position = " + position);
        return null;
    }

    @Override
    public long getItemId(int position) {
        Log.d(TAG, "getItemId()");
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;

        if(convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.row_item_weight, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.tvWeight = (TextView) convertView.findViewById(R.id.tv_row_weight);
            convertView.setTag(viewHolder);
        }
        else
        {
           viewHolder = (ViewHolder) convertView.getTag();
        }

        double curPos = position + AppConstants.LIST_WEIGHT_OFFSET_KG * unit;
        String weightText = String.format("%.0f", curPos);
        viewHolder.tvWeight.setText(weightText);

        Log.d(TAG, "value = " + value );
        Log.d(TAG, "weightText = " + weightText );

//        if(value.equals(weightText)) {
//            viewHolder.tvWeight.setActivated(true);
//        }

        return convertView;
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    static class ViewHolder {
        public TextView tvWeight;
    }
}
