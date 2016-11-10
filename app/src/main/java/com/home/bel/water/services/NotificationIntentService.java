package com.home.bel.water.services;

import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;
import android.widget.RemoteViews;

import com.home.bel.water.MainActivity_;
import com.home.bel.water.R;

/**
 * Service for the notification, that evokes in separate
 * from UI working thread and execute requests sequentially.
 *
 * Also the operation running in IntentService cannot be interrupted.
 *
 * After IntentService finish task it release wake lock
 * for the device received from WakefulBroadcastReceiver.
 */
public class NotificationIntentService extends IntentService {

    /*
    * Notifications will be served into the array, because we need
    * to send different text notifications to the user.
    * */
    private static final int NOTIFICATION_ID = 1;

    private static final String TAG = "Debug_NotifyService";

    public NotificationIntentService() {
        super(NotificationIntentService.class.getSimpleName());
        Log.d(TAG, "NotificationIntentService() constructor");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d(TAG, "onHandleIntent()");

        /*
        * Main code for invoking a notification
        * */
        notifyUser();

        /*
        * When finished release the wake lock from WakefulBroadcastReceiver
        * */
        NotificationWakefulBroadcastReceiver.completeWakefulIntent(intent);
    }

    private void notifyUser(){

        Resources resources = getResources();

        String ticker = resources.getString(R.string.notification_ticker);
        String title = resources.getString(R.string.notification_title);
        String text = resources.getString(R.string.notification_text);

        RemoteViews remoteView = new RemoteViews(getPackageName(), R.layout.notification_remoteview);
        remoteView.setTextViewText(R.id.tv_notification_title, title);
        remoteView.setTextViewText(R.id.tv_notification_text, text);

        final Intent intent = new Intent(this, MainActivity_.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        final NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(getApplicationContext())
                        .setContent(remoteView)
                        .setTicker(ticker)
                        .setAutoCancel(true)
                        .setContentIntent(pendingIntent)
                        .setSmallIcon(R.mipmap.ic_water)
                        .setShowWhen(true)
                        .setUsesChronometer(true);

        final NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build());
    }

}
