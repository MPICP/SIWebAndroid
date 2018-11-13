package mo.edu.ipm.siweb.util;

import android.content.Intent;

import org.jsoup.HttpStatusException;

public class StartLoginActivityOnAuthFailed {

    public static void start(HttpStatusException hse) {
        if (hse.getStatusCode() == 401) {
            // TODO start login activity
        }
    }
}
