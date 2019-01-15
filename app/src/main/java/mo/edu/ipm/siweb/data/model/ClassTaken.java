package mo.edu.ipm.siweb.data.model;

import java.util.List;

import pl.droidsonroids.jspoon.annotation.Selector;

public class ClassTaken {
    @Selector("#result_table > tbody > tr:nth-child(2) ~ tr[bgcolor^=\"#\"]")
    private List<ClassTaken> mClassTakensList;
    private List<ClassGrade> mClassGradeList;
    private List<ClassAbsence> mClassAbsenceList;

    public List<ClassTaken> getClassTakensList() {
        return mClassTakensList;
    }

    public List<ClassGrade> getClassGradeList() {
        return mClassGradeList;
    }

    public List<ClassAbsence> getClassAbsenceList() {
        return mClassAbsenceList;
    }
}
