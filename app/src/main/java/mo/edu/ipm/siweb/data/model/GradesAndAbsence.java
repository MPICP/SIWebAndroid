package mo.edu.ipm.siweb.data.model;

import android.arch.lifecycle.ViewModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.HttpStatusException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import mo.edu.ipm.siweb.data.remote.JsonDataAdapter;
import mo.edu.ipm.siweb.util.StartLoginActivityOnAuthFailed;

public class GradesAndAbsence extends ViewModel {

    private JSONArray mYears;
    private List<GradeAndAbsence> mGradeAndAbsences;
    private List<String> mGradeAndAbsenceYear;

    public class GradeAndAbsence {
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
            } catch (JSONException e) {

            }
        }
    }

    public void retrieveGradesAndAbsence(String year) {
        // JSONArray data = JsonDataAdapter.getInstance().getGradesAndAbsence(year);
    }

    public void retrieveAcademicYears() {
        try {
            mYears = JsonDataAdapter.getInstance().getAcademicYears();
        } catch (HttpStatusException hse) {
            StartLoginActivityOnAuthFailed.start(hse);
        } catch (IOException e) {
            // handle io exception
        }
    }
}
