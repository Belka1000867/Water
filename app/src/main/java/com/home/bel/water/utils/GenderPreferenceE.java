package com.home.bel.water.utils;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;

import com.home.bel.water.R;

/**
 * Preference for Gender : MALE or FEMALE
 */
public class GenderPreferenceE extends TwoBtnPreference{

    public GenderPreferenceE(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public GenderPreferenceE(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public GenderPreferenceE(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GenderPreferenceE(Context context) {
        super(context);
    }

    @Override
    void init(Context context) {
        Resources resources = context.getResources();
        setValueFirst(resources.getString(R.string.preferences_gender_male));
        setValueSecond(resources.getString(R.string.preferences_gender_female));

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
