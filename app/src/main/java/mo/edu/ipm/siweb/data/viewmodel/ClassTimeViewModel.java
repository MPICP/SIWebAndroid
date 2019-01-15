package mo.edu.ipm.siweb.data.viewmodel;

import android.app.Application;

import com.alamkanak.weekview.WeekViewEvent;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import mo.edu.ipm.siweb.data.model.ClassTime;
import mo.edu.ipm.siweb.data.repository.ClassTimeRepository;

public class ClassTimeViewModel extends AndroidViewModel {

    private LiveData<List<ClassTime>> mClassTime;
    private ClassTimeRepository mRepository;
    public boolean isLoading;

    public ClassTimeViewModel(@NonNull Application application) {
        super(application);
        mRepository = ClassTimeRepository.getInstance(application);
    }

    public LiveData<List<ClassTime>> getClassTime() {
        mClassTime = mRepository.getClassTime();
        return mClassTime;
    }

    public List<WeekViewEvent> getClassTimeEvents(int newYear, int newMonth) {
        List<WeekViewEvent> events = new ArrayList<>();
        if (mClassTime == null) {
            getClassTime();
        } else {
            for (ClassTime classTime : mClassTime.getValue()) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(newYear, newMonth, 1);                                       // set to first day of month
                boolean[] weekDays = classTime.getOnWeekDays();                                 // get weekdays in event
                for (int i = 0; i != weekDays.length; ++i) {                                    // generate events for different weekday
                    if (!weekDays[i]) continue;
                    calendar.set(Calendar.DAY_OF_WEEK, i);                                      // first occurrence of this time
                    while (calendar.get(Calendar.MONTH) <= newMonth) {                          // while still in this month
                        events.add(getEvent(calendar, classTime));
                        calendar.add(Calendar.DAY_OF_MONTH, 7);
                    }
                    calendar.set(newYear, newMonth, 1);                                   // reset calendar
                }
            }
        }
        return events;
    }

    private WeekViewEvent getEvent(Calendar calendar, ClassTime classTime) {
        WeekViewEvent weekViewEvent = new WeekViewEvent();
        weekViewEvent.setName(classTime.getName());
        weekViewEvent.setLocation(classTime.getLocation());

        Calendar startTime = Calendar.getInstance();
        Date startedAt = classTime.getStartedAt();
        startTime.setTime(startedAt);
        startTime.set(Calendar.YEAR, calendar.get(Calendar.YEAR));
        startTime.set(Calendar.MONTH, calendar.get(Calendar.MONTH));
        startTime.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH));

        weekViewEvent.setStartTime(startTime);

        Calendar endTime = calendar.getInstance();
        endTime.setTime(classTime.getEndedAt());
        endTime.set(Calendar.YEAR, calendar.get(Calendar.YEAR));
        endTime.set(Calendar.MONTH, calendar.get(Calendar.MONTH));
        endTime.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH));

        weekViewEvent.setEndTime(endTime);


        return weekViewEvent;
    }
}

