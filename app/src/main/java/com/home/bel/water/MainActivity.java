package com.home.bel.water;

import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
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
import com.home.bel.water.ui.MainFragment;
import com.home.bel.water.ui.MainFragment_;
import com.home.bel.water.ui.ReminderFragment_;
import com.home.bel.water.ui.SettingsFragment_;
import com.home.bel.water.ui.StatisticsFragment_;
import com.home.bel.water.utils.AppConstants;
import com.home.bel.water.utils.AppData;
import com.home.bel.water.database.DbAdapter.DbHelper;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import static com.home.bel.water.utils.AppConstants.BOT_NAV_ITEM_FORTH;
import static com.home.bel.water.utils.AppConstants.BOT_NAV_ITEM_MAIN;
import static com.home.bel.water.utils.AppConstants.BOT_NAV_ITEM_REMINDERS;
import static com.home.bel.water.utils.AppConstants.BOT_NAV_ITEM_SETTINGS;
import static com.home.bel.water.utils.AppConstants.BOT_NAV_ITEM_STATISTICS;
import static com.home.bel.water.utils.AppConstants.BOT_NAV_POSITION_FORTH;
import static com.home.bel.water.utils.AppConstants.BOT_NAV_POSITION_MAIN;
import static com.home.bel.water.utils.AppConstants.BOT_NAV_POSITION_REMINDERS;
import static com.home.bel.water.utils.AppConstants.BOT_NAV_POSITION_SETTINGS;
import static com.home.bel.water.utils.AppConstants.BOT_NAV_POSITION_STATISTICS;

/**
 * Main activity that contain two fragments:
 * 1. FrameLayout for fragments
 * 2. AHBottomNavigation for the Bottom Navigation Menu
 *
 * Activity implements Listeners to connect two fragments.
 *
 */
@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity implements AHBottomNavigation.OnTabSelectedListener, MainFragment.MainFragmentListener {

    final static String TAG = "Debug_MainActivity";
    final int FRAME_LAYOUT = R.id.framelayout_main;

    @ViewById(R.id.framelayout_main)
    FrameLayout mFrameLayout;

    @ViewById(R.id.bottom_navigation)
    AHBottomNavigation mBottomNavigation;

    @AfterViews
    void afterViews(){

        final int defaultCurrentItem = BOT_NAV_POSITION_MAIN;

//  Create Bottom Navigation Menu with current Item
        createBottomNavigationMenu(defaultCurrentItem);

        getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack(null)
                .add(FRAME_LAYOUT,
                        createFragment(defaultCurrentItem),
                        createItemDescription(defaultCurrentItem))
                .commit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate()");

        AppData appData = AppData.getInstance(this);

        if(appData.isFirstLaunch()){
            // Create SQL table
            DbHelper dbHelper = new DbHelper(this);
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            Log.d(TAG, "Database have been created on the path : " + db.getPath());

        }

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
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(FRAME_LAYOUT,
                            createFragment(position),
                            createItemDescription(position))
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
            case BOT_NAV_POSITION_SETTINGS:
                return new SettingsFragment_();
            case BOT_NAV_POSITION_STATISTICS:
                return new StatisticsFragment_();
            case BOT_NAV_POSITION_MAIN:
                return new MainFragment_();
            case BOT_NAV_POSITION_FORTH:
                return new ForthFragment_();
            case BOT_NAV_POSITION_REMINDERS:
                return new ReminderFragment_();
        }
        throw new IllegalArgumentException();
    }

    private String createItemDescription(int position){
        switch (position){
            case BOT_NAV_POSITION_SETTINGS:
                return BOT_NAV_ITEM_SETTINGS;
            case BOT_NAV_POSITION_STATISTICS:
                return BOT_NAV_ITEM_STATISTICS;
            case BOT_NAV_POSITION_MAIN:
                return BOT_NAV_ITEM_MAIN;
            case BOT_NAV_POSITION_FORTH:
                return BOT_NAV_ITEM_FORTH;
            case BOT_NAV_POSITION_REMINDERS:
                return BOT_NAV_ITEM_REMINDERS;
        }
        throw new IllegalArgumentException();
    }

    private int createPicture(int position){
        switch (position){
            case BOT_NAV_POSITION_SETTINGS:
                return R.mipmap.ic_settings;
            case BOT_NAV_POSITION_STATISTICS:
                return R.mipmap.ic_statistics;
            case BOT_NAV_POSITION_MAIN:
                return R.mipmap.ic_water;
            case BOT_NAV_POSITION_FORTH:
                return R.mipmap.ic_launcher;
            case BOT_NAV_POSITION_REMINDERS:
                return R.mipmap.ic_reminders;
        }
        throw new IllegalArgumentException();
    }



/* MainFragment_.MainFragmentListener functions */

    @Override
    public void showAmountOfGlassesLeft(int glassesLeft) {
//        final String title = glassesLeft == 0 ? null : String.valueOf(glassesLeft);
        mBottomNavigation.setNotification(String.valueOf(glassesLeft), BOT_NAV_POSITION_MAIN);
    }

    @Override
    public void changeTab(int position) {
        mBottomNavigation.setCurrentItem(position);
        onTabSelected(position, false);
    }

    public void showAlarm(int alarmId){

        int messageId;

        AlertDialog.Builder mAlertBuilder = new AlertDialog.Builder(this);
        mAlertBuilder.setTitle(alarmId);
        mAlertBuilder.setCancelable(false);

        switch (alarmId){
            case R.string.error_settings_empty_weight :
                messageId = R.string.dialog_message_empty_weight;

                mAlertBuilder
                        .setPositiveButton(R.string.dialog_button_positive, (dialog, which) -> {
                            dialog.dismiss();
                        });
                break;
            default:
                Log.d(TAG, AppConstants.ERROR + "No such string for Alert Dialog!");
                return;
        }

        String message = getResources().getString(messageId);

        // Set message, create dialog and show it to the user
        mAlertBuilder
                .setMessage(message)
                .create()
                .show();
    }

    /* End of MainFragment_.MainFragmentListener functions */

}
