package mo.edu.ipm.siweb.data.model;

import org.jsoup.nodes.Element;
import org.threeten.bp.LocalDate;
import org.threeten.bp.LocalDateTime;
import org.threeten.bp.LocalTime;
import org.threeten.bp.format.DateTimeFormatter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import pl.droidsonroids.jspoon.annotation.Selector;

public class ExamsTime {
    @Selector("body > strong > table:nth-child(4) > tbody > tr:nth-child(1) ~ tr[bgcolor^=\"#\"]")
    private List<Element> mExamsTimeElement;

    public List<ExamTime> getExamsTime() {
        List<ExamTime> examTimeList = new ArrayList<>();

        if (mExamsTimeElement == null) {
            return null;
        }

        for (Element element : mExamsTimeElement) {
            examTimeList.add(parseExam(element));
        }

        return examTimeList;
    }

    private ExamTime parseExam (Element element) {
        ExamTime examTime = new ExamTime();
        String date = element.selectFirst("td:nth-child(1)").text();
        String time = element.selectFirst("td:nth-child(2)").text();
        String classCode = element.selectFirst("td:nth-child(3)").text();
        String name = element.selectFirst("td:nth-child(4)").text();
        String location = element.selectFirst("td:nth-child(5)").text();
        String comment = element.selectFirst("td:nth-child(6)").text();

        String[] timeArr = time.split("-");
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");

        examTime.setName(name);
        examTime.setLocation(location);
        examTime.setClassCode(classCode);
        examTime.setStartedAt(parseTime(date, timeArr[0]));
        examTime.setEndedAt(parseTime(date, timeArr[1]));
        examTime.setComment(comment);

        return examTime;
    }

    private Date parseTime(String date, String time) {
        String parseStr = date + ' ' + time;
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd (EEE) hh:mm");

        try {
            return format.parse(parseStr);
        } catch (ParseException ex) {
            return null;
        }
    }
}
