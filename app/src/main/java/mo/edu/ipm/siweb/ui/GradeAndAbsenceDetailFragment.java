package mo.edu.ipm.siweb.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import mo.edu.ipm.siweb.R;
import mo.edu.ipm.siweb.data.model.GradesAndAbsence;

public class GradeAndAbsenceDetailFragment extends Fragment {

    public static final String ARG_TITLE = "title";
    public static final String ARG_COD = "cod";
    public static final String ARG_SEMYEAR = "semYear";
    private List<GradesAndAbsence.AttendenceRecord> mAtteHistory;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_grade_and_absence_detail, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        setTitle(getArguments().getString(GradeAndAbsenceDetailFragment.ARG_TITLE));

        RecyclerView rv = view.findViewById(R.id.recycler_atte_history);

        GradesAndAbsence gradesAndAbsence = ViewModelProviders.of(this).get(GradesAndAbsence.class);

        String sem = getArguments().getString(ARG_SEMYEAR);
        String cod = getArguments().getString(ARG_COD);

        gradesAndAbsence.getAttendenceHistory(sem, cod).observe(this, t -> {
            mAtteHistory = t;
            rv.setLayoutManager(new LinearLayoutManager(getContext()));
            rv.setHasFixedSize(true);
            rv.setAdapter(new AtteRecordsAdapter(mAtteHistory));
        });
    }

    private void setTitle(String title) {
        int i = 0;
        for (; i != title.length(); ++i) {
            if (Character.isIdeographic(title.charAt(i)))
                break;
        }

        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        actionBar.setTitle(title.substring(0, i - 1));
        actionBar.setSubtitle(title.substring(i));
    }

    public class AtteViewHolder extends RecyclerView.ViewHolder {

        public TextView duration;
        public TextView date;
        public TextView status;

        public AtteViewHolder(View itemView) {
            super(itemView);
            duration = itemView.findViewById(R.id.atte_duration);
            date = itemView.findViewById(R.id.atte_date);
            status = itemView.findViewById(R.id.atte_status);
        }
    }

    public class AtteRecordsAdapter extends RecyclerView.Adapter<AtteViewHolder> {

        private List<GradesAndAbsence.AttendenceRecord> mAttendenceHistory;

        public AtteRecordsAdapter(List<GradesAndAbsence.AttendenceRecord> ar) {
            mAttendenceHistory = ar;
        }

        @NonNull
        @Override
        public AtteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_item_gaa_atte_record, parent, false);
            AtteViewHolder vh = new AtteViewHolder(v);
            return vh;
        }

        @Override
        public void onBindViewHolder(@NonNull AtteViewHolder holder, int position) {
            holder.duration.setText(mAttendenceHistory.get(position).getDuration());
            holder.date.setText(mAttendenceHistory.get(position).getDate());
            String status = mAttendenceHistory.get(position).getStatus();
            if (status.equals("Yes")) {
                holder.status.setTextColor(Color.GREEN);
            } else if (status.equals("Unreasonable Absence")) {
                holder.status.setTextColor(Color.RED);
            }
            holder.status.setText(status);
        }

        @Override
        public int getItemCount() {
            return mAtteHistory.size();
        }
    }
}
