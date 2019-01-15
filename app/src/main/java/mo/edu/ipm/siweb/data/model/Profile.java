package mo.edu.ipm.siweb.data.model;

import pl.droidsonroids.jspoon.annotation.Selector;

public class Profile {
    @Selector("body > div:nth-child(4) > center > table:nth-child(1) > tbody > tr:nth-child(2) > td:nth-child(2) > font")
    private String id;

    @Selector("body > div:nth-child(4) > center > table:nth-child(1) > tbody > tr:nth-child(3) > td:nth-child(2) > span")
    private String name;

    @Selector("body > div:nth-child(4) > center > table:nth-child(1) > tbody > tr:nth-child(4) > td:nth-child(2) > span")
    private String fatherName;

    @Selector("body > div:nth-child(4) > center > table:nth-child(1) > tbody > tr:nth-child(5) > td:nth-child(2) > span")
    private String motherName;

    @Selector("body > div:nth-child(4) > center > table:nth-child(1) > tbody > tr:nth-child(6) > td:nth-child(2) > font")
    private String school;

    @Selector("body > div:nth-child(4) > center > table:nth-child(1) > tbody > tr:nth-child(7) > td:nth-child(2) > font")
    private String programCode;

    @Selector("body > div:nth-child(4) > center > table:nth-child(1) > tbody > tr:nth-child(8) > td:nth-child(2) > font")
    private String programName;

    @Selector("body > div:nth-child(4) > center > table:nth-child(1) > tbody > tr:nth-child(9) > td:nth-child(2) > font")
    private String section;

    @Selector("body > div:nth-child(4) > center > table:nth-child(1) > tbody > tr:nth-child(10) > td:nth-child(2) > font")
    private String language;

    @Selector("body > div:nth-child(4) > center > table:nth-child(1) > tbody > tr:nth-child(11) > td:nth-child(2) > font")
    private String mode;

    @Selector("body > div:nth-child(4) > center > table:nth-child(1) > tbody > tr:nth-child(12) > td:nth-child(2) > font")
    private String major;

    @Selector("body > div:nth-child(4) > center > table:nth-child(1) > tbody > tr:nth-child(13) > td:nth-child(2) > font")
    private String status;

    @Selector("body > div:nth-child(4) > center > table:nth-child(1) > tbody > tr:nth-child(14) > td:nth-child(2) > font")
    private double gpa;

    @Selector("body > div:nth-child(4) > center > table:nth-child(1) > tbody > tr:nth-child(15) > td:nth-child(2) > font")
    private double obtainedCredits;

    @Selector("body > div:nth-child(4) > center > table:nth-child(1) > tbody > tr:nth-child(16) > td:nth-child(2) > font")
    private String HKMOPermitNO;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public String getMotherName() {
        return motherName;
    }

    public void setMotherName(String motherName) {
        this.motherName = motherName;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getProgramCode() {
        return programCode;
    }

    public void setProgramCode(String programCode) {
        this.programCode = programCode;
    }

    public String getProgramName() {
        return programName;
    }

    public void setProgramName(String programName) {
        this.programName = programName;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getGpa() {
        return gpa;
    }

    public void setGpa(double gpa) {
        this.gpa = gpa;
    }

    public double getObtainedCredits() {
        return obtainedCredits;
    }

    public void setObtainedCredits(double obtainedCredits) {
        this.obtainedCredits = obtainedCredits;
    }

    public String getHKMOPermitNO() {
        return HKMOPermitNO;
    }

    public void setHKMOPermitNO(String HKMOPermitNO) {
        this.HKMOPermitNO = HKMOPermitNO;
    }
}
