package mo.edu.ipm.cp.siweb;

import android.app.ActionBar;
import android.content.ClipData;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.concurrent.ExecutionException;

import mo.edu.ipm.cp.siweb.Component.ProfileItem;
import mo.edu.ipm.cp.siweb.HTTP.HTTPRequest;
import mo.edu.ipm.cp.siweb.Model.Profile;

public class ProfileActivity extends AppCompatActivity {

    public final static String TAG = "ProfileActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        try {
            LinearLayout scrollView = findViewById(R.id.profile_scroll);
            addItems(scrollView);
        } catch (Exception e) {
            Log.e(TAG, e.toString(), e);
        }
    }

    private TextView createLable(String title) {
        TextView lable = new TextView(this);
        lable.setText(title);
        lable.setTextColor(getColor(R.color.colorPrimary));
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );

        float dpRatio = this.getResources().getDisplayMetrics().density;
        int px10 = (int) (10 * dpRatio);

        params.setMargins(px10, px10, px10, px10);
        lable.setLayoutParams(params);
        return lable;
    }

    private void addItems(LinearLayout scrollView) throws Exception{
        Profile profile = DataStorage.getData().getProfile();
        scrollView.addView(createLable("Personal"));
        addItem(scrollView, "Name", profile.name);
        addItem(scrollView, "Student ID", profile.id);
        addItem(scrollView, "School", profile.school);

        scrollView.addView(createLable("Course"));
        addItem(scrollView, "Program Name", profile.programName);
        addItem(scrollView, "Program Code", profile.programCode);
        addItem(scrollView, "Section", profile.section);
        addItem(scrollView, "Language", profile.language);
        addItem(scrollView, "Status", profile.status);
        addItem(scrollView, "Mode", profile.mode);

        scrollView.addView(createLable("Achievements"));
        if (profile.major != "")
            addItem(scrollView, "Major", profile.major);
        addItem(scrollView, "GPA", profile.gpa);
        addItem(scrollView, "Obtained Credits", profile.obtainedCredits);

        scrollView.addView(createLable("Miscellaneous"));
        addItem(scrollView, "HK & Macau Permit No.", profile.HKMOPermitNO);
        addItem(scrollView, "Father's name", profile.fname);
        addItem(scrollView, "Mother's name", profile.mname);
    }

    private void addItem(LinearLayout scrollView, String key, String value) {
        View scrollItem = new ProfileItem(this);
        TextView keyView = scrollItem.findViewById(R.id.key);
        keyView.setText(key);
        TextView valueView = scrollItem.findViewById(R.id.value);
        valueView.setText(value);
        scrollView.addView(scrollItem);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
