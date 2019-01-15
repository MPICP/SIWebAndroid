package mo.edu.ipm.siweb.ui;

import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import mo.edu.ipm.siweb.R;
import mo.edu.ipm.siweb.ui.fragment.FilesFragment;
import mo.edu.ipm.siweb.ui.fragment.MainFragment;
import mo.edu.ipm.siweb.ui.fragment.ProfileFragment;
import mo.edu.ipm.siweb.ui.fragment.ScheduleFragment;

import android.view.MenuItem;
import android.widget.FrameLayout;

public class MainActivity extends AppCompatActivity
        implements FilesFragment.OnFragmentInteractionListener, MainFragment.OnFragmentInteractionListener,
        ScheduleFragment.OnFragmentInteractionListener, ProfileFragment.OnFragmentInteractionListener {

    private FrameLayout mFragmentContainer;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = item -> {
                Fragment fragment = null;
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        fragment = MainFragment.newInstance("test", "test");
                        break;
                    case R.id.navigation_schedule:
                        fragment = ScheduleFragment.newInstance("test", "test");
                        break;
                    case R.id.navigation_files:
                        fragment = FilesFragment.newInstance("test", "test");
                        break;
                    case R.id.navigation_profile:
                        fragment = ProfileFragment.newInstance("test", "test");
                        break;
                }

                if (fragment != null) {
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.fragmentContainer, fragment);
                    fragmentTransaction.commit();
                    return true;
                }

                return false;
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        bindView();
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        initDefaultFragment();
    }

    private void bindView() {
        mFragmentContainer = findViewById(R.id.fragmentContainer);
    }

    private void initDefaultFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();

        ft.replace(R.id.fragmentContainer, new MainFragment());
        ft.commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
