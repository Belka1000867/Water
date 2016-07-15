package com.home.bel.water.utils;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.preference.DialogPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceDialogFragmentCompat;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.PreferenceViewHolder;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.home.bel.water.R;

/**
 * Created by bel on 15.06.16.
 */
public class GenderPreferenceD extends DialogPreference {

    private final static String TAG = "Debug_GenderPreferenceD";

    private static final String DIALOG_FRAGMENT_TAG =
            "android.support.v7.preference.PreferenceFragment.DIALOG";

    private TextView tvGenderValue;

    public GenderPreferenceD(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    public GenderPreferenceD(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public GenderPreferenceD(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public GenderPreferenceD(Context context) {
        super(context);
        init();
    }

    private void init(){
        Log.d(TAG, "init D ()");

        setPersistent(true);
        setLayoutResource(R.layout.preference_gender_v3);

        Log.d(TAG, "isPersist " + isPersistent());

        if(getDialogLayoutResource() == 0) {
            setDialogLayoutResource(R.layout.preference_gender_dialog_v3);
        }

    }

    @Override
    public void onBindViewHolder(PreferenceViewHolder holder) {
        super.onBindViewHolder(holder);

        Log.d(TAG, "onBindViewHolder()");

        tvGenderValue = (TextView) holder.findViewById(R.id.tv_gender_value_v3);

        if(shouldPersist()){
            final String gender = getPersistedString(getContext().getResources().getString(R.string.preferences_gender_male));
            tvGenderValue.setText(gender);
        }

    }

    public static void onDisplayPreferenceDialog(PreferenceFragmentCompat preferenceFragment, Preference preference){
        Log.d(TAG, "onDisplayPreferenceDialog(preferenceFragment, preference)");
        FragmentManager fragmentManager = preferenceFragment.getFragmentManager();
        if(fragmentManager.findFragmentByTag(DIALOG_FRAGMENT_TAG) == null) {
            GenderPreferenceDialogFragmentD genderPreferenceD =
                    GenderPreferenceDialogFragmentD.newInstance(preference.getKey());
            genderPreferenceD.setTargetFragment(preferenceFragment, 0);
            genderPreferenceD.show(fragmentManager, DIALOG_FRAGMENT_TAG);
        }
    }

    public static class GenderPreferenceDialogFragmentD extends PreferenceDialogFragmentCompat implements View.OnClickListener{

        private Button bMale;
        private Button bFemale;
        private String value;
        private TextView tvCurValue;
        private String sMale;
        private String sFemale;


        public static GenderPreferenceDialogFragmentD newInstance(String key) {
            GenderPreferenceDialogFragmentD fragment = new GenderPreferenceDialogFragmentD();
            Bundle b = new Bundle();
            b.putString("key", key);
            fragment.setArguments(b);
            return fragment;
        }

        @Override
        protected void onBindDialogView(View view) {
            Log.d(TAG, "onBindDialogView()");
            super.onBindDialogView(view);

            sMale = getResources().getString(R.string.preferences_gender_male);
            sFemale = getResources().getString(R.string.preferences_gender_female);

            bMale = (Button) view.findViewById(R.id.b_pref_male_v3);
            bFemale = (Button) view.findViewById(R.id.b_pref_female_v3);

            bMale.setOnClickListener(this);
            bFemale.setOnClickListener(this);

            resetBackground(bMale);
            resetBackground(bFemale);

            value = ((GenderPreferenceD) getPreference()).getPersistedString(sMale);
            final Button changeButton = value.equals(sMale) ? bMale : bFemale;
            setButtonBackground(changeButton);
        }

        @Override
        public void onDialogClosed(boolean b) {
            Log.d(TAG, "onDialogClosed()");
            changeValue(value);
        }


        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.b_pref_male_v3:

                    setButtonBackground(bMale);
                    resetBackground(bFemale);
                    value = sMale;

                    break;
                case R.id.b_pref_female_v3:

                    setButtonBackground(bFemale);
                    resetBackground(bMale);
                    value = sFemale;

                    break;
            }
        }

        private void resetBackground(Button button){
            button.setBackgroundResource(0);
        }

        private void setButtonBackground(Button button){
            button.setBackgroundColor(Color.rgb(121, 218, 255));
        }

        private void changeValue(String value){
            GenderPreferenceD preferenceD = (GenderPreferenceD) getPreference();
            if(preferenceD.shouldPersist()){
                preferenceD.persistString(value);
            }
        }

    }

}
