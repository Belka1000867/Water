package com.home.bel.water.utils;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.preference.DialogPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceDialogFragmentCompat;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.PreferenceViewHolder;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;

import com.home.bel.water.utils.TimePreferences.TimeCustomDialogPreference;

import static com.home.bel.water.utils.AppConstants.KEY_BEDTIME;
import static com.home.bel.water.utils.AppConstants.KEY_WAKE;
import static com.home.bel.water.utils.AppConstants.KEY_WEIGHT;
import static com.home.bel.water.utils.AppConstants.PREFERENCE_FRAGMENT_DIALOG_TAG;

/**
 * Created by bel on 03.10.16.
 */

public abstract class CustomDialogPreference extends DialogPreference
//        implements CustomPreferenceInterface
//        , View.OnClickListener
{

    private AppData mAppData;

    private boolean isDialogPreference = false;
    private int preferenceLayout;
    private String dialogTitle;
    private int dialogIcon;
    private int dialogLayoutResource = 0;

    private Button btnFirst;
    private Button btnSecond;

    private int btnFirstId;
    private int btnSecondId;

    private Object valueFirst;
    private Object valueSecond;

    public abstract void init(Context context);

  //  public abstract void whenButtonIsFocused(Button button);

    public CustomDialogPreference setDialogPreference(boolean isDialogPreference){
        this.isDialogPreference = isDialogPreference;
        return this;
    }

    public CustomDialogPreference setPreferenceLayout(int preferenceLayout){
        this.preferenceLayout = preferenceLayout;
        return this;
    }

    public CustomDialogPreference setDialogResources(String dialogTitle, int dialogIcon, @Nullable int dialogLayoutResource){
        this.dialogTitle = dialogTitle;
        this.dialogIcon = dialogIcon;
        this.dialogLayoutResource = dialogLayoutResource;
        return this;
    }

    public CustomDialogPreference setButtonIds(int btnFirstId, int btnSecondId){
        this.btnFirstId = btnFirstId;
        this.btnSecondId = btnSecondId;
        return this;
    }

    public CustomDialogPreference setValues(Object valueFirst, Object valueSecond){
        this.valueFirst = valueFirst;
        this.valueSecond = valueSecond;
        return this;
    }


    public CustomDialogPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);

        mAppData = AppData.getInstance(context);

        setLayoutResource(preferenceLayout);
        setDialogTitle(dialogTitle);
        setDialogIcon(dialogIcon);
        if(dialogLayoutResource > 0) setDialogLayoutResource(dialogLayoutResource);
    }

//    @Override
//    public void onBindViewHolder(PreferenceViewHolder holder) {
//        super.onBindViewHolder(holder);
//
//        // Disable click from the Preference View
//        holder.itemView.setClickable(isDialogPreference);
//
//        btnFirst = (Button) holder.findViewById(btnFirstId);
//        btnSecond = (Button) holder.findViewById(btnSecondId);
//        btnFirst.setOnClickListener(this);
//        btnSecond.setOnClickListener(this);
//
//        Object currentValue = getPersistValue();
//        final Button bFocused = currentValue.equals(valueFirst) ? btnFirst : btnSecond;
//        focusButton(bFocused);
//    }
//
//    private void focusButton(Button btn){
//        btn.setActivated(true);
//        btn.setEnabled(false);
//
//        final Button oppositeButton = getOppositeBtn(btn);
//        oppositeButton.setEnabled(true);
//        oppositeButton.setActivated(false);
//
//        whenButtonIsFocused(btn);
//    }
//
//    private Button getOppositeBtn(Button button){
//        return isMainButton(button) ? btnSecond : btnFirst;
//    }
//
//    protected boolean isMainButton(Button button){
//        return button.equals(btnFirst);
//    }
//
//    @Override
//    public void onClick(View v) {
//        Button btnFocused = v.equals(btnFirst) ? btnFirst : btnSecond;
//        Object value = v.equals(btnFirst) ? valueFirst : valueSecond;
//        focusButton(btnFocused);
//        setPersistValue(value);
//    }

//    protected Object getValueFirst(){
//        return valueFirst;
//    }

    protected AppData getAppData(){
        return mAppData;
    }

    public static void onDisplayPreferenceDialog(PreferenceFragmentCompat preferenceFragment, Preference preference){
        // Getting Fragment Manager ..
        FragmentManager fragmentManager = preferenceFragment.getFragmentManager();

        if(fragmentManager.findFragmentByTag(PREFERENCE_FRAGMENT_DIALOG_TAG) == null) {
            CustomDialogPreference.CustomPreferenceDialogFragmentCompat customPreferenceDialogFragment =
                    CustomPreferenceDialogFragmentCompat.newInstance(preference.getKey());
            customPreferenceDialogFragment.setTargetFragment(preferenceFragment, 0);
            customPreferenceDialogFragment.show(fragmentManager, PREFERENCE_FRAGMENT_DIALOG_TAG);
        }
    }

    public static class CustomPreferenceDialogFragmentCompat extends PreferenceDialogFragmentCompat {

        public static CustomPreferenceDialogFragmentCompat createFragment(String preferenceKey){
            switch(preferenceKey){
                case KEY_WAKE:
                case KEY_BEDTIME:
                    return new TimeCustomDialogPreference.TimePreferenceDialogFragmentCompat();
                case KEY_WEIGHT:
                    return new WeightDialogPreference.WeightPreferenceDialogFragmentCompat();
            }
            throw new IllegalArgumentException();
        }

        public static CustomPreferenceDialogFragmentCompat newInstance(String key) {
            CustomPreferenceDialogFragmentCompat fragment =
                    createFragment(key);
            Bundle b = new Bundle();
            b.putString("key", key);
            fragment.setArguments(b);
            return fragment;
        }

        public CustomPreferenceDialogFragmentCompat(){}

        @Override
        public void onDialogClosed(boolean b) {

        }
    }
}
