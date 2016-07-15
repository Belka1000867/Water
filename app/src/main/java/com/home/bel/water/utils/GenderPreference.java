package com.home.bel.water.utils;

import android.content.Context;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceViewHolder;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

import com.home.bel.water.R;

/**
 * Created by bel on 14.06.16.
 */
public class GenderPreference extends Preference {

    private final static String TAG = "Debug_GenderPreference";

    public final static String MALE = "Male";
    public final static String FEMALE = "Female";

    public TextView tvGenderValue;
    private String genderValue;

    public GenderPreference(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    public GenderPreference(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public GenderPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public GenderPreference(Context context) {
        super(context);
        init();
    }

    private void init(){

        Log.d(TAG, "init()");

        setPersistent(false);
        setLayoutResource(R.layout.preference_gender);
    }

    @Override
    public void onBindViewHolder(PreferenceViewHolder holder) {
        super.onBindViewHolder(holder);

        Log.d(TAG, "onBindViewHolder()");

        tvGenderValue = (TextView) holder.findViewById(R.id.tv_gender_value);
        setGenderValue(MALE);
    }

    public void changeGender(String value){
        setGenderValue(value);
    }

    public String getGenderValue() {
        return genderValue;
    }

    public void setGenderValue(String genderValue) {
        this.genderValue = genderValue;
        tvGenderValue.setText(genderValue);
    }
}
