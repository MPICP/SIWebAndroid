package mo.edu.ipm.siweb.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lucasurbas.listitemview.ListItemView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceFragmentCompat;
import androidx.recyclerview.widget.RecyclerView;
import mo.edu.ipm.siweb.R;
import mo.edu.ipm.siweb.ui.SecondaryActivity;

import static mo.edu.ipm.siweb.ui.SecondaryActivity.ARG_FRAGMENT;
import static mo.edu.ipm.siweb.ui.SecondaryActivity.FRAGMENT_GRADE;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ProfileFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

    public static final String TAG = "ProfileFragment";
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private Toolbar mToolbar;

    private ListItemView mListItemGrade;
    private ListItemView mListItemAbsence;
    private ListItemView mListItemPayments;
    private ListItemView mListItemVenue;
    private ListItemView mListItemDoc;
    private ListItemView mListItemSettings;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
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
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bindView(view);
        setUpActionBar();
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

    private void bindView(View view) {
        mToolbar = view.findViewById(R.id.toolbar);
        mListItemGrade = view.findViewById(R.id.list_item_grade);
        mListItemAbsence = view.findViewById(R.id.list_item_absence);
        mListItemPayments = view.findViewById(R.id.list_item_payments);
        mListItemVenue = view.findViewById(R.id.list_item_venue);
        mListItemDoc = view.findViewById(R.id.list_item_requisition_docs);
        mListItemSettings = view.findViewById(R.id.list_item_settings);

        mListItemGrade.setOnClickListener(mOnListItemClickedListener);
        mListItemAbsence.setOnClickListener(mOnListItemClickedListener);
    }

    private void setUpActionBar() {
        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
        mToolbar.setTitle("Profile");
    }

    private ListItemView.OnClickListener mOnListItemClickedListener = item -> {

        int fragment = 0;

        switch (item.getId()) {
            case R.id.list_item_grade:
                fragment = FRAGMENT_GRADE;
                break;
            case R.id.list_item_absence:
                break;
            case R.id.list_item_payments:
                break;
            case R.id.list_item_venue:
                break;
            case R.id.list_item_requisition_docs:
                break;
            case R.id.list_item_settings:
                break;
        }

        Intent intent = new Intent(getActivity(), SecondaryActivity.class);

        if (fragment != 0) {
            intent.putExtra(ARG_FRAGMENT, fragment);
            startActivity(intent);
        }
    };


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
}
