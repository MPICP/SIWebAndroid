package mo.edu.ipm.siweb.data.model;

import java.util.Date;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "classtime")
public class ClassTime {

    @NonNull
    @PrimaryKey
    private String classCode;

    @NonNull
    private int sem;

    @NonNull
    private String name;

    @NonNull
    private String location;

    @NonNull
    private String instructor;

    @NonNull
    private Date startedAt;

    @NonNull
    private Date endedAt;

    @NonNull
    private boolean[] onWeekDays;

    @NonNull
    public String getClassCode() {
        return classCode;
    }

    public void setClassCode(@NonNull String classCode) {
        this.classCode = classCode;
    }

    @NonNull
    public int getSem() {
        return sem;
    }

    public void setSem(@NonNull int sem) {
        this.sem = sem;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    @NonNull
    public String getLocation() {
        return location;
    }

    public void setLocation(@NonNull String location) {
        this.location = location;
    }

    @NonNull
    public String getInstructor() {
        return instructor;
    }

    public void setInstructor(@NonNull String instructor) {
        this.instructor = instructor;
    }

    @NonNull
    public Date getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(@NonNull Date startedAt) {
        this.startedAt = startedAt;
    }

    @NonNull
    public Date getEndedAt() {
        return endedAt;
    }

    public void setEndedAt(@NonNull Date endedAt) {
        this.endedAt = endedAt;
    }

    @NonNull
    public boolean[] getOnWeekDays() {
        return onWeekDays;
    }

    public void setOnWeekDays(@NonNull boolean[] onWeekDays) {
        this.onWeekDays = onWeekDays;
    }
}
