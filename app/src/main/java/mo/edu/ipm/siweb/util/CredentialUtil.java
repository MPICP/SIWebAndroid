package mo.edu.ipm.siweb.util;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.io.IOException;
import java.lang.ref.WeakReference;

import mo.edu.ipm.siweb.R;
import mo.edu.ipm.siweb.data.remote.JsonDataAdapter;
import mo.edu.ipm.siweb.ui.LoginActivity;

public class CredentialUtil {

    private static boolean authorized = false;

    public static void toggleAuthorizeState() {
        authorized = !authorized;
    }

    public static boolean isAuthorized() {
        return authorized;
    }

    public static void refreshCredential(Context context) {
        if (authorized) return;
        WeakReference<Context> reference = new WeakReference<Context>(context);
        Context mContext = reference.get();

        SharedPreferences sharedPreferences = mContext.getSharedPreferences(context.getString(R.string.pref_credential),
                Context.MODE_PRIVATE);

        String studentID = sharedPreferences.getString("studentID", "");
        String password = sharedPreferences.getString("password", "");

        if (!studentID.isEmpty() && !password.isEmpty()) {
            try {
                JsonDataAdapter.getInstance().login(studentID, password);
                authorized = true;
                return;
            } catch (Exception e) {
            }
        } else {
            // cannot refresh session
            Intent intent = new Intent(mContext, LoginActivity.class);
            mContext.startActivity(intent);
            return;
        }
    }

}

