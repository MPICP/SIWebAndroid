package mo.edu.ipm.siweb.data.model;

import org.threeten.bp.LocalDate;
import org.threeten.bp.LocalDateTime;
import org.threeten.bp.LocalTime;

import java.util.Date;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "classtime")
public class ClassTime {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull
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
    private LocalDate startedAt;

    @NonNull
    private LocalDate endedAt;

    @NonNull
    private LocalTime startTime;

    @NonNull
    private LocalTime endTime;

    @NonNull
    private boolean[] onWeekDays;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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
    public LocalDate getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(@NonNull LocalDate startedAt) {
        this.startedAt = startedAt;
    }

    @NonNull
    public LocalDate getEndedAt() {
        return endedAt;
    }

    public void setEndedAt(@NonNull LocalDate endedAt) {
        this.endedAt = endedAt;
    }

    @NonNull
    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(@NonNull LocalTime startTime) {
        this.startTime = startTime;
    }

    @NonNull
    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(@NonNull LocalTime endTime) {
        this.endTime = endTime;
    }

    @NonNull
    public boolean[] getOnWeekDays() {
        return onWeekDays;
    }

    public void setOnWeekDays(@NonNull boolean[] onWeekDays) {
        this.onWeekDays = onWeekDays;
    }
}
