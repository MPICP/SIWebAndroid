package mo.edu.ipm.siweb.data.model;

import org.threeten.bp.LocalTime;

public class Event {

    public Event(ClassTime classTime) {
        this.type = CLASS;

        this.name = classTime.getName();
        this.location = classTime.getLocation();
        this.startTime = classTime.getStartTime();
        this.endTime = classTime.getEndTime();

        if (this.status == SCHEDULED) {
            if (LocalTime.now().isAfter(startTime) && LocalTime.now().isBefore(endTime)) {
                this.status = ONGOING;
            }
        }
    }

    public static final int SCHEDULED = 0;
    public static final int CANCELLED = 1;
    public static final int ONGOING = 2;

    public static final int CLASS = 0;
    public static final int EXAM = 1;

    private String name;
    private String location;
    private int type;

    private int status;
    private LocalTime startTime;
    private LocalTime endTime;

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public int getType() {
        return type;
    }

    public int getStatus() {
        return status;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }
}
