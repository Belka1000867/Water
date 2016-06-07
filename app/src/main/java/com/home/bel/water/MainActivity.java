package com.home.bel.water;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.FrameLayout;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.home.bel.water.ui.MainFragment_;
import com.home.bel.water.ui.RemindersFragment_;
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
 * Activity implements Listeners to connect two fragments
 */
@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity implements AHBottomNavigation.OnTabSelectedListener, MainFragment_.MainFragmentListener{

    final static String TAG = "Debug_MainActivity";

    @ViewById(R.id.framelayout_main)
    FrameLayout mFrameLayout;

    @ViewById(R.id.viewpager_main)
    ViewPager mViewPager;

    @ViewById(R.id.bottom_navigation)
    AHBottomNavigation mBottomNavigation;

    @AfterViews
    void afterViews(){
//  Create Bottom Navigation Menu
        createBottomNavigationMenu();

        getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack(null)
                .add(R.id.framelayout_main, new MainFragment_(), "Main")
                .commit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

//  Listener for Bottom Navigation Menu
    @Override
    public void onTabSelected(int position, boolean wasSelected) {
        Log.d(TAG, "Position " + position + " was selected" + wasSelected);
        if(!wasSelected) {
            getSupportFragmentManager().beginTransaction()
                .replace(R.id.viewpager_main, getFragment(position))
                .addToBackStack(null)
                .commit();

        }
    }

    private void createBottomNavigationMenu(){
//  Create bottom navigation items
        AHBottomNavigationItem item1 = new AHBottomNavigationItem(AppConstants.BOT_NAV_ITEM_SETTINGS, R.mipmap.ic_settings);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem(AppConstants.BOT_NAV_ITEM_STATISTICS, R.mipmap.ic_statistics);
        AHBottomNavigationItem item3 = new AHBottomNavigationItem(AppConstants.BOT_NAV_ITEM_MAIN, R.mipmap.ic_water);
        AHBottomNavigationItem item4 = new AHBottomNavigationItem(AppConstants.BOT_NAV_ITEM_FORTH, R.mipmap.ic_launcher);
        AHBottomNavigationItem item5 = new AHBottomNavigationItem(AppConstants.BOT_NAV_ITEM_REMINDERS, R.mipmap.ic_reminders);

//  Add bottom navigation items
        mBottomNavigation.addItem(item1);
        mBottomNavigation.addItem(item2);
        mBottomNavigation.addItem(item3);
        mBottomNavigation.addItem(item4);
        mBottomNavigation.addItem(item5);

//  Set current item programmatically
        mBottomNavigation.setCurrentItem(2, true);

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

    private Fragment getFragment(int position){
        Fragment fragment;
        switch (position) {
            case AppConstants.BOT_NAV_POSITION_SETTINGS:
                fragment = new SettingsFragment_();
                fragmentTransaction.replace(R.id.framelayout_main, new SettingsFragment_(), AppConstants.BOT_NAV_ITEM_SETTINGS);
                break;
            case AppConstants.BOT_NAV_POSITION_STATISTICS:
                fragmentTransaction.replace(R.id.framelayout_main, new StatisticsFragment_(), AppConstants.BOT_NAV_ITEM_SETTINGS);
                break;
            case AppConstants.BOT_NAV_POSITION_MAIN:
                fragmentTransaction.replace(R.id.framelayout_main, new MainFragment_(), AppConstants.BOT_NAV_ITEM_SETTINGS);
                break;
            case AppConstants.BOT_NAV_POSITION_FORTH:

                break;
            case AppConstants.BOT_NAV_POSITION_REMINDERS:
                fragmentTransaction.replace(R.id.framelayout_main, new RemindersFragment_(), AppConstants.BOT_NAV_ITEM_SETTINGS);
                break;
            default:
                Log.d(TAG, AppConstants.ERROR + "There is no action for such position!");
                break;
        }
        return fragment;
    }

/*
*    MainFragmentListener functions
* */
    @Override
    public void showAmountOfGlassesLeft(int glassesLeft) {
        mBottomNavigation.setNotification(String.valueOf(glassesLeft), AppConstants.BOT_NAV_POSITION_MAIN);
    }

    /* Pager for changing the fragment on the main part of the screen */

    private class MyFragmentPagerAdapter extends FragmentPagerAdapter {

        public MyFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
            Log.d(TAG, "MyFragmentPagerAdapter()");
        }

        @Override
        public Fragment getItem(int position) {
            Log.d(TAG, "getItem(), position = " + position);
            return getFragment(position);
        }

        @Override
        public int getCount() {
            return AppConstants.PAGE_COUNT;
        }

    }
}
