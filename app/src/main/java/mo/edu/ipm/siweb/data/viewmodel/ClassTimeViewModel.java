package mo.edu.ipm.siweb.data.viewmodel;

import android.app.Application;
import android.util.Log;

import com.alamkanak.weekview.WeekViewEvent;

import org.threeten.bp.DateTimeUtils;
import org.threeten.bp.LocalDate;
import org.threeten.bp.LocalDateTime;
import org.threeten.bp.ZoneId;
import org.threeten.bp.ZonedDateTime;
import org.threeten.bp.temporal.TemporalField;
import org.threeten.bp.temporal.WeekFields;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import mo.edu.ipm.siweb.data.model.ClassTime;
import mo.edu.ipm.siweb.data.repository.ClassTimeRepository;

public class ClassTimeViewModel extends AndroidViewModel {

    private LiveData<List<ClassTime>> mClassTime;
    private ClassTimeRepository mRepository;
    public boolean isLoading;

    public static final String TAG = "ClassTimeViewModel";

    public ClassTimeViewModel(@NonNull Application application) {
        super(application);
        mRepository = ClassTimeRepository.getInstance(application);
    }

    public LiveData<List<ClassTime>> getClassTime() {
        mClassTime = mRepository.getClassTime();
        return mClassTime;
    }

    public List<WeekViewEvent> getClassTimeEvents(int newYear, int newMonth) {
        LocalDate thisMonth = LocalDate.of(newYear, newMonth, 1);
        if (mClassTime == null) {
            getClassTime();
            return null;
        } else {
            List<WeekViewEvent> events = new ArrayList<>();
            for (ClassTime classTime : mClassTime.getValue()) {
                Log.i(TAG, classTime.getClassCode() + classTime.getName() + classTime.getStartedAt() + classTime.getEndedAt());
            }
            for (ClassTime classTime : mClassTime.getValue()) {
                Log.d(TAG, classTime.getName() + Arrays.toString(classTime.getOnWeekDays()));
                for (LocalDate today : getAvailableWeeks(classTime, thisMonth)) {
                    events.add(getEvent(today, classTime));
                }
            }
            return events;
        }
    }

    public List<LocalDate> getAvailableWeeks(ClassTime classTime, LocalDate firstDayOfMonth) {
        // We get all start dates, we change the day to monday and we add one week to the minimum until we hit the maximum.
        final List<LocalDate> result = new ArrayList<>();
        LocalDate start = classTime.getStartedAt();
        LocalDate end = classTime.getEndedAt();
        LocalDate lastDayOfMonth = firstDayOfMonth.withDayOfMonth(firstDayOfMonth.lengthOfMonth());

        LocalDate min = firstDayOfMonth;
        LocalDate max = lastDayOfMonth;

        // end < firstDayOfMonth || start > lastDayOfMonth
        if (end.isBefore(firstDayOfMonth) || lastDayOfMonth.isBefore(start)) {
            return Collections.emptyList();
        }

        // start > firstDayOfMonth && start < lastDayOfMonth
        if (firstDayOfMonth.isBefore(start) && start.isBefore(lastDayOfMonth)) {
            min = start;
        }
        // end > firstDayOfMonth && end < lastDayOfMonth
        if (firstDayOfMonth.isBefore(end) && lastDayOfMonth.isBefore(end)) {
            max = end;
        }

        // Locale.CHINA is not in ISO format
        TemporalField fieldISO = WeekFields.of(Locale.FRANCE).dayOfWeek();

        boolean[] weekDays = classTime.getOnWeekDays();
        for (int day = 1; day != 8; ++day) {
            if (!weekDays[day - 1])
                continue;
            LocalDate thisMin = min.with(fieldISO, day);
            LocalDate thisMax = max.with(fieldISO, day);

            LocalDate current = thisMin;
            while (!current.isAfter(thisMax)) {
                result.add(current);
                current = current.plusWeeks(1);
            }
        }

        return result;
    }

    private WeekViewEvent getEvent(LocalDate today, ClassTime classTime) {
        WeekViewEvent weekViewEvent = new WeekViewEvent();
        weekViewEvent.setName(classTime.getName());
        weekViewEvent.setLocation(classTime.getLocation());

        ZonedDateTime startDateTime = LocalDateTime.of(today, classTime.getStartTime()).atZone(ZoneId.systemDefault());
        ZonedDateTime endDateTime = LocalDateTime.of(today, classTime.getEndTime()).atZone(ZoneId.systemDefault());

        weekViewEvent.setStartTime(DateTimeUtils.toGregorianCalendar(startDateTime));
        weekViewEvent.setEndTime(DateTimeUtils.toGregorianCalendar(endDateTime));

//        Log.i(TAG, weekViewEvent.getName() + ": " + weekViewEvent.getStartTime().getTime() + " - " + weekViewEvent.getEndTime().getTime());

        return weekViewEvent;
    }
}

