package mo.edu.ipm.siweb.data.model;

import java.util.List;

import pl.droidsonroids.jspoon.annotation.Selector;

public class AcademicYear {
    @Selector(value = "body > form > div > table > tbody > tr > td > select > option[value=\"SELECT\"] ~ option",
            attr = "value")
    private List<String> years;

    public List<String> getYears() {
        return years;
    }

    public void setYears(List<String> years) {
        this.years = years;
    }
}
