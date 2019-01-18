package mo.edu.ipm.siweb.ui.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import mo.edu.ipm.siweb.R;
import mo.edu.ipm.siweb.data.model.ClassTime;
import mo.edu.ipm.siweb.data.model.Event;
import mo.edu.ipm.siweb.data.viewmodel.MainViewModel;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MainFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ViewGroup mCardContainer;

    private OnFragmentInteractionListener mListener;
    private Toolbar mToolbar;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private View mEmptyView;
    private MainViewModel mViewModel;

    public MainFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MainFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MainFragment newInstance(String param1, String param2) {
        MainFragment fragment = new MainFragment();
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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bindView(view);
        setUpActionBar();
        loadCards();
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onRefresh() {
        loadCards();
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
        mCardContainer = view.findViewById(R.id.cards_container);
//        mEmptyView = ((ViewStub) view.findViewById(R.id.material_listview_empty)).inflate();
        mToolbar = view.findViewById(R.id.toolbar);
        mSwipeRefreshLayout = view.findViewById(R.id.pullToRefresh);

        mSwipeRefreshLayout.setOnRefreshListener(this);

        mViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
    }

    private void setUpActionBar() {
        mToolbar.setTitle(R.string.app_name);
        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
    }

    private void loadCards() {
        mSwipeRefreshLayout.setRefreshing(true);
        mViewModel.getOnGoingClass().observe(this, t -> {
            if (t != null) {
                mCardContainer.removeAllViews();
                for (Event classTime : t) {
                    classTime.getName();
                    View v = LayoutInflater.from(getContext()).inflate(R.layout.material_card_class, null);
                    TextView textView = v.findViewById(R.id.textViewClassName);
                    textView.setText(classTime.getName());
                    textView = v.findViewById(R.id.textViewTime);
                    textView.setText(classTime.getStartTime() + " - " + classTime.getEndTime());
                    textView = v.findViewById(R.id.textClassStatus);
                    switch (classTime.getStatus()) {
                        case Event.ONGOING:
                            textView.setText("● Ongoing");
                            break;
                        case Event.SCHEDULED:
                            textView.setText("● Scheduled");
                            break;
                        case Event.CANCELLED:
                            textView.setText("● Cancelled");
                            break;
                    }
                    mCardContainer.addView(v);
                }
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
    }
}
