package com.home.bel.water.extra;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by bel on 19.09.16.
 */
public class RowDate extends Date {

    public RowDate(){
        super();
    }

    public String getWeekOfYear(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("ww", Locale.getDefault());
        return simpleDateFormat.format(this);
    }

    public String getDayOfWeek(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("u", Locale.getDefault());
        return simpleDateFormat.format(this);
    }

    public String toDbFormat(Date date){
        // Format date as - Number of week - day - month - year
        SimpleDateFormat dateFormat = new SimpleDateFormat("wwuddmmyy", Locale.getDefault());
        return dateFormat.format(date);
    }
}
