package com.home.bel.water.ui;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.home.bel.water.R;

import org.androidannotations.annotations.EFragment;

/**
 * Created by bel on 04.06.16.
 */
@EFragment(R.layout.fragment_statistics)
public class StatisticsFragment extends Fragment {

    final static String TAG = "Debug_StatFragment";

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d(TAG, "onAttach()");
    }

}
