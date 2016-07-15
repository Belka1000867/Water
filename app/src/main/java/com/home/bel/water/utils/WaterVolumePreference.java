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
public class WaterVolumePreference extends TwoBtnPreference {

    public WaterVolumePreference(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public WaterVolumePreference(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public WaterVolumePreference(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public WaterVolumePreference(Context context) {
        super(context);
    }

    @Override
    void init(Context context) {
        Resources resources = context.getResources();
        setValueFirst(resources.getString(R.string.preferences_volume_ml));
        setValueSecond(resources.getString(R.string.preferences_volume_oz));

        setBtnFirstId(R.id.b_pref_ml);
        setBtnSecondId(R.id.b_pref_oz);

        setLayoutResource(R.layout.preference_volume);
    }

    @Override
    void setPersistValue(Object value) {
        if(shouldPersist()){
            persistString((String)value);
        }
    }

    @Override
    Object getPersistValue() {
        String defaultValue = (String) getValueFirst();
        return getPersistedString(defaultValue);
    }


}
