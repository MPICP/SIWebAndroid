package mo.edu.ipm.siweb.data.model;

import java.util.Date;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

// TODO convert to event, i.e. startedAt, endedAt.

@Entity(tableName = "examtime")
public class ExamTime {

    @NonNull
    @PrimaryKey
    private String classCode;

    @NonNull
    // TODO split class code and section
    private String name;

    @NonNull
    private String location;

    @NonNull
    private Date startedAt;

    @NonNull
    private Date endedAt;

    @NonNull
    private String comment;

    public String getClassCode() {
        return classCode;
    }

    public String getComment() {
        return comment;
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

    public void setClassCode(String classCode) {
        this.classCode = classCode;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
