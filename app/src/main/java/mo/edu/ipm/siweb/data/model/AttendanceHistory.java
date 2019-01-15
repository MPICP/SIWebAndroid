package mo.edu.ipm.siweb.data.model;

import java.util.List;

import pl.droidsonroids.jspoon.annotation.Selector;

public class AttendanceHistory {

    @Selector("body > table:nth-child(3) > tbody > tr:nth-child(1) ~ tr")
    private List<AttendanceRecord> attendanceHistory;

    public List<AttendanceRecord> getAttendanceHistory() {
        return attendanceHistory;
    }
}
