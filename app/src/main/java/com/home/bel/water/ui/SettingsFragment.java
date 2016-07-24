package com.home.bel.water.ui;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.home.bel.water.R;
import com.home.bel.water.utils.AppConstants;
import com.home.bel.water.utils.GenderPreference;
import com.home.bel.water.utils.GenderPreferenceB;
import com.home.bel.water.utils.GenderPreferenceD;
import com.home.bel.water.utils.WeightDialogPreference;

import org.androidannotations.annotations.AfterPreferences;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.PreferenceByKey;
import org.androidannotations.annotations.PreferenceClick;
import org.androidannotations.annotations.PreferenceScreen;

/**
 * Settings
 */
@PreferenceScreen(R.xml.preferences)
@EFragment
public class SettingsFragment extends PreferenceFragmentCompat  implements SharedPreferences.OnSharedPreferenceChangeListener{

    private final static String TAG = "Debug_SettingsFragment";

    public final static String KEY_FOOD_RATION = "key.foodRation";

    @PreferenceByKey(R.string.preferences_key_gender)
    GenderPreference preferenceGender;

    @PreferenceByKey(R.string.preferences_key_gender_v2)
    GenderPreferenceB preferenceGenderB;

    @AfterPreferences
    void afterPreferences(){

    }

    @PreferenceClick(R.string.preferences_key_gender)
    void onPrefGenderClick(){
        preferenceGender.changeGender(preferenceGender.getGenderValue().equals(GenderPreference.MALE)
                ? GenderPreference.FEMALE : GenderPreference.MALE);
    }

    @PreferenceClick(R.string.preferences_key_gender_v2)
    void onPrefGenderBClick(){
        preferenceGenderB.changeGender();
    }

    @Override
    public void onDisplayPreferenceDialog(Preference preference) {
        Log.d(TAG, "onDisplayPreferenceDialog()");
        if(preference instanceof WeightDialogPreference){
            WeightDialogPreference.onDisplayPreferenceDialog(this, preference);
        }
        else if(preference instanceof GenderPreferenceD)
        {
            GenderPreferenceD.onDisplayPreferenceDialog(this, preference);
        }
        else {
            super.onDisplayPreferenceDialog(preference);
        }
    }

    @Override
    public void onCreatePreferences(Bundle bundle, String s) {
        Log.d(TAG, "onCreatePreferences(), s = " + s);
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
        getPreferenceScreen().getSharedPreferences()
                .registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
        getPreferenceScreen().getSharedPreferences()
                .unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        Log.d(TAG, "onSharedPreferenceChanged");
        Log.d(TAG, "Key = " + key);
        View view = getView();
        switch (key) {
            case AppConstants.KEY_GENDER:

                break;
            case AppConstants.KEY_GENDER_V3:
                Log.d(TAG, key + " changed");

                if (view != null){
                    TextView tvValue = (TextView) view.findViewById(R.id.tv_gender_value_v3);
                    tvValue.setText(sharedPreferences.getString(key, "something"));
                }
                break;
            case AppConstants.KEY_WEIGHT:
                Log.d(TAG, key + " changed");
                if(view != null) {
                    TextView tvValue = (TextView) view.findViewById(R.id.tv_weight_value);
                    double value = Double.longBitsToDouble(sharedPreferences.getLong(key, 0));
                    String text = String.format("%.0f", value);
                    tvValue.setText(text);
                }
                break;
            case AppConstants.KEY_NOTIFICATION:
                Log.d(TAG, key + " changed");
                Preference notificationExtraPreference = getPreferenceManager().findPreference(AppConstants.KEY_NOTIFICATION_EXTRA);
                boolean enable = sharedPreferences.getBoolean(key, true);
                Log.d(TAG, "Enabled" + enable);
//                notificationExtraPreference.setEnabled(!enable);
                break;

        }

    }
}
