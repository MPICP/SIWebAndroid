package mo.edu.ipm.siweb.data.model;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.sysdata.widget.accordion.Item;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.HttpStatusException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import mo.edu.ipm.siweb.data.remote.JsonDataAdapter;
import mo.edu.ipm.siweb.exception.NotAuthorizedException;
import mo.edu.ipm.siweb.util.CredentialUtil;

public class GradesAndAbsence extends ViewModel {

    private MutableLiveData<JSONArray> mYears;
    private MutableLiveData<List<GradeAndAbsence>> mGradeAndAbsences;
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

    public class GradeAndAbsence extends Item {
        private String lastEntryDate;
        private String raRate;
        private String absenceRate;
        private String cod;
        private String absenceHour;
        private String totalHour;
        private String finalGrade;
        private String finalMark;
        private String examMark;
        private String assessmentMark;
        private String year;
        private String sem;
        private String subjectCode;
        private String sectionCode;
        private String subjectTitle;

        public GradeAndAbsence(JSONObject object) {
            try {
                this.year = object.getString("year");
                this.sem = object.getString("sem");
                this.subjectCode = object.getString("subjectCode");
                this.sectionCode = object.getString("sectionCode");
                this.subjectTitle = object.getString("subjectTitle");
                this.assessmentMark = object.getString("assessmentMark");
                this.examMark = object.getString("examMark");
                this.finalMark = object.getString("finalMark");
                this.finalGrade = object.getString("finalGrade");
                this.totalHour = object.getString("totalHour");
                this.absenceHour = object.getString("absenceHour");
                this.cod = object.getString("cod");
                this.absenceRate = object.getString("absenceRate");
                this.raRate = object.getString("raRate");
                this.lastEntryDate = object.getString("lastEntryDate");
            } catch (JSONException e) {

            }
        }

        @Override
        public int getUniqueId() {
            return (this.year + this.sem + this.subjectCode).hashCode();
        }

        public String getTitle() {
            return this.subjectTitle;
        }

        public String getDescription() {
            return this.year + '-' + this.sem + " " + this.subjectCode + '-' + this.sectionCode;
        }

        public String getInfo() {
            if (!this.finalGrade.equals("I")) {
                return String.format("CA: %s, Exam: %s, Final: %s (%s)",
                        this.assessmentMark, this.examMark, this.finalGrade, this.finalMark);
            } else {
                return String.format("Total: %sh, Absence: %sh (%s), RA: %s",
                        this.totalHour, this.absenceHour, this.absenceRate, this.raRate);
            }
        }

        public String getLastEntryDate() {
            if (!this.lastEntryDate.isEmpty()) {
                return "Last entry: " + this.lastEntryDate;
            }
            return null;
        }

        public String getCod() {
            return this.cod;
        }

        public String getSemYear() {
            return this.year;
        }

        public boolean isFinished() {
            return !this.finalGrade.equals("I");
        }
    }

    public LiveData<JSONArray> getYears() {
        if (mYears == null) {
            mYears = new MutableLiveData<>();
            retrieveAcademicYears();
        }
        return mYears;
    }

    public LiveData<List<GradeAndAbsence>> getGradeAndAbsence(String year) {
        if (mGradeAndAbsences == null) {
            mGradeAndAbsences = new MutableLiveData<>();
            retrieveGradesAndAbsence(year);
        }
        return mGradeAndAbsences;
    }

    public LiveData<List<AttendenceRecord>> getAttendenceHistory(String yearSem, String cod) {
        if (mAttendenceHistory == null) {
            mAttendenceHistory = new MutableLiveData<>();
            retrieveAttendenceHistory(yearSem, cod);
        }
        return mAttendenceHistory;
    }

    private void retrieveAttendenceHistory(String yearSem, String cod) {
        new Thread(() -> {
            try {
                List<AttendenceRecord> ar = new ArrayList<>();
                JSONArray data = JsonDataAdapter.getInstance().getAttendenceHistory(yearSem, cod);
                for (int i = 0; i != data.length(); ++i)
                    ar.add(new AttendenceRecord(data.getJSONObject(i)));
                mAttendenceHistory.postValue(ar);
            } catch (JSONException e) {
            } catch (IOException ioe) {
            } catch (NotAuthorizedException nae) {
                CredentialUtil.toggleAuthorizeState();
            }
        }).start();
    }

    public void retrieveGradesAndAbsence(String year) {
        new Thread(() -> {
            try {
                List<GradeAndAbsence> gae = new ArrayList<>();
                JSONArray data = JsonDataAdapter.getInstance().getGradesAndAbsence(year);
                for (int i = 0; i != data.length(); ++i) {
                    gae.add(new GradeAndAbsence(data.getJSONObject(i)));
                }
                mGradeAndAbsences.postValue(gae);
            } catch (IOException e) {

            } catch (JSONException je) {

            } catch (NotAuthorizedException nae) {
                CredentialUtil.toggleAuthorizeState();
            }
        }).start();
    }

    public void retrieveAcademicYears() {
        new Thread(() -> {
            try {
                mYears.postValue(JsonDataAdapter.getInstance().getAcademicYears());
            } catch (NotAuthorizedException nae) {
                CredentialUtil.toggleAuthorizeState();
            } catch (IOException e) {
                // handle io exception
            }
        }).start();
    }
}
