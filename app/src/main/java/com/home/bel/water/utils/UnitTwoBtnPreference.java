package com.home.bel.water.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceViewHolder;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;

import com.home.bel.water.R;

/**
 * Preference for water measurement : ML or OZ
 */
public class UnitTwoBtnPreference extends TwoBtnPreference {

    public UnitTwoBtnPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void init(Context context) {

        setButtonIds(R.id.b_pref_ml, R.id.b_pref_oz);
        setValues(AppConstants.VOLUME_ML, AppConstants.VOLUME_OZ);

        setLayoutResource(R.layout.preference_volume);
    }

    @Override
    public void setPersistValue(Object value) {
        if(shouldPersist()){
            persistString((String)value);
        }
    }

    @Override
    public Object getPersistValue() {
        String defaultValue = (String) getValueFirst();
        return getPersistedString(defaultValue);
    }


}
