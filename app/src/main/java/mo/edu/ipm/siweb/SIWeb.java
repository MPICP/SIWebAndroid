package mo.edu.ipm.siweb;

import android.app.Application;
import android.content.Context;

import com.jakewharton.threetenabp.AndroidThreeTen;

import java.lang.ref.SoftReference;

public class SIWeb extends Application {

    public static transient SoftReference<Context> applicationContext;

    @Override
    public void onCreate() {
        super.onCreate();
        applicationContext = new SoftReference<>(getApplicationContext());
        AndroidThreeTen.init(this);
    }

}
