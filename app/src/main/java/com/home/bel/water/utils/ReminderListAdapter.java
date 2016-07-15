package com.home.bel.water.utils;

import android.content.Context;
import android.database.DataSetObservable;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.home.bel.water.R;

/**
 * Created by bel on 07.06.16.
 */
public class ReminderListAdapter implements ListAdapter {

    private DataSetObservable mDataSetObservable;
    private int count;
    private int[] times;
    private int start, end, breakfast, lunch, dinner;

    final static String TAG = "Debug_StatListAdapter";

    public ReminderListAdapter(int[] times, Bundle events){
        this.times = times;
        count = times.length;

        start = events.getInt(ScheduleConstants.GET_UP, 0);
        end = events.getInt(ScheduleConstants.GO_TO_SLEEP);
        breakfast = events.getInt(ScheduleConstants.BREAKFAST);
        lunch = events.getInt(ScheduleConstants.LUNCH);
        dinner = events.getInt(ScheduleConstants.DINNER);


        mDataSetObservable = new DataSetObservable();
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEnabled(int position) {
      //  Log.d(TAG, "Position clicked = " + position);
        return true;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {
        mDataSetObservable.registerObserver(observer);
    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {
        mDataSetObservable.unregisterObserver(observer);
    }

    @Override
    public int getCount() {
        return count;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.d(TAG, "getView(), position = " + position);
        ViewHolder viewHolder = new ViewHolder();

        if(convertView == null) {
            LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.row_item_statistics, parent, false);

            viewHolder.rlRow = (RelativeLayout) convertView.findViewById(R.id.layout_row_stats);
            viewHolder.tvTime = (TextView) convertView.findViewById(R.id.tv_stats_time);
            viewHolder.ivDescription = (ImageView) convertView.findViewById(R.id.iv_stats_description);
            viewHolder.ivIsWaterNeed = (ImageView) convertView.findViewById(R.id.iv_stats_water_need);
            viewHolder.ivFaq = (ImageView) convertView.findViewById(R.id.iv_stats_faq);
            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        int minuteAmount = times[position];
        int hour = minuteAmount / 60;
        int minutes = minuteAmount % 60;

        final String minuteString = minutes == 0 ? "00" : String.valueOf(minutes);

        String time = hour + ":" + minuteString;

        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParams.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);

        setDescriptionImage(position, viewHolder.ivDescription);
        viewHolder.tvTime.setLayoutParams(layoutParams);
        viewHolder.tvTime.setText(time);
        viewHolder.tvTime.setGravity(Gravity.CENTER);

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

    static class ViewHolder{
        public RelativeLayout rlRow;
        public TextView tvTime;
        public ImageView ivDescription;
        public ImageView ivIsWaterNeed;
        public ImageView ivFaq;
    }

    /* Finish of List Adapter functions */

    private void setDescriptionImage(int position, ImageView imageView){

        if(position == start){
            imageView.setImageResource(R.mipmap.ic_reminders);
        }
        else if(position == end){
            imageView.setImageResource(R.mipmap.ic_reminders);
        }
        else if(position == breakfast){
            imageView.setImageResource(R.mipmap.ic_reminders);
        }
        else if(position == lunch){
            imageView.setImageResource(R.mipmap.ic_reminders);
        }
        else if(position == dinner){
            imageView.setImageResource(R.mipmap.ic_reminders);
        }

    }
}
