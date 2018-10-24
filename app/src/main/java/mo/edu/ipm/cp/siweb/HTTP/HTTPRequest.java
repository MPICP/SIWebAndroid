package mo.edu.ipm.cp.siweb.HTTP;

import android.util.JsonWriter;
import android.util.Log;

import com.alamkanak.weekview.WeekViewEvent;

import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mo.edu.ipm.cp.siweb.Model.AttendenceHistoryItem;
import mo.edu.ipm.cp.siweb.Model.ClassTime;
import mo.edu.ipm.cp.siweb.Model.Course;
import mo.edu.ipm.cp.siweb.Model.Profile;

public class HTTPRequest {
    private final static String TAG = "HTTPRequestWrapper";
//    private Map<String, String> cookies = new HashMap<String, String>() {{
//        put("ASPSESSIONIDQSCSSBRB", "LOHEPDJDODKNOGGBDGMOOAAJ");
//    }};
    private Map<String, String> cookies = new HashMap<String, String>();
    private final static String baseURL = "https://wapps.ipm.edu.mo/siweb";
    private static HTTPRequest instance = null;

    public static HTTPRequest getInstance() {
        if (instance == null) {
            instance = new HTTPRequest();
        }
        return instance;
    }

    private Connection request(String url) throws Exception{
        return Jsoup.connect(baseURL + url).sslSocketFactory(new TlsSocketFactoryCompat()).cookies(cookies);
    }

    public boolean login(String ID, String Password) {
        try {
            Connection.Response res = Jsoup.connect(baseURL + "/login.asp")
                    .header("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8")
                    .data("action", "login")
                    .data("NETID", ID)
                    .data("NETPASSWORD", Password)
                    .method(Connection.Method.POST)
                    .sslSocketFactory(new TlsSocketFactoryCompat())
                    .execute();

            Element el = res.parse().getElementsContainingText("Invalid NetID or Password").first();

            if (el == null) {
                cookies = res.cookies();
                return true;
            }


        } catch (IOException e) {
            Log.e(TAG, e.toString(), e);
            return false;
        } catch (NoSuchAlgorithmException e) {
            Log.e(TAG, e.toString(), e);
        } catch (KeyManagementException e) {
            Log.e(TAG, e.toString(), e);
        }
        return false;
    }

    public Profile getStudentProfile() throws Exception
    {
        Log.i(TAG, baseURL + "/stud_info.asp");
        Document doc = Jsoup.connect(baseURL + "/stud_info.asp")
                .sslSocketFactory(new TlsSocketFactoryCompat())
                .cookies(cookies)
                .get();

        Log.i(TAG, doc.html());
        Element table = doc.getElementsByAttributeValue("width", "600").first();
        Elements tr = table.getElementsByTag("tr");
        Profile profile = new Profile();
        // Student ID
        int i = 1;
        profile.id = tr.get(i++).getAllElements().get(4).text();
        profile.name = tr.get(i++).getAllElements().get(4).text();
        profile.fname = tr.get(i++).getAllElements().get(4).text();
        profile.mname = tr.get(i++).getAllElements().get(4).text();
        profile.school = tr.get(i++).getAllElements().get(4).text();
        profile.programCode = tr.get(i++).getAllElements().get(4).text();
        profile.programName = tr.get(i++).getAllElements().get(4).text();
        profile.section = tr.get(i++).getAllElements().get(4).text();
        profile.language = tr.get(i++).getAllElements().get(4).text();
        profile.mode = tr.get(i++).getAllElements().get(4).text();
        profile.major = tr.get(i++).getAllElements().get(4).text();
        profile.status = tr.get(i++).getAllElements().get(4).text();
        profile.gpa = tr.get(i++).getAllElements().get(4).text();
        profile.obtainedCredits = tr.get(i++).getAllElements().get(4).text();
        profile.HKMOPermitNO = tr.get(i++).getAllElements().get(4).text();
        return profile;
    }

    public String getNews()
    {
        try {
            Document doc = Jsoup.connect(baseURL + "/mainFrame.htm").sslSocketFactory(new TlsSocketFactoryCompat()).get();
            doc.head().appendElement("link")
                    .attr("rel", "stylesheet")
                    .attr("href", "file:///android_asset/news.css");

            doc.head().appendElement("meta")
                    .attr("name", "viewport")
                    .attr("content", "width=device-width, initial-scale=1");

            for (Element elem : doc.getElementsByAttribute("src"))
                elem.attr("src",
                        (elem.attr("src").contains("http")
                                || elem.attr("src").contains("file"))
                                ? elem.attr("src") : baseURL + "/" + elem.attr("src") );
            return doc.outerHtml();
        } catch (Exception e) {
            Log.e(TAG, e.toString(), e);
            return "<h1>Cannot get news</h1>";
        }
    }

    private Document getAcademicYearHTML () throws Exception {
        return Jsoup.connect(baseURL + "/grade.asp").sslSocketFactory(new TlsSocketFactoryCompat()).cookies(cookies).get();
    }

    public ArrayList<String> getAcademicYearForGrade() throws Exception{
        Document doc = getAcademicYearHTML();
        Element select = doc.getElementsByTag("select").first();

        ArrayList<String> options = new ArrayList<>();
        for (Element option : select.getElementsByAttributeValueNot("value", "SELECT")) {
            if (option.attr("value") != "")
                options.add(option.attr("value"));
        }

        return options;
    }

    public Course[] getGradesAndAbsense (String year) throws Exception {
        Document doc = request("/grade.asp")
                .header("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8")
                .data("sel_year", year).post();

        Element table = doc.getElementById("result_table").getElementsByTag("tbody").first();
        Elements tableRows = table.getElementsByTag("tr");
        tableRows.remove(0);
        tableRows.remove(0);
        tableRows.remove(tableRows.last());

        ArrayList<Course> coursesArr = new ArrayList<>();

        Log.i(TAG, "Starting for loop");

        for (Element row : tableRows) {
            Elements tds = row.getElementsByTag("td");
            int i = 0;
            Course course = new Course();
            course.year = tds.get(i++).text();
            course.sem = tds.get(i++).text();
            course.subjectCode = tds.get(i++).text();
            course.sectionCode = tds.get(i++).text();
            course.subjectTitle = tds.get(i++).text();
            ++i;
            course.assessmentMark = tds.get(i++).text();
            course.examMark = tds.get(i++).text();
            course.finalMark = tds.get(i++).text();
            course.finalGrade = tds.get(i++).text();
            course.totalHour = tds.get(i++).text();
            course.absenseHour = tds.get(i++).text();
            String linkHref = tds.get(i).getElementsByTag("a").first().attr("href");
            int start = linkHref.indexOf("cod=") + 4;
            course.cod = linkHref.substring(start, linkHref.indexOf("&total"));
            course.absenseRate = tds.get(i++).text();
            course.raRate = tds.get(i++).text();
            course.lastEntryDate = tds.get(i++).text();

            coursesArr.add(course);

            Log.i(TAG, course.cod);
        }

        Log.i(TAG, "end of for loop");

        return coursesArr.toArray(new Course[coursesArr.size()]);
    }

    public AttendenceHistoryItem[] getAttendenceHistory(String yearSem, String cod) throws Exception {
        Document doc = request(String.format("/grade_atte.asp?anol_ano=%s&cod=%s&total=1", yearSem, cod)).get();
        Elements tableRows = doc.getElementsByTag("tbody").get(2).getElementsByTag("tr");
        tableRows.remove(0);

        ArrayList<AttendenceHistoryItem> atteArr = new ArrayList<>();

        for (Element tr : tableRows) {
            int i = 0;
            Elements tds = tr.getElementsByTag("td");
            AttendenceHistoryItem atte = new AttendenceHistoryItem();
            atte.date = tds.get(i++).text();
            atte.time = tds.get(i++).text();
            atte.hour = tds.get(i++).text();
            atte.present = tds.get(i).childNodeSize() == 1 ? tds.get(i).text() : "Yes";
            i++;
            atte.approvalResult = tds.get(i++).text();
            atteArr.add(atte);
            Log.i(TAG, atte.date + atte.time + atte.present);
            Log.i(TAG, "END A LOOP");
        }

        return atteArr.toArray(new AttendenceHistoryItem[atteArr.size()]);
    }

    public List<ClassTime> getClassTime() throws Exception{
        Document doc = request("/time_stud.asp").get();
        Elements tableRows = doc.getElementsByTag("table").get(3).getAllElements().first().getElementsByTag("tr");
        tableRows.remove(0);

        List<ClassTime> list = new ArrayList<>();

        String prevClassCode = "";
        String prevSubject = "";
        for (Element tr : tableRows) {
            ClassTime clsTime = new ClassTime();
            Elements tds = tr.getElementsByTag("td");
            int i = 0;
            if (tds.get(0).attr("colspan").equals("3")) {
                clsTime.classCode = prevClassCode;
                clsTime.subject = prevSubject;
                ++i;
            } else {
                ++i;
                clsTime.classCode = tds.get(i++).text();
                clsTime.subject = tds.get(i++).text();
                prevClassCode = clsTime.classCode;
                prevSubject = clsTime.subject;
            }
            clsTime.instructor = tds.get(i++).text();
            clsTime.room = tds.get(i++).text();
            clsTime.period = tds.get(i++).text();
            clsTime.time = tds.get(i++).text(); // index = 6
            clsTime.week = new boolean[7];
            // Week starts on Sunday

            for (int day = 0; day != 7; ++day) {
                clsTime.week[day] = !tds.get(i + day).getElementsByTag("img").isEmpty();
            }

            list.add(clsTime);
        }

        return list;
    }

}
