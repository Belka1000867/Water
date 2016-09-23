package com.home.bel.water.utils;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;

import com.home.bel.water.R;

/**
 * Preference for Gender : MALE or FEMALE
 */
public class GenderTwoBtnPreference extends TwoBtnPreference{

    public GenderTwoBtnPreference(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public GenderTwoBtnPreference(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public GenderTwoBtnPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GenderTwoBtnPreference(Context context) {
        super(context);
    }

    @Override
    void init(Context context) {
        setValueFirst(AppConstants.MALE);
        setValueSecond(AppConstants.FEMALE);

        setBtnFirstId(R.id.b_pref_male_v4);
        setBtnSecondId(R.id.b_pref_female_v4);

        setLayoutResource(R.layout.preference_gender_v4);
    }

    @Override
    void setPersistValue(Object value) {
        if(isPersistent()){
            persistString((String)value);
        }
    }

    @Override
    Object getPersistValue() {
        String defaultValue = (String)getValueFirst();
        return getPersistedString(defaultValue);
    }

}
