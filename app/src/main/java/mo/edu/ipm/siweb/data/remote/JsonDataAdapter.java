package mo.edu.ipm.siweb.data.remote;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

import mo.edu.ipm.siweb.util.CredentialUtil;
import mo.edu.ipm.siweb.util.HttpRequest;

public class JsonDataAdapter {

    private static final String TAG = "JsonDataAdapter";
    private HttpRequest request;
    private static JsonDataAdapter instance;

    private JsonDataAdapter() {
        request = HttpRequest.getInstance();
    }

    public static JsonDataAdapter getInstance() {
        if (instance == null)
            instance = new JsonDataAdapter();
        return instance;
    }

    private boolean checkLoginStatus(Document body) {
        if (body.getElementsByTag("title").isEmpty())
            return true;

        String title = body.getElementsByTag("title").get(0).text();

        if (title.contains("401 Authorization Required")) {
            CredentialUtil.setUnauthorized();
            CredentialUtil.refreshCookie();
            return CredentialUtil.isAuthorized();
        }
        return true;
    }


    /**
     * Workaround for stud_info.asp
     *
     * @param body
     * @return
     */
    private boolean checkLoginStatusProfile(Document body) {
        Log.i(TAG, "bdy.cns: " + body.body().childNodeSize());

        if (body.body().childNodeSize() == 2) {
            CredentialUtil.setUnauthorized();
            CredentialUtil.refreshCookie();
            return CredentialUtil.isAuthorized();
        }

        return true;
    }

    public JSONObject login(String id, String password) throws IOException {
        try {
            if (id.isEmpty() || password.isEmpty()) {
                return new JSONObject().put("login", false);
            }

            Document body = request.login(id, password).parse();
            Element el = body.getElementsContainingText("Invalid NetID or Password").last();
            if (el != null) {
                return new JSONObject().put("login", false);
            } else {
                return new JSONObject().put("login", true);
            }
        } catch (JSONException je) {
            // no exception will ever throw, skipped
            return null;
        }
    }

    public JSONObject getProfile() throws IOException {
        try {
            Document body = request.getProfile().parse();
            if (!checkLoginStatusProfile(body)) return new JSONObject();
            body = request.getProfile().parse();

            Element table = body.getElementsByAttributeValue("width", "600").first();
            Elements tr = table.getElementsByTag("tr");
            JSONObject profile = new JSONObject();

            String[] fields = {"id", "name", "fname", "mname", "school", "programCode",
                    "programName", "section", "language", "mode", "major", "status",
                    "gpa", "obtainedCredits", "HKMOPermitNo"};

            for (int i = 0; i != fields.length; )
                profile.put(fields[i++], tr.get(i).getAllElements().get(4).text());

            Log.i(TAG, profile.toString());

            return profile;
        } catch (JSONException je) {
            return new JSONObject();
        }
    }

    public JSONArray getAcademicYears() throws IOException {
        Document body = request.getAcademicYears().parse();
        Element select = body.getElementsByTag("select").first();
        body = request.getAcademicYears().parse();

        if (!checkLoginStatus(body)) return new JSONArray();
        JSONArray years = new JSONArray();

        for (Element option : select.getElementsByAttributeValueNot("value", "SELECT"))
            if (option.attr("value") != "") years.put(option.attr("value"));

        return years;
    }

    public JSONArray getGradesAndAbsence(String year) throws IOException {
        try {
            Document body = request.getGradesAndAbsence(year).parse();
            if (!checkLoginStatus(body)) return new JSONArray();
            body = request.getGradesAndAbsence(year).parse();

            Element table = body.getElementById("result_table")
                    .getElementsByTag("tbody").first();
            Elements tableRows = table.getElementsByTag("tr");
            JSONArray gradesAndAbsences = new JSONArray();

            // removing redundant rows
            tableRows.remove(0);
            tableRows.remove(0);
            tableRows.remove(tableRows.last());

            String[] fields = {"year", "sem", "subjectCode", "sectionCode", "subjectTitle",
                    "assessmentMark", "examMark", "finalMark", "finalGrade", "totalHour", "absenceHour",
                    "cod", "absenceRate", "raRate", "lastEntryDate"};

            for (Element row : tableRows) {
                JSONObject gradeAndAbsence = new JSONObject();
                Elements tds = row.getElementsByTag("td");

                for (int i = 0, j = 0; i != fields.length; ++i, ++j) {
                    // skip useless column
                    // TODO correct offset of finished courses
                    if (fields[i] == "assessmentMark") {
                        ++j;
                    }

                    if (fields[i] == "cod") {
                        // cod is extracted from absenceHour, index of td should minus by one
                        try {
                            String linkHref = tds.get(j--).getElementsByTag("a").first().attr("href");
                            int start = linkHref.indexOf("cod=") + 4;
                            gradeAndAbsence.put(fields[i], linkHref.substring(start, linkHref.indexOf("&total")));
                        } catch (NullPointerException ne) {
                            gradeAndAbsence.put(fields[i], "");
                        }
                        continue;
                    }

                    gradeAndAbsence.put(fields[i], tds.get(j).text());
                }

                gradesAndAbsences.put(gradeAndAbsence);
            }

            Log.i(TAG, gradesAndAbsences.toString());

            return gradesAndAbsences;
        } catch (JSONException e) {
            Log.e(TAG, e.toString(), e);
            return null;
        }
    }

    public JSONArray getAttendenceHistory(String yearSem, String cod) throws IOException {
        String[] fields = {"date", "time", "hour", "present", "approvalResult"};
        try {
            Document body = request.getAttendenceHistory(yearSem, cod).parse();
            if (!checkLoginStatus(body)) return new JSONArray();
            body = request.getAttendenceHistory(yearSem, cod).parse();

            Elements tableRows = body.getElementsByTag("tbody")
                    .get(2)
                    .getElementsByTag("tr");
            JSONArray attendenceHistory = new JSONArray();

            // remove redundant row
            tableRows.remove(0);

            for (Element tr : tableRows) {
                Elements tds = tr.getElementsByTag("td");
                JSONObject attendenceHistoryItem = new JSONObject();

                for (int i = 0; i != fields.length; ++i) {
                    if (fields[i] == "present") {
                        String present = tds.get(i).childNodeSize() == 1 ? tds.get(i).text() : "Yes";
                        attendenceHistoryItem.put(fields[i], present);
                        continue;
                    }
                    attendenceHistoryItem.put(fields[i], tds.get(i).text());
                }

                attendenceHistory.put(attendenceHistoryItem);
            }

            Log.i(TAG, attendenceHistory.toString());

            return attendenceHistory;
        } catch (JSONException je) {
            Log.e(TAG, je.toString(), je);
            return null;
        }
    }

    // TODO not showing correctly
    public JSONArray getClassTime() throws IOException {
        String[] fields = {"classCode", "subject", "instructor", "room", "period", "time", "week"};
        try {
            Document body = request.getClassTime().parse();
            if (!checkLoginStatus(body)) return new JSONArray();
            body = request.getClassTime().parse();

            Elements tableRows = body
                    .getElementsByTag("table").get(3)
                    .getAllElements().first().getElementsByTag("tr");

            tableRows.remove(0);
            tableRows.remove(tableRows.size() - 1);

            JSONArray classTime = new JSONArray();

            String prevClassCode = "";
            String prevSubject = "";

            for (Element tr : tableRows) {
                Elements tds = tr.getElementsByTag("td");
                JSONObject classTimeItem = new JSONObject();

                int i = 1, f = 0;
                // add same class with different time
                if (tds.get(0).attr("colspan").equals("3")) {
                    classTimeItem.put(fields[f++], prevClassCode);
                    classTimeItem.put(fields[f++], prevSubject);
                } else {
                    String currClassCode = tds.get(i++).text();
                    String currSubject = tds.get(i++).text();

                    classTimeItem.put(fields[f++], currClassCode);
                    classTimeItem.put(fields[f++], currSubject);

                    prevClassCode = currClassCode;
                    prevSubject = currSubject;
                }

                // skip creation of week
                for (; f != fields.length - 1; ++f) {
                    classTimeItem.put(fields[f], tds.get(i++).text());
                }

                // start creation of week
                JSONArray week = new JSONArray();
                // Week starts on Sunday

                for (int day = 0; day != 7; ++day) {
                    week.put(!tds.get(i + day).getElementsByTag("img").isEmpty());
                }

                classTimeItem.put(fields[f], week);

                classTime.put(classTimeItem);
            }
            Log.i(TAG, classTime.toString());
            return classTime;
        } catch (JSONException je) {
            Log.e(TAG, je.toString(), je);
            return null;
        }
//
//        for (Element tr : tableRows) {
//        }
//
//        return list;
    }

    public JSONArray getExamTime() throws IOException {
        String[] fields = {"date", "time", "classcode", "title", "venue", "comment"};
        Document body = request.getExamTime().parse();
        if (!checkLoginStatus(body)) return new JSONArray();
        body = request.getExamTime().parse();

        Elements tableRows = body
                .getElementsByTag("table").get(3)
                .getAllElements().first().getElementsByTag("tr");

        tableRows.remove(0);
        tableRows.remove(tableRows.size() - 1);

        try {
            JSONArray examTime = new JSONArray();
            for (Element tr : tableRows) {
                Elements tds = tr.getElementsByTag("td");
                JSONObject examTimeObject = new JSONObject();

                for (int i = 0; i != fields.length; ++i) {
                    examTimeObject.put(fields[i], tds.get(i).text());
                }

                examTime.put(examTimeObject);
            }

            Log.i(TAG, examTime.toString());

            return examTime;
        } catch (JSONException je) {
            Log.e(TAG, je.toString(), je);
            return null;
        }
    }
}
