package com.home.bel.water.utils;

import android.widget.TextView;

/**
 * Created by bel on 26.06.16.
 */
public class AppConstantFunctions {

    public static void setText(TextView textView, double value){
        textView.setText(String.format("%.0f", value));
    }
}
