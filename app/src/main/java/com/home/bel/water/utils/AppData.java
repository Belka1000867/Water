package com.home.bel.water.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Shared Preferences Singleton
 */
public class AppData {

    private static final String TAG = "Debug_AppData";

    private static final String APP_DATA_NAME = "applicationData";

    private volatile static AppData instance = null;

    private final SharedPreferences appLocalData;
    private final SharedPreferences appSettings;

    private AppData(Context context){
        appLocalData = context.getSharedPreferences(APP_DATA_NAME, Context.MODE_PRIVATE);
        appSettings = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static AppData getInstance(Context context){
        if(instance == null){
            synchronized (AppData.class){
                if(instance == null)
                    instance = new AppData(context.getApplicationContext());
            }
        }
        return instance;
    }

    public void setFirstLaunch(boolean firstLaunch){
        appLocalData.edit()
                    .putBoolean("is_first_launch", firstLaunch)
                    .apply();
    }

    public boolean isFirstLaunch(){
        return appLocalData.getBoolean("is_first_launch", true);
    }

    public void setWeightUnitKg(Boolean weightUnitKg){
        appLocalData.edit()
                .putBoolean("is_weight_unit_kg", weightUnitKg)
                .apply();
    }

    public double getWeightValue(){
        return isWeightUnitKg() ? AppConstants.VALUE_WEIGHT_KG : AppConstants.VALUE_WEIGHT_LB;
    }

    public boolean isWeightUnitKg(){
        return appLocalData.getBoolean("is_weight_unit_kg", true);
    }

    /*
    * Get user weight from application settings.
    * */
    public double getUserWeight(){
        return Double.longBitsToDouble(appSettings.getLong(AppConstants.KEY_WEIGHT, 0));
    }

    public boolean isVolumeUnitMl(){
        String sVolumeUnit = appSettings.getString(AppConstants.KEY_VOLUME, AppConstants.VOLUME_ML);
        return sVolumeUnit.equals(AppConstants.VOLUME_ML);
    }

    public double getVolumeValue(){
        return isVolumeUnitMl() ? AppConstants.VALUE_VOLUME_ML : AppConstants.VALUE_VOLUME_OZ;
    }

    public String getVolumeUnit(){
        return isVolumeUnitMl() ? AppConstants.VOLUME_ML : AppConstants.VOLUME_OZ;
    }

    /*
     * Parameter for the amount of water for the day.
     * Count in real time from current units and values.
      * */
    public double getWaterDay(){
        return getUserWeight() / getWeightValue() * AppConstants.NECESSARY_ML_PER_KG / getVolumeValue();
    }

    /*
    * The amount of water user need to drink
    * until the end of the day.
    *
    * The parameter is stored in SharedPreferences
    * and multiplied/divided by the Volume Value (1 or 29.57353)
    * */

    public void setWaterDayLeft(double waterDayLeft){
        double waterLeftMl = waterDayLeft * getVolumeValue();
        appLocalData.edit()
                .putLong("water_left", Double.doubleToLongBits(waterLeftMl))
                .apply();
    }

    public double getWaterDayLeft(){
        // Default value is the amount of water for today
        long defaultValue = Double.doubleToLongBits(getWaterDay());
        return Double.longBitsToDouble(appLocalData.getLong("water_left", defaultValue)) / getVolumeValue();
    }

    /*
    * The amount of water user need
    * to drink represented in percentage.
    * */

    public void setWaterLeftPercentage(double percentage){
        appLocalData.edit()
                    .putLong("water_left_percentage", Double.doubleToLongBits(percentage))
                    .apply();
    }

    public double getWaterLeftPercentage(){
        long waterLeftPercentage = appLocalData.getLong("water_left_percentage", 0);
        return Double.longBitsToDouble(waterLeftPercentage);
    }

    /*
    * The amount of water user need to drink
    * represented in amount of glasses from
    * the default volume of the glass.
    * */

    public void setAmountGlassesLeft(int glasses){
        appLocalData.edit()
                    .putInt("water_glasses", glasses)
                    .apply();
    }

    public int getAmountGlassesLeft(){
        return (int) getWaterDayLeft() / AppConstants.GLASS_VOLUME;
    }

    public void refreshDayParameters(){

        double waterDayLeft = getWaterDay();

        setWaterDayLeft(waterDayLeft);
        setWaterLeftPercentage(AppConstants.PERCENTAGE_EMPTY);
    }

    public void setWaterDayDrunk(double drunk){
        appLocalData.edit()
                    .putLong("water_day_drunk", Double.doubleToLongBits(drunk))
                    .apply();
    }

    public Double getWaterDayDrunk(){
        long waterDayDrunk = appLocalData.getLong("water_day_drunk", 0);
        return Double.longBitsToDouble(waterDayDrunk);
    }
}
