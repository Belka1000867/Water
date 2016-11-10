package com.home.bel.water.utils.TimePreferences;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;

import com.home.bel.water.R;

/**
 * Created by bel on 02.10.16.
 */

public class WakeTimeCustomDialogPreference extends TimeCustomDialogPreference {

    private static final String TAG = "Debug_WakeTimeDiPref";
    
    public WakeTimeCustomDialogPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void init(Context context) {
        Log.d(TAG, "init: ");
        String dialogTitle = context.getResources().getString(R.string.preferences_title_wake);

        setTextViewIds(R.id.tv_value_wake, R.id.tv_clock_wake)
                .setPreferenceLayout(R.layout.preference_waketime)
                .setDialogResources(dialogTitle, R.mipmap.ic_bedtime, 0);
    }

}
