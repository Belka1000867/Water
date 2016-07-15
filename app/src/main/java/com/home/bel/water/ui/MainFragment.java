package com.home.bel.water.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.home.bel.water.R;
import com.home.bel.water.utils.AppConstantFunctions;
import com.home.bel.water.utils.AppConstants;
import com.home.bel.water.utils.AppData;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;

/**
 * fragment with the addition of water
 */
@EFragment(R.layout.fragment_main)
public class MainFragment extends Fragment {

    private double mWaterAmountDay;
    private double mWaterAmountLeft;
    private int mWaterAmountLeftPercent;

    private Resources resources;
    private SharedPreferences preferences;


    private String sVolumeUnitMl;
    private String sVolumeUnitOz;

    private String sVolumeUnit;
    private double dVolumeUnit;

    private double dWeightUnit;

    private AppData appData;
    //Preferences
    private double weight;

    private MainFragmentListener mainFragmentListener;

    final static String TAG = "Debug_MainFragment";

    public interface MainFragmentListener{

        void changeTab(int position);

        void showAmountOfGlassesLeft(int glassesLeft);

    }

    @ViewById(R.id.tv_main_waterAmount)
    TextView tvWaterAmount;

    @ViewById(R.id.tv_main_waterAmountLeftPercent)
    TextView tvWaterAmountLeftPercent;

    @ViewById(R.id.b_main_drink)
    Button bDrink;

    @AfterViews
    void afterViews(){
        Log.d(TAG, "afterViews()");
    }

    @Click(R.id.b_main_drink)
    void clickDrink(){
        double volumeDrink = 200;

        getVolumeUnit();

        mWaterAmountLeft -= volumeDrink / dVolumeUnit;

        String sWaterAmountLeft;

        if(mWaterAmountLeft < 0){
            sWaterAmountLeft = "Above the norm on " + Math.abs(mWaterAmountLeft) + sVolumeUnit;
        }
        else
        {
            sWaterAmountLeft = String.valueOf(mWaterAmountLeft) + sVolumeUnitMl;
        }

        tvWaterAmountLeftPercent.setText(sWaterAmountLeft);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d(TAG, "onAttach()");

        try {
            mainFragmentListener = (MainFragmentListener) context;
        } catch (Exception e) {
            Log.d(TAG, AppConstants.ERROR + "mainFragmentListener " + e.getMessage());
        }

        if(mainFragmentListener != null){
            mainFragmentListener.showAmountOfGlassesLeft(7);
        }

        // Get shared preferences
        appData = AppData.getInstance(getActivity());

        resources = getResources();
        preferences = PreferenceManager.getDefaultSharedPreferences(context);

        sVolumeUnitMl = resources.getString(R.string.preferences_volume_ml);
        sVolumeUnitOz = resources.getString(R.string.preferences_volume_oz);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate()");

        //Get weight and weight unit
        getWeightUnit();
        long weightLong = preferences.getLong(AppConstants.KEY_WEIGHT, 0);
        weight = Double.longBitsToDouble(weightLong);

        //Get water volume unit
        getVolumeUnit();

        if(appData.isFirstLaunch() && mainFragmentListener != null){
            mainFragmentListener.changeTab(AppConstants.BOT_NAV_POSITION_SETTINGS);
            appData.setFirstLaunch(false);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume()");

        final double mlWaterKg = 30;

        mWaterAmountDay = weight / dWeightUnit * mlWaterKg / dVolumeUnit;

        setWaterAmountText();

        mWaterAmountLeft = mWaterAmountDay;
    }

    private void getVolumeUnit(){
        final double mlValue = 1;
        final double ozValue = 29.57353;
        sVolumeUnit = preferences.getString(AppConstants.KEY_VOLUME, sVolumeUnitMl);
        dVolumeUnit = sVolumeUnit.equals(sVolumeUnitMl) ? mlValue : ozValue;

        Log.d(TAG, "weight = " + weight);
        Log.d(TAG, "volume unit  = " + sVolumeUnit);
        Log.d(TAG, "double volume unit  = " + dVolumeUnit);
    }

    private void getWeightUnit(){
        dWeightUnit = appData.isWeightUnitKg() ? AppConstants.UNIT_VALUE_KG : AppConstants.UNIT_VALUE_LB;
    }

    private void setWaterAmountText(){
        tvWaterAmount.setText(String.format("%.0f " + sVolumeUnit, mWaterAmountDay));
    }

}
