package mo.edu.ipm.siweb.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import mo.edu.ipm.siweb.R;

public class LoginActivity extends AppCompatActivity {

    private FragmentManager mFragmentManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();

        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.pref_credential), Context.MODE_PRIVATE);
        String studentID = sharedPreferences.getString("studentID", "");
        String password = sharedPreferences.getString("password", "");

        fragmentTransaction.replace(R.id.login_container, new LoginFragment());
        fragmentTransaction.commit();
    }
}
