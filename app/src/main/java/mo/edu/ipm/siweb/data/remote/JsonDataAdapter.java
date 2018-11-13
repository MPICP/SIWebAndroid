package mo.edu.ipm.siweb.data.remote;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Connection;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

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

    public JSONObject login(String id, String password) throws IOException {
        try {
            Document body = request.login(id, password).parse();
            Element el = body.getElementsContainingText("Invalid NetID or Password").last();
            if (el != null) {
                return new JSONObject().put("login", false);
            } else {
                return new JSONObject().put("login", true);
            }
        } catch (JSONException je) {
            return null;
        }
    }

    public JSONObject getProfile() throws IOException {
        try {
            Document body = request.getProfile().parse();
            Log.d(TAG, body.html());
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
            return null;
        }
    }

    public JSONArray getAcademicYears() throws IOException {
        Element select = request.getAcademicYears().parse().getElementsByTag("select").first();
        JSONArray years = new JSONArray();

        for (Element option : select.getElementsByAttributeValueNot("value", "SELECT"))
            if (option.attr("value") != "") years.put(option.attr("value"));

        return years;
    }

    public JSONArray getGradesAndAbsence(String year) throws IOException {
        try {
            Element table = request.getGradesAndAbsence(year).parse().getElementById("result_table")
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

                for (int i = 0; i != fields.length; ++i) {
                    // skip useless column
                    if (fields[i] == "assessmentMark") {
                        ++i;
                    } else if (fields[i] == "cod") {
                        String linkHref = tds.get(i).getElementsByTag("a").first().attr("href");
                        int start = linkHref.indexOf("cod=") + 4;
                        gradeAndAbsence.put(fields[i], linkHref.substring(start, linkHref.indexOf("&total")));
                        continue;
                    }

                    gradeAndAbsence.put(fields[i], tds.get(i).text());
                }

                gradesAndAbsences.put(gradeAndAbsence);
            }

            return gradesAndAbsences;
        } catch (JSONException e) {
            return null;
        }
    }

    public JSONArray getAttendenceHistory(String yearSem, String cod) throws IOException {
        try {
            Elements tableRows = request.getAttendenceHistory(yearSem, cod).parse()
                    .getElementsByTag("tbody")
                    .get(2)
                    .getElementsByTag("tr");
            JSONArray attendenceHistory = new JSONArray();

            String[] fields = {"date", "time", "hour", "present", "approvalResult"};
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

            return attendenceHistory;
        } catch (JSONException je) {
            return null;
        }
    }

    // TODO
//    public JSONArray getClassTime() {
//        Elements tableRows = doc.getElementsByTag("table").get(3).getAllElements().first().getElementsByTag("tr");
//        tableRows.remove(0);
//        tableRows.remove(tableRows.size() - 1);
//
//        List<ClassTime> list = new ArrayList<>();
//
//        String prevClassCode = "";
//        String prevSubject = "";
//        for (Element tr : tableRows) {
//            ClassTime clsTime = new ClassTime();
//            Elements tds = tr.getElementsByTag("td");
//            int i = 1;
//            if (tds.get(0).attr("colspan").equals("3")) {
//                clsTime.classCode = prevClassCode;
//                clsTime.subject = prevSubject;
//            } else {
//                clsTime.classCode = tds.get(i++).text();
//                clsTime.subject = tds.get(i++).text();
//                prevClassCode = clsTime.classCode;
//                prevSubject = clsTime.subject;
//            }
//            clsTime.instructor = tds.get(i++).text();
//            clsTime.room = tds.get(i++).text();
//            clsTime.period = tds.get(i++).text();
//            clsTime.time = tds.get(i++).text(); // index = 6
//            clsTime.week = new boolean[7];
//            // Week starts on Sunday
//
//            for (int day = 0; day != 7; ++day) {
//                clsTime.week[day] = !tds.get(i + day).getElementsByTag("img").isEmpty();
//            }
//
//            list.add(clsTime);
//        }
//
//        return list;
//    }
}
