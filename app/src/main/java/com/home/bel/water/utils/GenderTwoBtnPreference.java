package com.home.bel.water.utils;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;

import com.home.bel.water.R;

/**
 * Preference for Gender : MALE or FEMALE
 */
public class GenderTwoBtnPreference extends TwoBtnPreference{

    public GenderTwoBtnPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void init(Context context) {

        setButtonIds(R.id.b_pref_male_v4, R.id.b_pref_female_v4);
        setValues(AppConstants.MALE, AppConstants.FEMALE);

        setLayoutResource(R.layout.preference_gender_v4);
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
