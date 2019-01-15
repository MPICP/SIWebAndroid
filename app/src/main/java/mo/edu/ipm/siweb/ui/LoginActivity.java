package mo.edu.ipm.siweb.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import mo.edu.ipm.siweb.R;
import mo.edu.ipm.siweb.ui.fragment.LoginFragment;

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

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(1);
    }
}
