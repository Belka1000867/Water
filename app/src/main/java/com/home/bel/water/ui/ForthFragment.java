package com.home.bel.water.ui;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.home.bel.water.R;
import com.home.bel.water.utils.AppConstants;

import org.androidannotations.annotations.EFragment;

/**
 * Created by bel on 07.06.16.
 */
@EFragment(R.layout.fragment_forth)
public class ForthFragment extends Fragment {

    final static String TAG = "Debug_ForthFragment";

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d(TAG, "onAttach()");
    }

}
