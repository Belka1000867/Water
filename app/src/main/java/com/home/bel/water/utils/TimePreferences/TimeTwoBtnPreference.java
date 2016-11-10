package com.home.bel.water.utils.TimePreferences;

import android.content.Context;
import android.support.v7.preference.Preference;
import android.util.AttributeSet;
import android.util.Log;

import com.home.bel.water.R;
import com.home.bel.water.utils.AppConstants;
import com.home.bel.water.utils.AppData;
import com.home.bel.water.utils.CustomDialogPreference;
import com.home.bel.water.utils.TwoBtnPreference;

/**
 * Created by bel on 30.09.16.
 */

public class TimeTwoBtnPreference extends TwoBtnPreference {

    public TimeTwoBtnPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void init(Context context) {
        setButtonIds(R.id.b_pref_time_12, R.id.b_pref_time_24);
        setValues(AppConstants.TIME_12, AppConstants.TIME_24);
        setLayoutResource(R.layout.preference_time);
    }

    @Override
    public void setPersistValue(Object value) {
        if(isPersistent()){
            persistString((String)value);
        }
    }

    @Override
    public Object getPersistValue() {
        String defaultValue = (String)getValueFirst();
        return getPersistedString(defaultValue);
    }
}
