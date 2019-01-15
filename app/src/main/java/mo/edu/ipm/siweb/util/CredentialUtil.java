package mo.edu.ipm.siweb.util;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.io.IOException;

import mo.edu.ipm.siweb.R;
import mo.edu.ipm.siweb.SIWeb;
import mo.edu.ipm.siweb.data.HttpService;
import mo.edu.ipm.siweb.ui.LoginActivity;

public class CredentialUtil {

    private static final String TAG = "CredentialUtil";
    private static boolean authorized = false;
    private static String mStudentID = "";
    private static String mPassword = "";

    public static void setUnauthorized() {
        authorized = false;
    }

    public static void setAuthorized() {
        authorized = true;
    }

    public static boolean isAuthorized() {
        return authorized;
    }


    public static void refreshCredential(Context context) {
        if (authorized) return;
        new Thread(() -> {

            refreshSavedCredential();

            if (!(mStudentID.isEmpty() || mPassword.isEmpty())) {
                refreshCookie();
            }

            if (!authorized) {
                // cannot refresh session
            }
        }).start();
    }

    public static boolean refreshSavedCredential() {
        Context context = SIWeb.applicationContext.get();
        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getString(R.string.pref_credential),
                Context.MODE_PRIVATE);

        if (sharedPreferences == null) {
            return false;
        }

        if (mStudentID.isEmpty() || mPassword.isEmpty()) {
            mStudentID = sharedPreferences.getString("studentID", "");
            mPassword = sharedPreferences.getString("password", "");
        }

        return refreshCookie();
    }

    /**
     * Attempt relogin to refresh cookie
     *
     * @return
     */
    public static boolean refreshCookie() {
        try {
            boolean status = authenticate(mStudentID, mPassword);
            authorized = status;
            return status;
        } catch (IOException ioe) {
            return false;
        }
    }

    public static boolean authenticate(String id, String password) throws IOException {
        return HttpService.SIWEB().authenticate(id, password, "login").execute().code() == 302;
    }

    public static void reLogin() {
        Context context = SIWeb.applicationContext.get();
        Intent intent = new Intent(context, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public static void logout() {
        Context context = SIWeb.applicationContext.get();
        context.getSharedPreferences("mo.edu.ipm.siweb.pref_Credential", Context.MODE_PRIVATE).edit().clear().commit();
        reLogin();
    }

}

