package com.home.bel.water.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.TextView;

import com.home.bel.water.R;
import com.home.bel.water.database.DbIntentService;
import com.home.bel.water.utils.AppConstants;
import com.home.bel.water.utils.AppData;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.Locale;

/**
 * fragment with the addition of water
 */
@EFragment(R.layout.fragment_main)
public class MainFragment extends Fragment {

    private double dWaterAmountDay;
    private double dWaterAmountDayLeft;

    private String sVolumeUnit;

    private AppData appData;

    private MainFragmentListener mainFragmentListener;

    final static String TAG = "Debug_MainFragment";

    public interface MainFragmentListener{

        void changeTab(int position);

        void showAmountOfGlassesLeft(int glassesLeft);

        void showAlarm(int id);

    }

    @ViewById(R.id.tv_main_waterAmount)
    TextView tvWaterAmount;

    @ViewById(R.id.tv_main_waterAmountLeft)
    TextView tvWaterAmountLeft;

    @ViewById(R.id.tv_main_waterAmountLeft_title)
    TextView tvWaterAmountLeftTitle;

    @ViewById(R.id.tv_main_waterAmountLeftPercent)
    TextView tvWaterAmountLeftPercent;

    @ViewById(R.id.tv_main_glassesLeft)
    TextView tvGlassesLeft;

    @AfterViews
    void afterViews(){
        Log.d(TAG, "afterViews()");

        refreshTextWaterAmount();
        refreshTextWaterAmountLeft();
    }

    @Click(R.id.b_main_drink)
    void clickDrink(){
        double volumeValue = appData.getVolumeValue();
        double dWaterLeft = appData.getWaterDayLeft();
        double glassValue = AppConstants.GLASS_VOLUME / volumeValue;

        dWaterLeft -= glassValue;
        appData.setWaterDayLeft(dWaterLeft);

        double dWaterDrunk = appData.getWaterDay() - dWaterLeft;
        Log.d(TAG, "clickDrink: " + dWaterDrunk);
        appData.setWaterDayDrunk(dWaterDrunk);

        double leftDayPercent = appData.getWaterLeftPercentage();
        double glassPercent = glassValue * AppConstants.PERCENTAGE_FULL / dWaterAmountDay;
        leftDayPercent += glassPercent;
        appData.setWaterLeftPercentage(leftDayPercent);

        refreshTextWaterAmountLeft();
        writeDbDayData();
    }

    @Click(R.id.btnRefresh)
    void clickRefresh(){
        appData.refreshDayParameters();

        refreshTextWaterAmount();
        refreshTextWaterAmountLeft();

        /*   --- REST API ---
        *    Call Intent Service to work with Database
        * */
//        Intent intent = new Intent(getActivity(), DBIntentService.class);
//        intent.setAction(AppConstants.);
//        getActivity().startService(intent);
        Log.d(TAG, "start service");

//      SAVE DATA TO SQL DATABASE
//
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d(TAG, "onAttach()");

        try {
            mainFragmentListener = (MainFragmentListener) context;
        } catch (Exception e) {
            Log.d(TAG, AppConstants.ERROR + " mainFragmentListener " + e.getMessage());
        }

        // Get sharedPreferences
        appData = AppData.getInstance(getActivity());
        //Get water volume unit
        sVolumeUnit = appData.getVolumeUnit();
        //Get water amount for the day
        dWaterAmountDay = appData.getWaterDay();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate()");

        if(appData.isFirstLaunch() && mainFragmentListener != null){
            mainFragmentListener.changeTab(AppConstants.BOT_NAV_POSITION_SETTINGS);
            appData.setFirstLaunch(false);
        }

        if(appData.getUserWeight() == 0 && !appData.isFirstLaunch()){
            if(mainFragmentListener != null){
                mainFragmentListener.changeTab(AppConstants.BOT_NAV_POSITION_SETTINGS);
                mainFragmentListener.showAlarm(R.string.error_settings_empty_weight);
            }
        }

    }

    /*
    * Refresh the text view that shows the normal amount
    * of water that user need to drink for day
    * */
    private void refreshTextWaterAmount(){
        String text = String.format("%.0f " + sVolumeUnit, dWaterAmountDay);
        tvWaterAmount.setText(text);
    }

    /*
    * Refresh the text view that shows left amount
    * of water that user still need to drink for day
    * */
    private void refreshTextWaterAmountLeft(){

        double value = appData.getWaterDayLeft();

        String title = value < 0 ? "Above the norm on :" : "Left to drink :";
        tvWaterAmountLeftTitle.setText(title);

        String text = String.format(Locale.getDefault(), "%.0f" + sVolumeUnit, Math.abs(value));
        tvWaterAmountLeft.setText(text);

        // Also refresh the remaining amount of water in percentage
        // and number of glasses necessary to drink
        refreshTextWaterAmountLeftPercentage();
        refreshTextGlassesLeft();
    }

    /*
    * Refresh the text view that shows left amount
    * of water that user still need to drink for day
    * */
    private void refreshTextWaterAmountLeftPercentage(){

//      Get the amount of water left to drink in percentage in real time
        double value = appData.getWaterLeftPercentage();

        final String text = String.format(Locale.getDefault(), "%.1f %%", value);
        tvWaterAmountLeftPercent.setText(text);
    }

    /*
    * Refresh the amount of water necessary to drink
    * in glasses represented
    * */
    private void refreshTextGlassesLeft(){

//      Count how many glasses left to drink
        int count = appData.getAmountGlassesLeft();
        count = count < 0 ? 0 : count;

//      Refresh the text in the text view
        final String text = String.valueOf(count);
        tvGlassesLeft.setText(text);

//      Refresh the amount of glasses in the bottom navigation menu
//        if(mainFragmentListener != null){
//            mainFragmentListener.showAmountOfGlassesLeft(count);
//        }
    }

    private void writeDbDayData(){
        Log.d(TAG, "Refreshing statistics table .. ");
        Intent intent = new Intent(getContext(), DbIntentService.class);
        intent.setAction(AppConstants.ACTION_IS_CURRENT_DAY);
        intent.putExtra(AppConstants.EXTRAS_DB_AMOUNT, appData.getWaterDay());
        intent.putExtra(AppConstants.EXTRAS_DB_DRUNK, appData.getWaterDayDrunk());
        getActivity().startService(intent);
    }

}
