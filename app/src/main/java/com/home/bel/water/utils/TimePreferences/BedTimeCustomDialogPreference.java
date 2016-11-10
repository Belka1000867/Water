package com.home.bel.water.utils.TimePreferences;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;

import com.home.bel.water.R;

/**
 * Preference for the Time when User is Going to sleep / Going to bed...
 */

public class BedTimeCustomDialogPreference extends TimeCustomDialogPreference {

    private static final String TAG = "Debug_BedTimeDiPref";

    BedTimeCustomDialogPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void init(Context context) {
        Log.d(TAG, "init: ");
        String dialogTitle = context.getResources().getString(R.string.preferences_title_bedtime);

        setTextViewIds(R.id.tv_value_bedtime, R.id.tv_clock_bedtime)
                    .setPreferenceLayout(R.layout.preference_bedtime)
                    .setDialogResources(dialogTitle, R.mipmap.ic_bedtime, 0);
    }
}
