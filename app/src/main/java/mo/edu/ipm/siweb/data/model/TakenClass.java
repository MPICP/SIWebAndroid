package mo.edu.ipm.siweb.data.model;

import java.util.Date;

import androidx.room.Entity;
import pl.droidsonroids.jspoon.annotation.Format;
import pl.droidsonroids.jspoon.annotation.Selector;


@Entity(tableName = "takenclass")
public class TakenClass {

    @Selector("td:nth-child(1)")
    private String year;

    @Selector("td:nth-child(2) > div")
    private int sem;

    @Selector("td:nth-child(3) > span")
    private String subjectCode;

    @Selector("td:nth-child(4)")
    private int sectionCode;

    @Selector("td:nth-child(5)")
    private String subjectTitle;

    @Selector("td:nth-child(6) > div")
    private String unknownFields;

    @Selector(value = "td:nth-child(7) > div > font", defValue = "0", regex = "(\\S*)")
    private double caMark;

    @Selector(value = "td:nth-child(8) > div > font", defValue = "0", regex = "(\\S*)")
    private double examMark;

    @Selector(value = "td:nth-child(9) > div > strong", defValue = "0", regex = "(\\S*)")
    private double finalMark;

    @Selector("td:nth-child(10) > div > font > strong")
    private String finalGrade;

    @Selector(value = "td:nth-child(11) > div", defValue = "0", regex = "(\\S*)")
    private double totalHour;

    @Selector(value = "td:nth-child(12) > div > a", defValue = "0", regex = "(\\S*)")
    private double absenceHour;

    // TODO: type should be double (percentage)
    @Selector("td:nth-child(13) > div > a")
    private String absenceRate; // temporal workaround, will use custom converter some day

    @Selector("td:nth-child(14) > div > a")
    private String raRate;

    @Format(value = "yyyy-MM-dd")
    @Selector(value = "td:nth-child(15) > span", regex = "(\\S*)", defValue = "1970-01-01")
    private Date lastEntryDate;

    @Selector(value = "td:nth-child(13) > div > a",
            attr = "href",
            regex = "&cod=(\\d*)",
            defValue = "0")
    private int cod;

    /**
     * Where getter start
     */
    public String getYear() {
        return year;
    }

    public int getSem() {
        return sem;
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public int getSectionCode() {
        return sectionCode;
    }

    public String getSubjectTitle() {
        return subjectTitle;
    }

    public String getUnknownFields() {
        return unknownFields;
    }

    public double getCaMark() {
        return caMark;
    }

    public double getExamMark() {
        return examMark;
    }

    public double getFinalMark() {
        return finalMark;
    }

    public String getFinalGrade() {
        return finalGrade;
    }

    public double getTotalHour() {
        return totalHour;
    }

    public double getAbsenceHour() {
        return absenceHour;
    }

    public String getAbsenceRate() {
        return absenceRate;
    }

    public String getRaRate() {
        return raRate;
    }

    public Date getLastEntryDate() {
        return lastEntryDate;
    }

    public int getCod() {
        return cod;
    }

    public boolean isFinished() {
        return finalGrade != "I";
    }
}
