package mo.edu.ipm.siweb.data.viewmodel;

import android.app.Application;

import com.dexafree.materialList.card.Card;

import org.threeten.bp.LocalDate;
import org.threeten.bp.LocalTime;
import org.threeten.bp.temporal.TemporalField;
import org.threeten.bp.temporal.WeekFields;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import mo.edu.ipm.siweb.data.model.ClassTime;
import mo.edu.ipm.siweb.data.repository.ClassTimeRepository;

public class MainViewModel extends AndroidViewModel {

    private final ClassTimeRepository mClassTimeRepository;
    private MutableLiveData<List<ClassTime>> mClassList = new MutableLiveData<>();

    public MainViewModel(@NonNull Application application) {
        super(application);
        mClassTimeRepository = ClassTimeRepository.getInstance(application);
    }

    public LiveData<List<ClassTime>> getOnGoingClass() {
        LiveData<List<ClassTime>> classTimeList = Transformations.switchMap(mClassTimeRepository.getClassTime(), list -> {
            List<ClassTime> classTimes = new ArrayList<>();
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

                classTimes.add(classTime);
            }

            mClassList.postValue(classTimes);
            return mClassList;
        });

        return classTimeList;
    }
}
