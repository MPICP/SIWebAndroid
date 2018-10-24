package mo.edu.ipm.cp.siweb.Model;

import java.lang.reflect.Field;

public class Profile {
    public String id;
    public String name;
    public String fname;
    public String mname;
    public String school;
    public String programCode;
    public String programName;
    public String section;
    public String language;
    public String mode;
    public String major;
    public String status;
    public String gpa;
    public String obtainedCredits;
    public String HKMOPermitNO;

    @Override
    public String toString() {
        return "id:" + id + ", name: " + name;
    }
}
