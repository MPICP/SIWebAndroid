package mo.edu.ipm.siweb.util;

import java.io.File;

import mo.edu.ipm.siweb.SIWeb;

public class FileUtil {

    public static File getCacheDirectory() {
        return SIWeb.applicationContext.get().getCacheDir();
    }

}
