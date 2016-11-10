package com.home.bel.water.utils.TimePreferences;

import android.content.Context;
import android.os.Build;
import android.support.v7.preference.PreferenceViewHolder;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.TimePicker;

import com.home.bel.water.utils.AppConstants;
import com.home.bel.water.utils.AppData;
import com.home.bel.water.utils.CustomDialogPreference;

/**
 * Preference that represent time.
 */

public abstract class TimeCustomDialogPreference extends CustomDialogPreference {

    private static final String TAG = "Debug_TimePreference";

    private int hour = 0;
    private int minute = 0;

    private TextView tvTimeValue;
    private TextView tvTimeClock;

    private int idTvValue;
    private int idTvClock;

    TimeCustomDialogPreference setTextViewIds(int idTvValue, int idTvClock) {
        this.idTvValue = idTvValue;
        this.idTvClock = idTvClock;
        return this;
    }

    TimeCustomDialogPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void onBindViewHolder(PreferenceViewHolder holder) {
        super.onBindViewHolder(holder);

        tvTimeValue = (TextView) holder.findViewById(idTvValue);
        tvTimeClock = (TextView) holder.findViewById(idTvClock);

        String time = getPersistedString(timeToString(hour, minute));
        refreshTextViews(getAppData().isTime24Hour(), time, tvTimeValue, tvTimeClock);
    }

    @Override
    protected void onSetInitialValue(boolean restoreValue, Object defaultValue) {
        Log.d(TAG, "onSetInitialValue() restoreValue " + restoreValue + " defaultValue " + defaultValue);
        String value;
        if (restoreValue)
        {
            value = (null == defaultValue) ? getPersistedString("00:00") : getPersistedString(defaultValue.toString());
        }
        else
        {
            value = defaultValue.toString();
        }

        hour = parseHour(value);
        minute = parseMinute(value);
    }

    private static int parseHour(String value) {
        try
        {
            String[] time = value.split(":");
            return (Integer.parseInt(time[0]));
        }
        catch (Exception e)
        {
            return 0;
        }
    }

    private static int parseMinute(String value) {
        try
        {
            String[] time = value.split(":");
            return (Integer.parseInt(time[1]));
        }
        catch (Exception e)
        {
            return 0;
        }
    }

    private static String timeToString(int h, int m) {
        return String.format("%02d", h) + ":" + String.format("%02d", m);
    }


    public static void refreshTextViews(boolean isTime24Hour, String time, TextView textViewValue, TextView textViewClock){
        textViewValue.setText(getValueText(isTime24Hour, time));
        textViewClock.setText(getClockText(isTime24Hour, time));
    }

    // Get the modified exact Time Value (03:00)
    private static String getValueText(boolean isTime24Hour, String time){
        int parsedHour = parseHour(time);
        parsedHour = !isTime24Hour && parsedHour >= 12 ? parsedHour - 12 : parsedHour;
        time = timeToString(parsedHour, parseMinute(time));
        return time;
    }

    // Get Time Clock modifier as PM or AM
    private static String getClockText(boolean isTime24Hour, String time){
        String clockString = "";
        if(!isTime24Hour) { clockString = parseHour(time) >= 12 ? AppConstants.TIME_PM : AppConstants.TIME_AM ; }
        return clockString;
    }

    private void persistStringValue(String value) {
        Log.d(TAG, "persistStringValue()");
        persistString(value);
        refreshTextViews(getAppData().isTime24Hour(), value, tvTimeValue, tvTimeClock);
    }

    public static class TimePreferenceDialogFragmentCompat extends CustomPreferenceDialogFragmentCompat {

        TimePicker timePicker = null;
        boolean is24HourView;

        @Override
        protected View onCreateDialogView(Context context) {
            Log.d(TAG, "onCreateDialogView()");
            is24HourView = AppData.getInstance(context).isTime24Hour();
            timePicker = new TimePicker(context);
            return timePicker;
        }

        @Override
        protected void onBindDialogView(View v) {
            super.onBindDialogView(v);
            Log.d(TAG, "onBindDialogView()");

            timePicker.setIs24HourView(is24HourView);

            TimeCustomDialogPreference pref = (TimeCustomDialogPreference) getPreference();

            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                timePicker.setCurrentHour(pref.hour);
                timePicker.setCurrentMinute(pref.minute);
            } else {
                timePicker.setHour(pref.hour);
                timePicker.setMinute(pref.minute);
            }
        }

        @Override
        public void onDialogClosed(boolean positiveResult) {
            Log.d(TAG, "onDialogClosed() positiveResult: " + positiveResult);

            if (!positiveResult)
                return;

            TimeCustomDialogPreference pref = (TimeCustomDialogPreference) getPreference();

            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                pref.hour = timePicker.getCurrentHour();
                pref.minute = timePicker.getCurrentMinute();
            } else {
                pref.hour = timePicker.getHour();
                pref.minute = timePicker.getMinute();
            }

            String value = TimeCustomDialogPreference.timeToString(pref.hour, pref.minute);
            if (pref.callChangeListener(value)) pref.persistStringValue(value);
        }


    }


}


