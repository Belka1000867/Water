package com.home.bel.water.ui;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.home.bel.water.R;
import com.home.bel.water.utils.AppConstants;

import org.androidannotations.annotations.EFragment;

/**
 * fragment with the addition of water
 */
@EFragment(R.layout.fragment_main)
public class MainFragment extends Fragment {

    final static String TAG = "Debug_MainFragment";

    MainFragmentListener mainFragmentListener;

    public interface MainFragmentListener{

        void showAmountOfGlassesLeft(int glassesLeft);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d(TAG, "onAttach()");
        try {
            mainFragmentListener = (MainFragmentListener) context;
        } catch (Exception e) {
            Log.d(TAG, AppConstants.ERROR + "mainFragmentListener " + e.getMessage());
        }

        if(mainFragmentListener != null){
            mainFragmentListener.showAmountOfGlassesLeft(7);
        }
    }

}
