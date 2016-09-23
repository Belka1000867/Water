package com.home.bel.water.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.annotation.WorkerThread;
import android.support.v4.app.Fragment;
import android.support.v4.os.ResultReceiver;
import android.util.Log;
import android.widget.TextView;

import com.home.bel.water.R;
import com.home.bel.water.services.DBIntentService;
import com.home.bel.water.utils.AppConstants;

import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

/**
 * Different possibilities to get data from the database.
 *
 * 1. To intent in different thread.
 */
@EFragment(R.layout.fragment_statistics)
public class StatisticsFragment extends Fragment {

//    private DbAdapter db;
    DbResultReceiver dbResultReceiver;

    final static String TAG = "Debug_StatFragment";


    class DbResultReceiver extends ResultReceiver {

        private static final String TAG = "Debug_DbResultReceiver";

        DbResultReceiver(Handler handler) {
            super(handler);
        }

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {
            Log.d(TAG, "Result code : " + resultCode);

            switch(resultCode){
                case AppConstants.RESULT_CURRENT_WEEK :
                    writeRows(resultData);
                    break;
            }


        }

    }

    @ViewById(R.id.tv_ex_stat)
    TextView tvExampleText;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d(TAG, "onAttach()");

        // Register receiver for get response from intent service
        dbResultReceiver = new DbResultReceiver(new Handler());

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "Starting service.. ");


        refreshStatistics();
    }


    private void refreshStatistics(){
        Log.d(TAG, "Refreshing statistics table .. ");
        Intent intent = new Intent(getContext(), DBIntentService.class);
        intent.setAction(AppConstants.ACTION_GET_WEEK_DATA);
        intent.putExtra(AppConstants.EXTRAS_INTENT_RESULT, dbResultReceiver);
        getActivity().startService(intent);
    }

    private void writeRows(Bundle data){

        Log.d(TAG, "Writing rows..");

        int rowCount = data.getInt(AppConstants.EXTRAS_DB_ROWS_COUNT);
        String text = "";

        Log.d(TAG, "amount of rows: " + rowCount);

        for(int i = 0; i < rowCount; i++){
            text += tvExampleText.getText() +
                    "Date : " + data.getString(AppConstants.EXTRAS_DB_DATE + i) +
                    "Amount : " + data.getLong(AppConstants.EXTRAS_DB_AMOUNT + i) +
                    "Drunk : " + data.getDouble(AppConstants.EXTRAS_DB_DRUNK + i) + "\n";

        }
        tvExampleText.setText(text);
    }



}
