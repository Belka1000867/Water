package com.home.bel.water.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.Resources;
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
public class  WeightDialogPreference extends CustomDialogPreference implements View.OnClickListener, CustomPreferenceInterface{

    private TextView tvWeightValue;
    private Button bKg;
    private Button bLb;

    private final static String TAG = "Debug_WeightPreference";

    @Override
    public void init(Context context) {
        Resources resources = context.getResources();
        String dialogTitle = resources.getString(R.string.preferences_title_weight);

        setPreferenceLayout(R.layout.preference_weight)
                .setDialogResources(dialogTitle, R.mipmap.ic_water, R.layout.preference_weight_dialog);
    }

//    @Override
//    public void whenButtonIsFocused(Button button) {
//        getAppData().setWeightUnitKg(isMainButton(button));
//    }

    public WeightDialogPreference(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    @Override
    public void onBindViewHolder(PreferenceViewHolder holder) {
        super.onBindViewHolder(holder);

        tvWeightValue = (TextView) holder.findViewById(R.id.tv_weight_value);
        bKg = (Button) holder.findViewById(R.id.b_weight_kg);
        bLb = (Button) holder.findViewById(R.id.b_weight_lb);
        bKg.setOnClickListener(this);
        bLb.setOnClickListener(this);

        //set text with the value from the shared preferences of the settings
        setValueText(getAppData().getUserWeight());

        //Define which button should be focused
        boolean isUnitKg = getAppData().isWeightUnitKg();
        Button focusedButton = isUnitKg ? bKg : bLb;
        focusButton(focusedButton);
    }

    @Override
    public void onClick(View v) {
        double value = v.equals(bKg) ? getAppData().getUserWeight()/AppConstants.VALUE_WEIGHT_LB :
                                        getAppData().getUserWeight()*AppConstants.VALUE_WEIGHT_LB;
        Button focusedButton = v.equals(bKg) ? bKg : bLb ;

        focusButton(focusedButton);
        setPersistValue(value);
    }

    private void focusButton(Button button){
        button.setActivated(true);
        button.setEnabled(false);

        boolean isUnitKg = button.equals(bKg);

        final Button oppositeButton = isUnitKg ? bLb : bKg;
        oppositeButton.setActivated(false);
        oppositeButton.setEnabled(true);

        getAppData().setWeightUnitKg(isUnitKg);
    }

    private void setValueText(double value){
        tvWeightValue.setText(String.format("%.0f", value));
    }

    @Override
    public void setPersistValue(Object value) {
        if(shouldPersist()){
            persistLong(Double.doubleToLongBits((double)value));
            setValueText((double)value);
        }
    }

    @Override
    public Object getPersistValue() {
        return getAppData().getUserWeight();
    }

    public static class WeightPreferenceDialogFragmentCompat extends CustomDialogPreference.CustomPreferenceDialogFragmentCompat implements OnItemClickListener{

        private AppData appData;
        private ListView mListView;

        private final int BTN_OK = -1;
        private final int BTN_CANCEL = -2;

        private double weight;
        private double unit;

        public WeightPreferenceDialogFragmentCompat(){}

        @Override
        protected void onBindDialogView(View view) {
            Log.d(TAG, "onBindDialogView()");
            super.onBindDialogView(view);

            appData = AppData.getInstance(getContext());
            unit = appData.getWeightValue();

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

