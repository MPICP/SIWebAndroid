package mo.edu.ipm.siweb.ui.fragment;

import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import mo.edu.ipm.siweb.R;
import mo.edu.ipm.siweb.data.model.ExamTime;
import mo.edu.ipm.siweb.data.viewmodel.ExamTimeViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ExamFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ExamFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ExamFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private RecyclerView mRecyclerView;
    private ExamTimeViewModel mViewModel;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ExamTimeRecyclerAdapter mAdapter;

    public ExamFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ExamFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ExamFragment newInstance(String param1, String param2) {
        ExamFragment fragment = new ExamFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_exam, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bindView(view);
        bindViewModel();
        setUpRecyclerView();
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onRefresh() {
        refreshData();
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private void bindView(View view) {
        mRecyclerView = view.findViewById(R.id.recycler_exam_time);
        mSwipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout);
        mSwipeRefreshLayout.setOnRefreshListener(this);
    }

    private void bindViewModel() {
        mViewModel = ViewModelProviders.of(this).get(ExamTimeViewModel.class);
    }

    private void setUpRecyclerView() {
        mAdapter = new ExamTimeRecyclerAdapter();
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setHasFixedSize(true);
        refreshData();
    }

    private void refreshData() {
        mSwipeRefreshLayout.setRefreshing(true);
        mViewModel.getExamTime().observe(this, t -> {
            if (t != null) {
                mAdapter.setExamTime(t);
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
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

        private List<ExamTime> examTime = new ArrayList<>();
        private SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        private SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:mm");

        public void setExamTime(List<ExamTime> examTime) {
            this.examTime = examTime;
            this.notifyDataSetChanged();
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
            holder.subject.setText(examTime.get(position).getName());
            holder.time.setText(formatter.format(examTime.get(position).getStartedAt()) + "-" + timeFormatter.format(examTime.get(position).getEndedAt()));
            holder.venue.setText(examTime.get(position).getLocation());

//            if (examTime.get(position).getEndedAt().isBefore(LocalDateTimeEx.getNow())){
//                holder.subject.setTextColor(Color.GRAY);
//            }
        }

        @Override
        public int getItemCount() {
            return examTime.size();
        }
    }
}
