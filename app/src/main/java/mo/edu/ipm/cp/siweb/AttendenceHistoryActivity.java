package mo.edu.ipm.cp.siweb;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import mo.edu.ipm.cp.siweb.HTTP.HTTPRequest;
import mo.edu.ipm.cp.siweb.Model.AttendenceHistoryItem;

public class AttendenceHistoryActivity extends AppCompatActivity {

    private static final String TAG = "AttendenceHistoryActivity";
    private AttendenceHistoryItem[] attendenceHistory;
    private String cod;
    private String year;
    private String courseName;
    private LinearLayout mAtteHistoryContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendence_history);

        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            return;
        }

        cod = extras.getString("cod");
        courseName = extras.getString("courseName");
        year = extras.getString("year");

        setTitle(courseName);
        setupActionBar();

        mAtteHistoryContainer = findViewById(R.id.atte_history_container);

        new AtteHistory(this).execute();
    }

    private void setupActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // Show the Up button in the action bar.
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    public class AtteHistory extends AsyncTask<Void, Void, Void>
    {

        private Context mContext;

        public AtteHistory (Context context){
            mContext = context;
        }

        @Override
        protected Void doInBackground(Void... params)
        {
            try {
                attendenceHistory = HTTPRequest.getInstance().getAttendenceHistory(year, cod);
            } catch (Exception e) {
                Log.e(TAG, e.toString(), e);
            }
            return null;
        }

        protected void onPostExecute (Void v) {
            for (AttendenceHistoryItem item : attendenceHistory) {
                TextView tv = new TextView(mContext);
                tv.setText(item.date + ' ' + item.time + ' ' +  item.hour + "   " + item.present + ' ' + item.approvalResult);
                mAtteHistoryContainer.addView(tv);
            }
        }

    }
}
