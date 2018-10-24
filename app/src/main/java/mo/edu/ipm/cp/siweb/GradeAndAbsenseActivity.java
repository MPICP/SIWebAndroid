package mo.edu.ipm.cp.siweb;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sysdata.widget.accordion.ExpandableItemHolder;
import com.sysdata.widget.accordion.FancyAccordionView;
import com.sysdata.widget.accordion.Item;
import com.sysdata.widget.accordion.ItemAdapter;

import java.util.ArrayList;
import java.util.List;

import mo.edu.ipm.cp.siweb.Component.CourseCollapsedViewHolder;
import mo.edu.ipm.cp.siweb.Component.CourseExpandedViewHolder;
import mo.edu.ipm.cp.siweb.Component.CourseItem;
import mo.edu.ipm.cp.siweb.HTTP.HTTPRequest;
import mo.edu.ipm.cp.siweb.Model.Course;

public class GradeAndAbsenseActivity extends AppCompatActivity {

    // TODO: cannot select academic year

    public final static String TAG = "GradeAndAbsenseActivity";
    private static final String KEY_EXPANDED_ID = "expandedId";

    private static TextView mYearTextView;
    private ArrayList<String> options;
    private AcademicYearDialogFragment dialog = new AcademicYearDialogFragment();
    private static FancyAccordionView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupActionBar();

        setContentView(R.layout.activity_grade_and_absense);

        new Thread(new Runnable(){
            @Override
            public void run() {
                try {
                    setData();
                } catch (Exception e) {
                }
            }
        }).start();

        LinearLayout selectorTrigger = findViewById(R.id.academicYearSelectorTrigger);
        selectorTrigger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show(getFragmentManager(), "Dialog");
                try {
                    loadData();
                } catch (Exception e) {

                }
            }
        });

        mRecyclerView = (FancyAccordionView) findViewById(R.id.grade_and_absense_list);
        mRecyclerView.setCollapsedViewHolderFactory(CourseCollapsedViewHolder.Factory.create(R.layout.simple_layout_collapsed), mListener);
        mRecyclerView.setExpandedViewHolderFactory(CourseExpandedViewHolder.Factory.create(R.layout.simple_layout_expanded), mListener);

        if (savedInstanceState != null) {
            mRecyclerView.setExpandedItemId(savedInstanceState.getLong(KEY_EXPANDED_ID, Item.INVALID_ID));
        }

    }

    private void setData() throws Exception {
        options = DataStorage.getData().getAcademicYearOptions();
        DataStorage.currentSelectedAcademicYear = options.get(0);
        loadData();

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    mYearTextView = findViewById(R.id.academicYearTextView);
                    mYearTextView.setText(DataStorage.currentSelectedAcademicYear);
                } catch (Exception e) {
                    Log.e(TAG, e.toString(), e);
                }
            }
        });
    }

    private void setupActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // Show the Up button in the action bar.
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    public static class AcademicYearDialogFragment extends DialogFragment {
        public static final int DIALOG_FRAGMENT = 1;
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            try {
                final ArrayList<String> options = DataStorage.getData().getAcademicYearOptions();
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Select academic year")
                        .setItems(options.toArray(new String[options.size()]), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                DataStorage.currentSelectedAcademicYear = options.get(which);
                                mYearTextView.setText(DataStorage.currentSelectedAcademicYear);
                            }
                        });
                return builder.create();
            } catch (Exception e) {
                return null;
            }
        }

        // TODO: data cannot be updated while changing academic year
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            loadData();
        } catch (Exception e) {

        }
    }

    private ItemAdapter.OnItemClickedListener mListener = new ItemAdapter.OnItemClickedListener() {
        @Override
        public void onItemClicked(ItemAdapter.ItemViewHolder<?> viewHolder, int id) {
            ItemAdapter.ItemHolder itemHolder = viewHolder.getItemHolder();
            CourseItem item = ((CourseItem) itemHolder.item);
            switch (id) {
                case CourseExpandedViewHolder.SHOW_ATTE_HISTORY_BTN_ONCLICK:
                    Intent intent = new Intent(GradeAndAbsenseActivity.this, AttendenceHistoryActivity.class);
                    intent.putExtra("cod", item.getCod());
                    intent.putExtra("year", item.getYear());
                    intent.putExtra("courseName", item.getTitle());
                    startActivity(intent);
                    break;
            }
        }
    };


    private void loadData() throws Exception {
        final Course[] courses = HTTPRequest.getInstance().getGradesAndAbsense(DataStorage.currentSelectedAcademicYear);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    final List<ExpandableItemHolder> itemHolders = new ArrayList<>();
                    Item itemModel;
                    ExpandableItemHolder itemHolder;
                    for (Course course : courses) {
                        itemModel = CourseItem.create(course);
                        itemHolder = new ExpandableItemHolder(itemModel);
                        itemHolders.add(itemHolder);
                    }

                    mRecyclerView.setAdapterItems(itemHolders);
                    mRecyclerView.setVisibility(View.GONE);
                    mRecyclerView.setVisibility(View.VISIBLE);
                } catch (Exception e) {
                    Log.e(TAG, e.toString(), e);
                }
            }
        });
    }

}

