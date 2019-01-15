package mo.edu.ipm.siweb.util;

import android.util.Log;

import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

public class HttpRequest {
    private final static String TAG = "HTTPRequestWrapper";
    private Map<String, String> cookies;
    private final static String baseURL = "https://wapps.ipm.edu.mo/siweb";
    private static HttpRequest instance = null;

    public static HttpRequest getInstance() {
        if (instance == null) {
            instance = new HttpRequest();
        }
        return instance;
    }

    /**
     * Wrapper method of Jsoup.connect
     *
     * @param url
     * @return A new Jsoup connection
     * @throws Exception
     */
    private Connection request(String url) {
        try {
            Log.d(TAG, "HTTP Request: " + baseURL + url);
            Connection connection = Jsoup.connect(baseURL + url).sslSocketFactory(new TlsSocketFactoryCompat());
            if (cookies != null)
                connection.cookies(cookies);
            return connection;
        } catch (KeyManagementException ke) {
            Log.e(TAG, ke.toString(), ke);
            return null;
        } catch (NoSuchAlgorithmException nae) {
            Log.e(TAG, nae.toString(), nae);
            return null;
        }
    }

    /**
     * Send login request
     *
     * @param ID
     * @param Password
     * @return
     * @throws Exception
     */
    public Connection.Response login(String ID, String Password) throws IOException {
        Connection.Response response = request("/login.asp")
                .header("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8")
                .data("action", "login")
                .data("NETID", ID)
                .data("NETPASSWORD", Password)
                .method(Connection.Method.POST)
                .execute();
        cookies = response.cookies();
        return response;
    }

    /**
     * Get class timetable
     *
     * @return
     * @throws IOException
     */
    public Connection.Response getClassTime() throws IOException {
        Connection.Response response = request("/time_stud.asp").execute();

        return response;
    }

}
