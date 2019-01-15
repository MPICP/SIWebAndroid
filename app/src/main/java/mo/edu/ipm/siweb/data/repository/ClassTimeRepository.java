package mo.edu.ipm.siweb.data.repository;

import android.app.Application;

import java.util.List;

import androidx.lifecycle.LiveData;
import mo.edu.ipm.siweb.data.HttpService;
import mo.edu.ipm.siweb.data.SIWebService;
import mo.edu.ipm.siweb.data.database.SIWebRoomDatabase;
import mo.edu.ipm.siweb.data.database.dao.ClassTimeDao;
import mo.edu.ipm.siweb.data.model.ClassTime;
import mo.edu.ipm.siweb.data.model.ClassesTime;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClassTimeRepository {

    private final SIWebService mSIWebService;
    private final ClassTimeDao mClassTimeDao;
    private static ClassTimeRepository instance;

    public ClassTimeRepository (Application application) {
        SIWebRoomDatabase db = SIWebRoomDatabase.getDatabase(application);
        this.mSIWebService = HttpService.SIWEB();
        mClassTimeDao = db.mClassTimeDao();
    }

    public static ClassTimeRepository getInstance(Application application) {
        if (instance == null)
            instance = new ClassTimeRepository(application);
        return instance;
    }

    public LiveData<List<ClassTime>> getClassTime() {
        refreshClassTime();
        return mClassTimeDao.getClassTime();
    }

    private void refreshClassTime() {
        mSIWebService.getClassesTime().enqueue(new Callback<ClassesTime>() {
            @Override
            public void onResponse(Call<ClassesTime> call, Response<ClassesTime> response) {
                new Thread(() -> mClassTimeDao.insertMultipleListRecord(response.body().getClassTimeList())).start();
            }

            @Override
            public void onFailure(Call<ClassesTime> call, Throwable t) {

            }
        });
    }


}
