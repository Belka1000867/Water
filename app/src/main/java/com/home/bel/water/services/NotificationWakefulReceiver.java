package com.home.bel.water.services;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;

/**
 * WakefulBroadcastReceiver make sure that application
 * will keep the device in wake state and give time to
 * evoke the service, before it will finish
 */
public class NotificationWakefulReceiver extends WakefulBroadcastReceiver {

    private static final String ACTION_START_INTENT = "ACTION_START_INTENT";

    public static void createAlarm(Context context){
        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        PendingIntent alarmIntent = getStartPendingIntent(context);
        alarmManager.set(AlarmManager.RTC_WAKEUP,
                        System.currentTimeMillis() + 5 * 1000,
                        alarmIntent);

    }

    public static PendingIntent getStartPendingIntent(Context context){
        Intent intent = new Intent(context, NotificationWakefulReceiver.class);
        intent.setAction(ACTION_START_INTENT);
        return PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }



    @Override
    public void onReceive(Context context, Intent intent) {
        Intent intentService = new Intent(context, NotificationIntentService.class);

        // Insert data inside the Intent

        startWakefulService(context, intentService);
    }

}
