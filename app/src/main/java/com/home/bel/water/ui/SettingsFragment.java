package com.home.bel.water.ui;

import android.os.Bundle;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.util.Log;

import com.home.bel.water.R;

import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.PreferenceScreen;

/**
 * Settings
 */
@PreferenceScreen(R.xml.preferences)
@EFragment
public class SettingsFragment extends PreferenceFragmentCompat {

    final static String TAG = "Debug_SettingsFragment";

    @Override
    public void onCreatePreferences(Bundle bundle, String s) {
        Log.d(TAG, "onCreatePreferences(), s = " + s);
    }
}
