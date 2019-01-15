package mo.edu.ipm.siweb.data.viewmodel;

import android.app.Application;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import mo.edu.ipm.siweb.data.HttpService;
import mo.edu.ipm.siweb.data.model.ExamTime;
import mo.edu.ipm.siweb.data.model.ExamsTime;
import mo.edu.ipm.siweb.data.repository.ExamTimeRepository;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ExamTimeViewModel extends AndroidViewModel {

    private LiveData<List<ExamTime>> mExamTime;
    private ExamTimeRepository mRepository;
    public boolean isLoading;

    public ExamTimeViewModel(@NonNull Application application) {
        super(application);
        mRepository = ExamTimeRepository.getInstance(application);
    }

    public LiveData<List<ExamTime>> getExamTime() {
        mExamTime = mRepository.getExamTime();
        return mExamTime;
    }
}
