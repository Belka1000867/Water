package com.home.bel.water;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.FrameLayout;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;

import com.home.bel.water.ui.ForthFragment_;
import com.home.bel.water.ui.MainFragment_;
import com.home.bel.water.ui.ReminderFragment_;
import com.home.bel.water.ui.SettingsFragment_;
import com.home.bel.water.ui.StatisticsFragment_;
import com.home.bel.water.utils.AppConstants;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

/**
 * Main activity that contain two fragments:
 * 1. FrameLayout for fragments
 * 2. AHBottomNavigation for the Bottom Navigation Menu
 *
 * Activity implements Listeners to connect two fragments.
 *
 */
@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity implements AHBottomNavigation.OnTabSelectedListener, MainFragment_.MainFragmentListener {

    final static String TAG = "Debug_MainActivity";

    @ViewById(R.id.framelayout_main)
    FrameLayout mFrameLayout;

    @ViewById(R.id.bottom_navigation)
    AHBottomNavigation mBottomNavigation;

    @AfterViews
    void afterViews(){

        final int defaultCurrentItem = AppConstants.BOT_NAV_POSITION_MAIN;

//  Create Bottom Navigation Menu with current Item
        createBottomNavigationMenu(defaultCurrentItem);

        getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack(null)
                .add(R.id.framelayout_main,
                        createFragment(defaultCurrentItem),
                        createItemDescription(defaultCurrentItem))
                .commit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        if(preferences.getBoolean(AppConstants.KEY_NOTIFICATION, true)){
//            Intent service = new Intent(NotificationService.class);
//            startService(service);
        }
    }

//  Listener for Bottom Navigation Menu
    @Override
    public void onTabSelected(int position, boolean wasSelected) {
        Log.d(TAG, "Position " + position + " was selected" + wasSelected);
        if(!wasSelected) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.framelayout_main,
                            createFragment(position),
                            createItemDescription(position)
                    )
                    .addToBackStack(null)
                    .commit();
        }
    }

    private void createBottomNavigationMenu(int defaultCurrentItem){

        for(int i = 0; i<5; i++){
            //  Create bottom navigation item
            AHBottomNavigationItem item =
                    new AHBottomNavigationItem(createItemDescription(i),
                                                createPicture(i));
            //  Add bottom navigation item
            mBottomNavigation.addItem(item);
        }

//  Set current item programmatically
        mBottomNavigation.setCurrentItem(defaultCurrentItem, true);

//  Set background color
        mBottomNavigation.setDefaultBackgroundColor(Color.parseColor("#56ABF8"));

//  Disable the translation inside the CoordinatorLayout
        mBottomNavigation.setBehaviorTranslationEnabled(false);

//  Change colors
        mBottomNavigation.setAccentColor(Color.parseColor("#FF844C"));
        mBottomNavigation.setInactiveColor(Color.parseColor("#FFD44C"));

//  Force to tint the drawable (useful for font with icon for example)
        //mBottomNavigation.setForceTint(true);

//  Use colored navigation with circle reveal effect
        //mBottomNavigation.setColored(true);


//  Customize notification (title, background, typeface)
        //mBottomNavigation.setNotificationBackgroundColor(Color.parseColor("#F63D2B"));

//  Add or remove notification for each item
        //mBottomNavigation.setNotification("4", 1);
        //mBottomNavigation.setNotification("", 1);

//  Set listener
        mBottomNavigation.setOnTabSelectedListener(this);
    }

    private Fragment createFragment(int position){
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

    private String createItemDescription(int position){
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

    private int createPicture(int position){
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



/* MainFragment_.MainFragmentListener functions */

    @Override
    public void showAmountOfGlassesLeft(int glassesLeft) {
        mBottomNavigation.setNotification(String.valueOf(glassesLeft), AppConstants.BOT_NAV_POSITION_MAIN);
    }

    @Override
    public void changeTab(int position) {
        mBottomNavigation.setCurrentItem(position);
        onTabSelected(position, false);
    }

    /* End of MainFragment_.MainFragmentListener functions */

}
