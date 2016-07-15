package com.home.bel.water;

import android.support.v4.app.Fragment;
import android.util.Log;

import com.home.bel.water.ui.ForthFragment_;
import com.home.bel.water.ui.MainFragment_;
import com.home.bel.water.ui.ReminderFragment_;
import com.home.bel.water.ui.SettingsFragment_;
import com.home.bel.water.ui.StatisticsFragment_;
import com.home.bel.water.utils.AppConstants;

/**
 * Factory for creating
 * 1. Fragment by given position
 * 2. Item Tag by given position
 *
 * not correct use of Factory pattern
 */
public class ChangeFragmentFactory {

    public static Fragment createFragment(int position){

        switch (position) {
            case AppConstants.BOT_NAV_POSITION_SETTINGS:
                return new SettingsFragment_();
            case AppConstants.BOT_NAV_POSITION_STATISTICS:
                return new StatisticsFragment_();
            case AppConstants.BOT_NAV_POSITION_MAIN:
                return new MainFragment_();
            case AppConstants.BOT_NAV_POSITION_FORTH:
                return new ForthFragment_();
            case AppConstants.BOT_NAV_POSITION_REMINDERS:
                return new ReminderFragment_();
        }
        throw new IllegalArgumentException();
    }

    public static String createItemDescription(int position){

        switch (position){
            case AppConstants.BOT_NAV_POSITION_SETTINGS:
                return AppConstants.BOT_NAV_ITEM_SETTINGS;
            case AppConstants.BOT_NAV_POSITION_STATISTICS:
                return AppConstants.BOT_NAV_ITEM_STATISTICS;
            case AppConstants.BOT_NAV_POSITION_MAIN:
                return AppConstants.BOT_NAV_ITEM_MAIN;
            case AppConstants.BOT_NAV_POSITION_FORTH:
                return AppConstants.BOT_NAV_ITEM_FORTH;
            case AppConstants.BOT_NAV_POSITION_REMINDERS:
                return AppConstants.BOT_NAV_ITEM_REMINDERS;
        }
        throw new IllegalArgumentException();
    }

    public int createPicture(int position){
        switch (position){
            case AppConstants.BOT_NAV_POSITION_SETTINGS:
                return R.mipmap.ic_settings;
            case AppConstants.BOT_NAV_POSITION_STATISTICS:
                return R.mipmap.ic_statistics;
            case AppConstants.BOT_NAV_POSITION_MAIN:
                return R.mipmap.ic_water;
            case AppConstants.BOT_NAV_POSITION_FORTH:
                return R.mipmap.ic_launcher;
            case AppConstants.BOT_NAV_POSITION_REMINDERS:
                return R.mipmap.ic_reminders;
        }
        throw new IllegalArgumentException();
    }
}
