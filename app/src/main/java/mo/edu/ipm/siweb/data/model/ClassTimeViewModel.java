package mo.edu.ipm.siweb.data.model;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.alamkanak.weekview.WeekViewEvent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import mo.edu.ipm.siweb.data.remote.JsonDataAdapter;

public class ClassTimeViewModel extends ViewModel {
    public static final String TAG = "ClassTimeViewModel";
    private MutableLiveData<List<WeekViewEvent>> mEvents;
    private JSONArray mClassTime;
    private List<WeekViewEvent> mList = new ArrayList<>();
    private static int mId = 0;
    private boolean isLoading;

    private int prevYear;
    private int prevMonth;

    public List<WeekViewEvent> getEvents(int newYear, int newMonth) {
        if (prevYear == newYear && prevMonth == newMonth) {
            return mList;
        } else {
            mList = new ArrayList<>();
        }

        mEvents = new MutableLiveData<>();

        loadClassTime();
        try {
            for (int i = 0; i != mClassTime.length(); ++i)
                addClassTime(mClassTime.getJSONObject(i), newYear, newMonth);
        } catch (JSONException je) {
        }

        prevMonth = newMonth;
        prevYear = newYear;

        return mList;
    }

    private void loadClassTime() {
        Thread thread = new Thread(() -> {
            try {
                JSONArray classTime = JsonDataAdapter.getInstance().getClassTime();
                mClassTime = classTime;
            } catch (IOException ioe) {
            }
        });

        thread.start();
        try {
            thread.join();
        } catch (InterruptedException ie) {
        }
    }

    /**
     * add class time to list
     * <p>
     * {
     * "classCode":"COMP211-21221",
     * "subject":"DATABASE DESIGN",
     * "instructor":"CALANA CHAN MEI POU",
     * "room":"A202",
     * "period":"2018\/08\/27-2018\/09\/27",
     * "time":"10:00-11:30",
     * "week":[false,true,false,false,false,false,false]
     * },
     *
     * @param obj
     * @param newYear
     * @param newMonth
     */
    private void addClassTime(JSONObject obj, int newYear, int newMonth) throws JSONException {
        String period = obj.getString("period");
        String[] periodArr = period.split("-");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
        String title = obj.getString("subject");
        String[] time = obj.getString("time").split("-");
        String[] startAt = time[0].split(":");
        String[] endedAt = time[1].split(":");

        Date startDate = null;
        Date endDate = null;
        try {
            startDate = simpleDateFormat.parse(periodArr[0]);
            endDate = simpleDateFormat.parse(periodArr[1]);
        } catch (ParseException pe) {
            Log.e(TAG, pe.getMessage(), pe);
        }

        Calendar startCal = Calendar.getInstance();
        startCal.setFirstDayOfWeek(Calendar.SUNDAY);
        startCal.set(newYear, newMonth, 1);
        startCal.set(Calendar.MINUTE, 0);
        startCal.set(Calendar.SECOND, 0);
        startCal.set(Calendar.MILLISECOND, 0);

        int daysInMonth = startCal.getActualMaximum(Calendar.DAY_OF_MONTH);

        JSONArray week = obj.getJSONArray("week");

        for (int i = 0; i != week.length(); ++i) {
            if (!week.getBoolean(i)) continue;
            for (int j = 0; j != daysInMonth; ++j) {
                startCal.set(Calendar.DAY_OF_MONTH, j);

                if (startCal.getTime().compareTo(endDate) > 0) break;
                if (startCal.getTime().compareTo(startDate) < 0) continue;

                int dayOfWeek = startCal.get(Calendar.DAY_OF_WEEK);

                if (dayOfWeek == i + 1) {
                    // add to list
                    Calendar startTime = (Calendar) startCal.clone();
                    Calendar endTime = (Calendar) startTime.clone();
                    startTime.set(Calendar.HOUR_OF_DAY, Integer.parseInt(startAt[0]));
                    startTime.set(Calendar.MINUTE, Integer.parseInt(startAt[1]));

                    endTime.set(Calendar.HOUR_OF_DAY, Integer.parseInt(endedAt[0]));
                    endTime.set(Calendar.MINUTE, Integer.parseInt(endedAt[1]));
                    WeekViewEvent event = new WeekViewEvent(mId++, title, startTime, endTime);
                    event.setLocation(obj.getString("room"));
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm", Locale.CHINA);
                    Log.i(TAG, event.getName() + " " +
                            sdf.format(event.getStartTime().getTime()) + " ~ " +
                            sdf.format(event.getEndTime().getTime()));
                    mList.add(event);
                }

            }
        }
    }
}
