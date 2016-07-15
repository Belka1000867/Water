package com.home.bel.water.utils;


import android.content.Context;
import android.graphics.Color;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceViewHolder;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

import com.home.bel.water.R;

/**
 * Created by bel on 15.06.16.
 */
public class GenderPreferenceB extends Preference {

    TextView tvGenderMale;
    TextView tvGenderFemale;

    public final static String MALE = "Male";
    public final static String FEMALE = "Female";

    public String genderValue;

    public GenderPreferenceB(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    public GenderPreferenceB(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public GenderPreferenceB(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public GenderPreferenceB(Context context) {
        super(context);
        init();
    }

    private void init(){
        setPersistent(false);
        setLayoutResource(R.layout.preference_gender_v2);
    }

    @Override
    public void onBindViewHolder(PreferenceViewHolder holder) {
        super.onBindViewHolder(holder);

        tvGenderMale = (TextView) holder.findViewById(R.id.tv_gender_male_v2);
        tvGenderFemale = (TextView) holder.findViewById(R.id.tv_gender_female_v2);
        setGenderValue(MALE);
    }

    public void changeGender(){
        Log.d("Debug", "ChangeFocus : " + genderValue);
        setGenderValue(genderValue.equals(MALE) ? FEMALE : MALE);
    }

    public void setGenderValue(String genderValue) {
        this.genderValue = genderValue;
        changeFocus();
    }

    public void changeFocus(){
        Log.d("Debug", "ChangeFocus : " + genderValue);
        if(genderValue.equals(MALE)) {
            tvGenderMale.setBackgroundColor(Color.rgb(121,218,255));
            tvGenderFemale.setBackgroundResource(0);
        }
        else
        {
            tvGenderMale.setBackgroundResource(0);
            tvGenderFemale.setBackgroundColor(Color.rgb(121,218,255));
        }
    }


}
