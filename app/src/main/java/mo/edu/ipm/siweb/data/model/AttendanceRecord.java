package mo.edu.ipm.siweb.data.model;

import java.util.Date;

import mo.edu.ipm.siweb.util.converter.presentConverter;
import pl.droidsonroids.jspoon.annotation.Selector;

// TODO convert to event (startedAt, endedAt)
public class AttendanceRecord {

    @Selector(value = "td:nth-child(1) > font", format = "yyyy-MM-dd")
    private Date date;

    @Selector(value = "td:nth-child(2) > font")
    private String time;

    @Selector(value = "td:nth-child(3) > font")
    private String hour;

    @Selector(value = "td:nth-child(4) > font", converter = presentConverter.class)
    private String present;

    @Selector(value = "td:nth-child(5) > font")
    private String approvalResult;

    public Date getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getHour() {
        return hour;
    }

    public String getPresent() {
        return present;
    }

    public String getApprovalResult() {
        return approvalResult;
    }
}

