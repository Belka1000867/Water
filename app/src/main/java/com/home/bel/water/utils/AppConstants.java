package com.home.bel.water.utils;

import android.provider.BaseColumns;

/**
 * Created by bel on 04.06.16.
 */
public final class AppConstants {

    public AppConstants(){}

    /* Bottom Navigation Menu Positions */
    public static final int BOT_NAV_POSITION_REMINDERS = 0;
    public static final int BOT_NAV_POSITION_STATISTICS = 1;
    public static final int BOT_NAV_POSITION_MAIN = 2;
    public static final int BOT_NAV_POSITION_FORTH = 3;
    public static final int BOT_NAV_POSITION_SETTINGS = 4;

    /* Bottom Navigation Menu Item Names */
    public static final String BOT_NAV_ITEM_SETTINGS = "Settings";
    public static final String BOT_NAV_ITEM_STATISTICS = "Statistics";
    public static final String BOT_NAV_ITEM_MAIN = "Main";
    public static final String BOT_NAV_ITEM_FORTH = "Forth";
    public static final String BOT_NAV_ITEM_REMINDERS = "Reminders";

    /* Preference keys */
    public static final String KEY_WEIGHT = "key_weight";
    public static final String KEY_GENDER = "key.gender";
    public static final String KEY_GENDER_V3 = "key_gender_v3";
    public static final String KEY_VOLUME = "key_volume";
    public static final String KEY_NOTIFICATION = "key_notification";
    public static final String KEY_NOTIFICATION_EXTRA = "key_notification_extra";

    /* Unit Weight Value */
    public static final double VALUE_WEIGHT_KG = 1;
    public static final double VALUE_WEIGHT_LB = 2.204;

    /* Unit Volume Value */
    public static final double VALUE_VOLUME_ML = 1;
    public static final double VALUE_VOLUME_OZ = 29.57353;

    /* Preference persist values */
    public static final String VOLUME_ML = "Ml";
    public static final String VOLUME_OZ = "Oz";

    public static final String MALE = "Male";
    public static final String FEMALE = "Female";

    /* Preference variables */
    public static final int LIST_WEIGHT_OFFSET_KG = 20;
    public static final int NECESSARY_ML_PER_KG = 30;

    public static final int PERCENTAGE_FULL = 100;
    public static final int PERCENTAGE_EMPTY = 0;

    public static final int GLASS_VOLUME = 200;

    /* To work with the database */

//    public static final String ACTION_INSERT_DATA = "action_insert_data";
//    public static final String ACTION_UPDATE_DATA = "action_update_data";
    public static final String ACTION_IS_CURRENT_DAY = "action_is_current_day";
    public static final String ACTION_GET_WEEK_DATA = "action_get_week_data";
    public static final String ACTION_GET_DAY_DATA = "action_get_day_data";

    public static final String EXTRAS_DB_DATE = "extras_db_date";
    public static final String EXTRAS_DB_AMOUNT = "extras_db_amount";
    public static final String EXTRAS_DB_DRUNK = "extras_db_drunk";
    public static final String EXTRAS_DB_ROWS_COUNT = "extras_db_rows_count";
    public static final String EXTRAS_INTENT_RESULT = "extras_intent_result";


    /* Key codes for DbResultReceiver */
    public static final int RESULT_INSERT_CURRENT_DAY = 1;
    public static final int RESULT_UPDATE_CURRENT_DAY = 2;
    public static final int RESULT_CURRENT_DAY = 3;
    public static final int RESULT_CURRENT_WEEK = 4;
    public static final int RESULT_CURRENT_DAY_TRUE = 5;
    public static final int RESULT_CURRENT_DAY_FALSE = 6;


    /* For Debug */
    public static final String ERROR = "ERROR! ";

    /* Default errors that can arise */
    public static final int DIALOG_ERROR = 401;


}
