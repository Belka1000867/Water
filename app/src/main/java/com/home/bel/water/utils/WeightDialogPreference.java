package com.home.bel.water.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.Resources;
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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.home.bel.water.R;

/**
 * Preference to enter the weight for the user.
 */
public class WeightDialogPreference extends DialogPreference implements View.OnClickListener {

    private static final String DIALOG_FRAGMENT_TAG =
            "android.support.v7.preference.PreferenceFragment.DIALOG";

    private AppData appData;
    private SharedPreferences sharedPreferences;

    private TextView tvWeightValue;
    private Button bKg;
    private Button bLb;

    private final static String TAG = "Debug_WeightPreference";

    public WeightDialogPreference(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        Log.d(TAG, "WeightPreference(context, attrs, defStyleAttr, defStyleRes)");
        init(context);
    }

    public WeightDialogPreference(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Log.d(TAG, "WeightPreference(context, attrs, defStyleAttr)");
        init(context);
    }

    public WeightDialogPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
        Log.d(TAG, "WeightPreference(context, attrs)");
        init(context);
    }

    public WeightDialogPreference(Context context) {
        super(context);
        Log.d(TAG, "WeightPreference(context)");
        init(context);
    }

    private void init(Context context){
        appData = AppData.getInstance(context);

//      Set required UI for dialog
        Resources resources = context.getResources();
        String title = resources.getString(R.string.preferences_title_weight); // + "  [ " + unit + " ]" ;

        setLayoutResource(R.layout.preference_weight);

        setDialogTitle(title);
        setDialogIcon(R.mipmap.ic_water);
        setDialogLayoutResource(R.layout.preference_weight_dialog);
    }

    public static void onDisplayPreferenceDialog(PreferenceFragmentCompat preferenceFragment, Preference preference){
        Log.d(TAG, "onDisplayPreferenceDialog(preferenceFragment, preference)");
        FragmentManager fragmentManager = preferenceFragment.getFragmentManager();
        if(fragmentManager.findFragmentByTag(DIALOG_FRAGMENT_TAG) == null) {
            WeightPreferenceDialogFragment weightFragment =
                    WeightPreferenceDialogFragment.newInstance(preference.getKey());
            weightFragment.setTargetFragment(preferenceFragment, 0);
            weightFragment.show(fragmentManager, DIALOG_FRAGMENT_TAG);
        }
    }

    @Override
    public void onBindViewHolder(PreferenceViewHolder holder) {
        super.onBindViewHolder(holder);

        tvWeightValue = (TextView) holder.findViewById(R.id.tv_weight_value);
        bKg = (Button) holder.findViewById(R.id.b_weight_kg);
        bLb = (Button) holder.findViewById(R.id.b_weight_lb);

        bKg.setOnClickListener(this);
        bLb.setOnClickListener(this);

        sharedPreferences = getPreferenceManager().getSharedPreferences();
        //set text with the value from the shared preferences of the settings
        setText(getWeight());

        //Define which button should be focused
        boolean isUnitKg = appData.isWeightUnitKg();
        Button focusedButton = isUnitKg ? bKg : bLb;
        focusButton(focusedButton);
    }

    @Override
    public void onClick(View v) {
        double value;
        Button focusedButton;

        if(v.equals(bKg)){
            focusedButton = bKg;
            value = getWeight()/AppConstants.UNIT_VALUE_LB;
        }
        else {
            focusedButton = bLb;
            value = getWeight()*AppConstants.UNIT_VALUE_LB;
        }

        focusButton(focusedButton);
        saveValue(value);
    }

    private void focusButton(Button button){
        button.setActivated(true);
        button.setEnabled(false);

        boolean isUnitKg = button.equals(bKg);

        final Button oppositeButton = isUnitKg ? bLb : bKg;
        oppositeButton.setActivated(false);
        oppositeButton.setEnabled(true);

        appData.setWeightUnitKg(isUnitKg);
    }

    private void setText(double value){
        tvWeightValue.setText(String.format("%.0f", value));
    }

    private void saveValue(double value){
        if(shouldPersist()){
            persistLong(Double.doubleToLongBits(value));
            setText(value);
        }
    }

    private double getWeight(){
        long weight = sharedPreferences.getLong(getKey(), 0);
        return Double.longBitsToDouble(weight);
    }

    public static class WeightPreferenceDialogFragment extends PreferenceDialogFragmentCompat implements OnItemClickListener{

        private AppData appData;
        private ListView mListView;

        private final int BTN_OK = -1;
        private final int BTN_CANCEL = -2;

        private double weight;
        private double unit;

        public static WeightPreferenceDialogFragment newInstance(String key) {
            WeightPreferenceDialogFragment fragment = new WeightPreferenceDialogFragment();
            Bundle b = new Bundle();
            b.putString("key", key);
            fragment.setArguments(b);
            return fragment;
        }

        @Override
        protected void onBindDialogView(View view) {
            Log.d(TAG, "onBindDialogView()");
            super.onBindDialogView(view);

            appData = AppData.getInstance(getContext());
            unit = appData.isWeightUnitKg() ? AppConstants.UNIT_VALUE_KG : AppConstants.UNIT_VALUE_LB;

            mListView = (ListView) view.findViewById(R.id.listview_weight);

            mListView.setDivider(null);
            mListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
            mListView.setItemChecked(100, true);

            WeightDialogPreferenceListAdapter weightPreferenceListAdapter = new WeightDialogPreferenceListAdapter(getContext());
            mListView.setAdapter(weightPreferenceListAdapter);

            //Select item in the middle of the list
            //int selectedItem = weightPreferenceListAdapter.getCount() / 2 - 1;
            //mListView.setSelection(selectedItem);

            mListView.setOnItemClickListener(this);

            mListView.setOnItemLongClickListener((AdapterView<?> parent, View itemView, int position, long id) -> {
                Log.d(TAG, "setOnItemLongClickListener()");
                Log.d(TAG, "position = " + position);
                Log.d(TAG, "id = " + id);

                return false;
            });

            mListView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    Log.d(TAG, "onItemSelected()");
                    Log.d(TAG, "position = " + position);
                    Log.d(TAG, "id = " + id);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

        }

        @Override
        public void onDialogClosed(boolean b) {
            Log.d(TAG, "onDialogClosed()");
        }

        @Override
        public void onDismiss(DialogInterface dialog) {
            super.onDismiss(dialog);
            Log.d(TAG, "onDismiss()");
        }

        @Override
        public void onClick(DialogInterface dialog, int which) {
            super.onClick(dialog, which);
            Log.d(TAG, "onClick()");
            Log.d(TAG, "which = " + which);
            switch (which){
                case BTN_OK:
                    Log.d(TAG, "OK clicked");

                    DialogPreference preference = getPreference();
                    if(preference instanceof WeightDialogPreference){
                        WeightDialogPreference weightPreference = (WeightDialogPreference) preference;
                        if(weightPreference.shouldPersist()){
                            weightPreference.persistLong(Double.doubleToLongBits(weight));
                        }
                    }

                    break;
                case BTN_CANCEL:
                    Log.d(TAG, "Cancel clicked");
                    break;
            }
        }

        // List View on Item Click
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Log.d(TAG, "onItemClick() position = " + position);

            weight = position + AppConstants.LIST_WEIGHT_OFFSET_KG * unit;

            view.setSelected(true);
        }
    }

}

