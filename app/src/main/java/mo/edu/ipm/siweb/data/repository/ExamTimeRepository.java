package mo.edu.ipm.siweb.data.repository;

import android.app.Application;

import java.util.List;

import androidx.lifecycle.LiveData;
import mo.edu.ipm.siweb.data.HttpService;
import mo.edu.ipm.siweb.data.SIWebService;
import mo.edu.ipm.siweb.data.database.SIWebRoomDatabase;
import mo.edu.ipm.siweb.data.database.dao.ExamTimeDao;
import mo.edu.ipm.siweb.data.model.ExamTime;
import mo.edu.ipm.siweb.data.model.ExamsTime;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ExamTimeRepository {

    private final SIWebService mSIWebService;
    private final ExamTimeDao mExamTimeDao;
    private static ExamTimeRepository instance;

    public ExamTimeRepository (Application application) {
        SIWebRoomDatabase db = SIWebRoomDatabase.getDatabase(application);
        this.mSIWebService = HttpService.SIWEB();
        mExamTimeDao = db.mExamTimeDao();
    }

    public static ExamTimeRepository getInstance(Application application) {
        if (instance == null)
            instance = new ExamTimeRepository(application);
        return instance;
    }

    public LiveData<List<ExamTime>> getExamTime() {
        refreshExamTime();
        return mExamTimeDao.getExamsTime();
    }

    private void refreshExamTime() {
            mSIWebService.getExamsTime().enqueue(new Callback<ExamsTime>() {
                @Override
                public void onResponse(Call<ExamsTime> call, Response<ExamsTime> response) {
                    new Thread(() -> mExamTimeDao.insertMultipleListRecord(response.body().getExamsTime())).start();
                }

                @Override
                public void onFailure(Call<ExamsTime> call, Throwable t) {

                }
            });
    }


}
