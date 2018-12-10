package mo.edu.ipm.siweb.data.model;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import mo.edu.ipm.siweb.data.remote.JsonDataAdapter;
import mo.edu.ipm.siweb.exception.NotAuthorizedException;
import mo.edu.ipm.siweb.util.CredentialUtil;

public class ExamTimeViewModel extends ViewModel {

    private MutableLiveData<List<ExamTime>> mExamTime;

    public class ExamTime {
        private String date;
        private String time;
        private String classcode;
        private String title;
        private String venue;
        private String comment;

        public String getDate() {
            return date;
        }

        public String getTime() {
            return time;
        }

        public String getClasscode() {
            return classcode;
        }

        public String getTitle() {
            return title;
        }

        public String getVenue() {
            return venue;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public void setClasscode(String classcode) {
            this.classcode = classcode;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setVenue(String venue) {
            this.venue = venue;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        public String getComment() {
            return comment;
        }
    }

    public LiveData<List<ExamTime>> getExamTime() {
        if (mExamTime == null) {
            mExamTime = new MutableLiveData<>();
            loadProfile();
        }
        return mExamTime;
    }

    private void loadProfile() {
        new Thread(() -> {
            List<ExamTime> lst = new ArrayList<>();
            try {
                JSONArray exam = JsonDataAdapter.getInstance().getExamTime();
                for (int i = 0; i != exam.length(); ++i) {
                    JSONObject obj = exam.getJSONObject(i);
                    ExamTime examTime = new ExamTime();
                    examTime.setDate(obj.getString("date"));
                    examTime.setTime(obj.getString("time"));
                    examTime.setClasscode(obj.getString("classcode"));
                    examTime.setTitle(obj.getString("title"));
                    examTime.setVenue(obj.getString("venue"));
                    examTime.setComment(obj.getString("comment"));
                    lst.add(examTime);
                }
                mExamTime.postValue(lst);

            } catch (IOException ioe) {
            } catch (JSONException je) {
            } catch (NotAuthorizedException nae) {
                CredentialUtil.toggleAuthorizeState();
            }
        }).start();
    }
}
