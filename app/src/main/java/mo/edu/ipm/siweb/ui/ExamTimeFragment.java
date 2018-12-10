package mo.edu.ipm.siweb.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import mo.edu.ipm.siweb.R;
import mo.edu.ipm.siweb.data.model.ExamTimeViewModel;
import mo.edu.ipm.siweb.util.CredentialUtil;

public class ExamTimeFragment extends Fragment {

    private static final String TAG = "ExamTimeFragment";
    private ExamTimeViewModel mViewModel;
    private RecyclerView rv;

    public static ExamTimeFragment newInstance() {
        return new ExamTimeFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_exam_time, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rv = view.findViewById(R.id.recycler_exam_time);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(ExamTimeViewModel.class);

        mViewModel.getExamTime().observe(this, t -> {
            rv.setAdapter(new ExamTimeRecyclerAdapter(t));
            rv.setLayoutManager(new LinearLayoutManager(getContext()));
            rv.setHasFixedSize(true);

            CredentialUtil.refreshCredential(getContext());
            Log.i(TAG, "Credential util refreshed, status " + CredentialUtil.isAuthorized());
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle(R.string.title_exam_time);
    }

    private class ExamTimeViewHolder extends RecyclerView.ViewHolder {

        public TextView time;
        public TextView subject;
        public TextView venue;

        public ExamTimeViewHolder(View itemView) {
            super(itemView);
            time = itemView.findViewById(R.id.exam_time);
            subject = itemView.findViewById(R.id.exam_subject);
            venue = itemView.findViewById(R.id.exam_venue);
        }
    }

    public class ExamTimeRecyclerAdapter extends RecyclerView.Adapter<ExamTimeViewHolder> {

        private List<ExamTimeViewModel.ExamTime> examTime;

        public ExamTimeRecyclerAdapter(List<ExamTimeViewModel.ExamTime> t) {
            examTime = t;
        }

        @NonNull
        @Override
        public ExamTimeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_item_exam_time, parent, false);
            ExamTimeViewHolder vh = new ExamTimeViewHolder(v);
            return vh;
        }

        @Override
        public void onBindViewHolder(@NonNull ExamTimeViewHolder holder, int position) {
            holder.subject.setText(examTime.get(position).getTitle());
            holder.time.setText(examTime.get(position).getDate() + " " + examTime.get(position).getTime());
            holder.venue.setText(examTime.get(position).getVenue());
        }

        @Override
        public int getItemCount() {
            return examTime.size();
        }
    }

}
