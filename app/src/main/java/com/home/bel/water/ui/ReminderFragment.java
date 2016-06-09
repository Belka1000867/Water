package com.home.bel.water.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.ListView;

import com.home.bel.water.R;
import com.home.bel.water.utils.ReminderListAdapter;
import com.home.bel.water.utils.ScheduleConstants;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

/**
 * Created by bel on 04.06.16.
 */
@EFragment(R.layout.fragment_reminders)
public class ReminderFragment extends Fragment {

    final static String TAG = "Debug_RemindersFragment";

    private int times[];
    private Bundle events;
    private final static int TIMES_LENGTH = 34;

    @ViewById(R.id.listview_statistics)
    ListView mListView;

    @AfterViews
    void afterViews(){
        createSchedule();
        ReminderListAdapter reminderListAdapter = new ReminderListAdapter(times, events);
        mListView.setAdapter(reminderListAdapter);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d(TAG, "onAttach()");
    }

    private void createSchedule(){
        times = new int[TIMES_LENGTH];

        for(int i = 0; i < times.length; i++){
            times[i] = 420 + i * 30;
        }

        events = new Bundle();
        events.putInt(ScheduleConstants.GET_UP, 0);
        events.putInt(ScheduleConstants.GO_TO_SLEEP, TIMES_LENGTH - 2 );
        events.putInt(ScheduleConstants.BREAKFAST, 10);
        events.putInt(ScheduleConstants.LUNCH, 17);
        events.putInt(ScheduleConstants.DINNER, 24);

    }
}
