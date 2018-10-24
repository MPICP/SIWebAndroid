package mo.edu.ipm.cp.siweb;

import android.graphics.RectF;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.alamkanak.weekview.MonthLoader;
import com.alamkanak.weekview.WeekView;
import com.alamkanak.weekview.WeekViewEvent;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import mo.edu.ipm.cp.siweb.HTTP.HTTPRequest;

public class ClassTimeActivity extends AppCompatActivity {

    private static final String TAG = "ClassTimeActivity";
    private WeekView mWeekView;
    private List<WeekViewEvent> lwv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_time);

        // Get a reference for the week view in the layout.
        mWeekView = (WeekView) findViewById(R.id.weekView);
        mWeekView.setNumberOfVisibleDays(5);
        mWeekView.setHourHeight(30);

// Set an action when any event is clicked.
        mWeekView.setOnEventClickListener(new WeekView.EventClickListener() {
            @Override
            public void onEventClick(WeekViewEvent event, RectF eventRect) {

            }
        });

        mWeekView.setMonthChangeListener(new MonthLoader.MonthChangeListener() {
            @Override
            public List<WeekViewEvent> onMonthChange(int newYear, int newMonth) {
                // Populate the week view with some events.
                List<WeekViewEvent> events = getEvents();
                return events;
            }
        });

// Set long press listener for events.
        mWeekView.setEventLongPressListener(new WeekView.EventLongPressListener() {
            @Override
            public void onEventLongPress(WeekViewEvent event, RectF eventRect) {

            }
        });
    }

    private List<WeekViewEvent> getEvents() {

        Thread t = new Thread(new Runnable(){
            @Override
            public void run() {
                try {
                    //lwv = HTTPRequest.getInstance().getClassTime();
                } catch (Exception e) {
                    Log.e(TAG, e.toString(), e);
                    Log.e(TAG, "Get reference on null object");
                }
            }
        });

        t.start();

        try {
            t.join();
        } catch (Exception e) {
            return null;
        }

        return lwv;

    }
}
