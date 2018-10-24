package mo.edu.ipm.cp.siweb;

import android.util.Log;

import com.alamkanak.weekview.WeekViewEvent;

import org.jsoup.nodes.Document;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import mo.edu.ipm.cp.siweb.HTTP.HTTPRequest;
import mo.edu.ipm.cp.siweb.Model.Profile;

public class DataStorage {

    public static DataStorage instance = null;
    private HTTPRequest request = HTTPRequest.getInstance();

    private Profile profile;
    private String news;
    private ArrayList<String> academicYearOptions;
    public static String currentSelectedAcademicYear;

    public static DataStorage getData() {
        if (instance == null) {
            instance = new DataStorage();
        }
        return instance;
    }

    public Profile getProfile() throws Exception {
        if (profile == null) {
            profile = request.getStudentProfile();
        }
        return profile;
    }

    public void refreshProfile() throws Exception {
        profile = request.getStudentProfile();
    }

    public String getNews() {
        if (news == null) {
            news = request.getNews();
        }
        return news;
    }

    public ArrayList<String> getAcademicYearOptions() throws Exception {
        if (academicYearOptions == null) {
            academicYearOptions = request.getAcademicYearForGrade();
        }
        return academicYearOptions;
    }

    // TODO
    //public void getWeekViewEvent(int year, int month) throws Exception {
//        Calendar calendar = Calendar.getInstance();
//
//        SimpleDateFormat data = new SimpleDateFormat("yyyy/MM/DD");
//
//        Log.e("DATA", period.split("-")[0] + "[][][]" + period.split("-")[1]);
//        Date start = new SimpleDateFormat("yyyy/MM/DD").parse(period.split("-")[0]);
//        Date end = new SimpleDateFormat("yyyy/MM/DD").parse(period.split("-")[1]);
//
//        Log.e("DATA START", data.format(start));
//        Log.e("DATA END", data.format(end));
//
//        calendar.setTime(start);
//
//        while (start.compareTo(end) < 1) {
//
//            int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
//
//            if (week[--dayOfWeek]) {
//                WeekViewEvent we = new WeekViewEvent();
//                we.setLocation(room);
//                we.setName(subject);
//                // TODO: add hour
//                Calendar startTime = (Calendar) calendar.clone();
//                Calendar endTime = (Calendar) calendar.clone();
//                startTime.add(Calendar.HOUR_OF_DAY, Integer.parseInt(time.substring(0, 2)));
//                startTime.add(Calendar.MINUTE, Integer.parseInt(time.substring(3, 5)));
//                endTime.add(Calendar.HOUR_OF_DAY, Integer.parseInt(time.substring(6, 8)));
//                endTime.add(Calendar.MINUTE, Integer.parseInt(time.substring(9, 11)));
//                we.setStartTime(startTime);
//                we.setEndTime(calendar);
//                list.add(we);
//                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
//                // Log.e("[WEEKVIEWEVENT]", instructor + ' ' + period + ' ' + sdf.format(startTime.getTime()));
//            }
//            calendar.add(Calendar.DAY_OF_MONTH, 1);
//
//        }
//    }
}
