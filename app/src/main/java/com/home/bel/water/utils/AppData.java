package com.home.bel.water.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Shared Preferences Singleton
 */
public class AppData {

    private static final String TAG = "Debug_AppData";

    private static final String APP_DATA_NAME = "applicationData";

    private volatile static AppData instance = null;

    private final SharedPreferences appLocalData;

//    public static final String IS_FIRST_LAUNCH = "is_first_launch";
//    public static final String IS_FIRST_SETTING_DONE = "is_first_setting";
    public static final String WATER_AMOUNT_DAY = "water_amount_day";

    private AppData(Context context){
        appLocalData = context.getSharedPreferences(APP_DATA_NAME, Context.MODE_PRIVATE);
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

    public void setFirstSettingDone(boolean firstSettingDone){
        appLocalData.edit()
                .putBoolean("is_first_setting", firstSettingDone)
                .apply();
    }

    public boolean isFirstSetting(){
        return appLocalData.getBoolean("is_first_setting", true);
    }

    public void setWeightUnitKg(Boolean weightUnitKg){
        appLocalData.edit()
                .putBoolean("is_weight_unit_kg", weightUnitKg)
                .apply();
    }

    public boolean isWeightUnitKg(){
        return appLocalData.getBoolean("is_weight_unit_kg", true);
    }

    public void setUserWeight(double userWeight){
        appLocalData.edit()
                    .putLong("user_weight", Double.doubleToLongBits(userWeight))
                    .apply();
    }

    public double getUserWeight(){
        return Double.longBitsToDouble(appLocalData.getLong("user_weight", 0));
    }
}
