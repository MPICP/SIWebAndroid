package mo.edu.ipm.siweb.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import org.json.JSONException;
import org.jsoup.HttpStatusException;

import java.io.IOException;

import mo.edu.ipm.siweb.R;
import mo.edu.ipm.siweb.data.remote.JsonDataAdapter;

public class LoginActivity extends AppCompatActivity {

    private boolean isLogged = true;
    private FragmentManager mFragmentManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();

        SharedPreferences sharedPreferences = getPreferences(Context.MODE_PRIVATE);
        String studentID = sharedPreferences.getString("studentID", "");
        String password = sharedPreferences.getString("password", "");

        if (!studentID.isEmpty() || !password.isEmpty()) {
            fragmentTransaction.replace(R.id.login_container, new SplashFragment());
            fragmentTransaction.commit();
            new LoginTask().execute(studentID, password);
        } else {
            fragmentTransaction.replace(R.id.login_container, new LoginFragment());
            fragmentTransaction.commit();
        }
    }

    public class LoginTask extends AsyncTask<String, Void, Integer> {

        public final Integer LOGIN_SUCCESS = 1;
        public final Integer LOGIN_FAILURE = 2;
        public final Integer LOGIN_IO_FAILURE = 3;

        @Override
        protected Integer doInBackground(String... strings) {
            try {
                JsonDataAdapter jsonDataAdapter = JsonDataAdapter.getInstance();
                return jsonDataAdapter.login(strings[0], strings[1]).getBoolean("login") ? LOGIN_SUCCESS : LOGIN_FAILURE;
            } catch (HttpStatusException hse) {
                if (hse.getStatusCode() == 401)
                    return LOGIN_FAILURE;
            } catch (IOException ioe) {
                return LOGIN_IO_FAILURE;
            } catch (JSONException jsone) {
                return LOGIN_IO_FAILURE;
            }
            return LOGIN_SUCCESS;
        }

        @Override
        protected void onPostExecute(Integer result) {
            if (result == LOGIN_SUCCESS) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_TASK_ON_HOME);
                startActivity(intent);
                finish();
                // JUMP TO ACTIVITY
            } else {
                String message = "";
                if (result == LOGIN_FAILURE) {
                    message = "Invalid id or password";
                } else if (result == LOGIN_IO_FAILURE) {
                    message = "An error occured while login";
                }
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT);
                FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.login_container, new LoginFragment());
                fragmentTransaction.commit();
            }

        }
    }
}
