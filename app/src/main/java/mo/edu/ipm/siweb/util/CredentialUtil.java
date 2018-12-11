package mo.edu.ipm.siweb.util;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import org.json.JSONException;

import java.io.IOException;

import mo.edu.ipm.siweb.R;
import mo.edu.ipm.siweb.data.remote.JsonDataAdapter;
import mo.edu.ipm.siweb.ui.LoginActivity;

public class CredentialUtil {

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

            refreshSavedCredential(context);

            if (!(mStudentID.isEmpty() || mPassword.isEmpty())) {
                refreshCookie();
            }

            if (!authorized) {
                // cannot refresh session
                Intent intent = new Intent(context, LoginActivity.class);
                context.startActivity(intent);
            }
        }).start();
    }

    public static void refreshSavedCredential(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getString(R.string.pref_credential),
                Context.MODE_PRIVATE);

        if (mStudentID.isEmpty() || mPassword.isEmpty()) {
            mStudentID = sharedPreferences.getString("studentID", "");
            mPassword = sharedPreferences.getString("password", "");
        }

    }

    public static void refreshCookie() {
        try {
            boolean status = JsonDataAdapter.getInstance().login(mStudentID, mPassword).getBoolean("login");
            authorized = status;
        } catch (IOException ioe) {
        } catch (JSONException je) {
        }
    }

}

