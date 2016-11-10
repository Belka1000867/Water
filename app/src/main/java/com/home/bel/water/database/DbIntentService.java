package com.home.bel.water.database;

import android.app.IntentService;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.os.ResultReceiver;
import android.util.Log;

import com.home.bel.water.utils.AppConstants;

import java.util.Date;

/**
 *  Service to work with the database.
 */
public class DbIntentService extends IntentService {

    private Intent mIntent;
    private DbAdapter db;

    private Bundle data = null;

    private static final String TAG = "Debug_DBIntentService";

    public DbIntentService() {
        super(DbIntentService.class.getSimpleName());
        Log.d(TAG, "Start thread : " + Thread.currentThread().getName());
        db = new DbAdapter(this);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d(TAG, "onHandleIntent()");

        int resultCode;
        mIntent = intent;

//      Open connection with DB
        Log.d(TAG, "Opening database..");
        db.open();

//      Proceed request to the database
        resultCode = proceedRequest(mIntent.getAction());

//      Close connection with DB
        Log.d(TAG, "Closing database..");
        db.close();

//      Sending result back to an Activity
        Log.d(TAG, "Sending result back to an activity..");
        sendResult(resultCode, data);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "Finish thread : " + Thread.currentThread().getName());
    }

    private int proceedRequest(String action){
        switch (action){
            case AppConstants.ACTION_IS_CURRENT_DAY:
                return isCurrentDay();
            case AppConstants.ACTION_GET_DAY_DATA :
                return getCurrentDayData();
            case AppConstants.ACTION_GET_WEEK_DATA :
                return getCurrentWeekData();
        }
        throw new IllegalArgumentException();
    }

    /* Insert day row into the database */
    private int insertDayData(){
        Log.d(TAG, "Inserting data for the day..");

        Bundle data = mIntent.getExtras();
        double amount = data.getDouble(AppConstants.EXTRAS_DB_AMOUNT);
        double drunk = data.getDouble(AppConstants.EXTRAS_DB_DRUNK);

        db.insertRow(new Date(), amount, drunk);

        return AppConstants.RESULT_INSERT_CURRENT_DAY;
    }

    /* Update data in the row of database */
    private int updateDayData(){
        Log.d(TAG, "Updating data for the day..");
        Bundle dayData = mIntent.getExtras();
        double amount = dayData.getDouble(AppConstants.EXTRAS_DB_AMOUNT);
        double drunk = dayData.getDouble(AppConstants.EXTRAS_DB_DRUNK);

        if(db.updateRow(new Date(), amount, drunk))
            Log.d(TAG, "updated row " + drunk);

        return AppConstants.RESULT_UPDATE_CURRENT_DAY;
    }

    /* Check if this day is in the database and than need to update a current raw or insert a new row */
    private int isCurrentDay(){
        Log.d(TAG, "Checking if this day already in the database..");

        if(db.isCurrentDay())
            return updateDayData();
        else
            return insertDayData();
    }

    /* Get the data for the current day */
    private int getCurrentDayData(){
        Log.d(TAG, "Getting data for the day..");

        Cursor row = db.getRow();
        Bundle dayData = new Bundle();

        if(row.moveToFirst()){
            dayData.putString(AppConstants.EXTRAS_DB_DATE, row.getString(0));
            dayData.putLong(AppConstants.EXTRAS_DB_AMOUNT, row.getLong(1));
            dayData.putDouble(AppConstants.EXTRAS_DB_DRUNK, row.getDouble(2));
        }

        data = dayData;
        return AppConstants.RESULT_CURRENT_DAY;
    }

    /* Get the data for the current week */
    private int getCurrentWeekData(){
        Log.d(TAG, "Getting data for the week..");

        Cursor rows = db.getCurWeekRows();

        int rowsCount = rows.getCount();
        Bundle weekData = new Bundle();

        int count = 0;
        weekData.putInt(AppConstants.EXTRAS_DB_ROWS_COUNT, rowsCount);

        if(rows.moveToFirst()){
            do {
                weekData.putString(AppConstants.EXTRAS_DB_DATE + count, rows.getString(0));
                weekData.putLong(AppConstants.EXTRAS_DB_AMOUNT + count, rows.getLong(1));
                weekData.putDouble(AppConstants.EXTRAS_DB_DRUNK + count, rows.getDouble(2));

                Log.d(TAG, "ROW [" + count + "]  date : " + rows.getString(0) + " amount : " + rows.getLong(1) + " drink : "+ rows.getDouble(2));

                count++;
            }
            while(rows.moveToNext());
        }

        data = weekData;
        return AppConstants.RESULT_CURRENT_WEEK;
    }

    private void sendResult(int resultCode, Bundle data){
        ResultReceiver resultReceiver = mIntent.getParcelableExtra(AppConstants.EXTRAS_INTENT_RESULT);
        if(resultReceiver != null) {
            resultReceiver.send(resultCode, data);
        }
    }


}
