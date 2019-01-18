package mo.edu.ipm.siweb.data.viewmodel;

import android.app.Application;

import org.threeten.bp.LocalDate;
import org.threeten.bp.LocalTime;
import org.threeten.bp.temporal.TemporalField;
import org.threeten.bp.temporal.WeekFields;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import mo.edu.ipm.siweb.data.model.ClassTime;
import mo.edu.ipm.siweb.data.model.Event;
import mo.edu.ipm.siweb.data.repository.ClassTimeRepository;

public class MainViewModel extends AndroidViewModel {

    private final ClassTimeRepository mClassTimeRepository;
    private MutableLiveData<List<Event>> mClassList = new MutableLiveData<>();

    public MainViewModel(@NonNull Application application) {
        super(application);
        mClassTimeRepository = ClassTimeRepository.getInstance(application);
    }

    public LiveData<List<Event>> getOnGoingClass() {
        LiveData<List<Event>> classTimeList = Transformations.switchMap(mClassTimeRepository.getClassTime(), list -> {
            List<Event> classTimes = new ArrayList<>();
            for (ClassTime classTime : list) {
                // class already ended or not started
                if (classTime.getEndedAt().isBefore(LocalDate.now()) || classTime.getStartedAt().isAfter(LocalDate.now())) {
                    continue;
                }

                // class in today ends
                if (classTime.getEndTime().isBefore(LocalTime.now())) {
                    continue;
                }


                TemporalField fieldISO = WeekFields.of(Locale.FRANCE).dayOfWeek();
                if (!classTime.getOnWeekDays()[LocalDate.now().get(fieldISO) - 1])
                    continue;

                classTimes.add(new Event(classTime));
            }

            Collections.sort(classTimes, (t1, t2) -> {
                if (t1.getStartTime().isBefore(t2.getStartTime()))
                    return -1;
                return 1;
            });

            mClassList.postValue(classTimes);
            return mClassList;
        });

        return classTimeList;
    }
}
