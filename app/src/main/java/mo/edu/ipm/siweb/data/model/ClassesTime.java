package mo.edu.ipm.siweb.data.model;

import org.jsoup.nodes.Element;
import org.threeten.bp.LocalDate;
import org.threeten.bp.LocalDateTime;
import org.threeten.bp.LocalTime;
import org.threeten.bp.format.DateTimeFormatter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import pl.droidsonroids.jspoon.annotation.Selector;

public class ClassesTime {

    private int prevSem = 0;
    private String prevSubject = "";
    private String prevClassCode = "";

    @Selector("body > strong > table > tbody > tr")
    private List<Element> mClassTimeElements;

    public List<ClassTime> getClassTimeList() {
        List<ClassTime> classTimeList = new ArrayList<>();

        for (Element element : mClassTimeElements) {
            if (element.childNodeSize() < 10) {
                continue;
            }

            if (element.getElementsByAttributeValue("bgcolor", "#0000CE").isEmpty()) {
                ClassTime classTime = parseClass(element);
                if (classTime != null) {
                    classTimeList.add(classTime);
                }
            }
        }

        return classTimeList;
    }

    private ClassTime parseClass(Element element) {

        if (element.childNodeSize() < 25) {
            return null;
        }

        if (element.childNodeSize() > 26){
            prevSem = Integer.parseInt(element.child(0).text().trim());
            prevClassCode = element.child(1).text();
            prevSubject = element.child(2).text();
        }
        String location = element.select("td:nth-last-child(10)").text();
        // TODO set time and date, interval
        String period = element.select("td:nth-last-child(9)").text();
        String[] periodArr = period.split("-");

        String time = element.select("td:nth-last-child(8)").text();
        String[] timeArr = time.split("-");

        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm");

        ClassTime classTime = new ClassTime();
//        classTime.setBaseEvent(prevSubject, location, startedAt, endedAt);
//        classTime.setRecurring(true);
        classTime.setName(prevSubject);
        classTime.setLocation(location);
        try {
            classTime.setStartedAt(format.parse(periodArr[0] + ' ' + timeArr[0]));
            classTime.setEndedAt(format.parse(periodArr[1] + ' ' + timeArr[1]));
        } catch (ParseException e) {
            return null;
        }
        classTime.setSem(prevSem);
        classTime.setClassCode(prevClassCode);
        classTime.setInstructor(element.select("td:nth-last-child(11)").text());
        // TODO
        classTime.setOnWeekDays(getWeekDays(element));

        return classTime;
    }

    /**
     * Get weekdays
     *
     * [Mon, Tue, Wed ...]
     */
    private boolean[] getWeekDays(Element element) {
        boolean[] weekDays = new boolean[7];
        for (int i = 1; i != 8; ++i) {
            boolean hasClass = !element.select("td:nth-last-child(" + i + ") img").isEmpty();
                // Sat Fri Thu Wed Tue Mon Sun 1 2 3 4 5 6 7
                // Sun Mon Tue Wed Thu Fri Sat 7 6 5 4 5 2 1 -> 0 1 2 3 4 5 6
                // Mon Tue Wed Thu Fri Sat Sun
            if (hasClass) {
                int index = 7 - i;
                weekDays[index] = true;
            }
        }
        return weekDays;
    }
}
