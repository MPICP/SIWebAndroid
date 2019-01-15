package mo.edu.ipm.siweb.data.viewmodel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import mo.edu.ipm.siweb.data.HttpService;
import mo.edu.ipm.siweb.data.model.AcademicYear;
import mo.edu.ipm.siweb.data.model.ClassTaken;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GradesAndAbsenceViewModel extends ViewModel {

    private MutableLiveData<AcademicYear> mYears;
    private MutableLiveData<ClassTaken> mGradeAndAbsences;
    private MutableLiveData<List<AttendenceRecord>> mAttendenceHistory;

    public class AttendenceRecord {
        private String date;
        private String time;
        private String hour;
        private String present;
        private String approvalResult;

        public AttendenceRecord(JSONObject object) throws JSONException {
            date = object.getString("date");
            time = object.getString("time");
            hour = object.getString("hour");
            present = object.getString("present");
            approvalResult = object.getString("approvalResult");
        }

        public String getDate() {
            return date;
        }

        public String getStatus() {
            return present + ' ' + approvalResult;
        }

        public String getDuration() {
            return time + " (" + hour + ')';
        }
    }

    public LiveData<AcademicYear> getYears() {
        if (mYears == null) {
            mYears = new MutableLiveData<>();
            getAcademicYears();
        }

        return mYears;
    }

    public LiveData<ClassTaken> getGradeAndAbsences(String year) {
        if (mGradeAndAbsences == null) {
            mGradeAndAbsences = new MutableLiveData<>();
            retrieveGradesAndAbsence(year);
        }
        return mGradeAndAbsences;
    }

    // TODO
    public LiveData<List<AttendenceRecord>> getAttendenceHistory(String yearSem, String cod) {
        if (mAttendenceHistory == null) {
            mAttendenceHistory = new MutableLiveData<>();
        }
        return mAttendenceHistory;
    }

    public void retrieveGradesAndAbsence(String year) {
        HttpService.SIWEB().getGradeAndAbsences(year).enqueue(new Callback<ClassTaken>() {
            @Override
            public void onResponse(Call<ClassTaken> call, Response<ClassTaken> response) {
                mGradeAndAbsences.postValue(response.body());
            }

            @Override
            public void onFailure(Call<ClassTaken> call, Throwable t) {
                mGradeAndAbsences.postValue(new ClassTaken());
            }
        });
    }

    public void getAcademicYears() {
        HttpService.SIWEB().getAcademicYear().enqueue(new Callback<AcademicYear>() {
            @Override
            public void onResponse(Call<AcademicYear> call, Response<AcademicYear> response) {
                mYears.postValue(response.body());
            }

            @Override
            public void onFailure(Call<AcademicYear> call, Throwable t) {
                mYears.postValue(new AcademicYear());
            }
        });
    }
}
