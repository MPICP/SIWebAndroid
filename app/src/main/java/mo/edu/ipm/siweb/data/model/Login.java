package mo.edu.ipm.siweb.data.model;

import pl.droidsonroids.jspoon.annotation.Selector;

public class Login {
    @Selector("body > table:nth-child(2) > tbody > tr:nth-child(1) > td:nth-child(4) > table > tbody > tr > td > div > form > table > tbody > tr:nth-child(2) > td:nth-child(2) > b > p > strong > font")
    private String info;

    public boolean isLogin() {
        return info == null;
    }
}
