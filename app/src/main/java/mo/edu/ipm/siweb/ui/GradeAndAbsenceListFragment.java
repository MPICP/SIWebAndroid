package mo.edu.ipm.siweb.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.sysdata.widget.accordion.ExpandableItemHolder;
import com.sysdata.widget.accordion.FancyAccordionView;
import com.sysdata.widget.accordion.Item;

import java.util.ArrayList;
import java.util.List;

import mo.edu.ipm.siweb.R;
import mo.edu.ipm.siweb.data.model.GradesAndAbsence;
import mo.edu.ipm.siweb.ui.common.GaaCollapsedViewHolder;
import mo.edu.ipm.siweb.ui.common.GaaExpandedViewHolder;
import mo.edu.ipm.siweb.util.CredentialUtil;

public class GradeAndAbsenceListFragment extends Fragment {

    public static final String ARG_YEARS = "GaaListYear";
    private static final String KEY_EXPANDED_ID = "GaaExpandedId";
    private FancyAccordionView mRecyclerView;
    private GradesAndAbsence mGaaViewModel;
    private ProgressBar mSpinnerView;
    private static Context mContext;

    public static void showDetails(GradesAndAbsence.GradeAndAbsence gaa) {
        if (gaa.isFinished()) {
            Toast.makeText(mContext, "You have already completed this course", Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(mContext, GradeAndAbsenceDetailActivity.class);
            intent.putExtra(GradeAndAbsenceDetailFragment.ARG_TITLE, gaa.getTitle());
            intent.putExtra(GradeAndAbsenceDetailFragment.ARG_COD, gaa.getCod());
            intent.putExtra(GradeAndAbsenceDetailFragment.ARG_SEMYEAR, gaa.getSemYear());
            mContext.startActivity(intent);
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_grade_and_absence_list, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRecyclerView = (FancyAccordionView) view.findViewById(R.id.gaa_accordion_view);
        mSpinnerView = (ProgressBar) view.findViewById(R.id.gaa_list_progress);
        mRecyclerView.setCollapsedViewHolderFactory(GaaCollapsedViewHolder.Factory.create(R.layout.gaa_layout_collapsed));
        mRecyclerView.setExpandedViewHolderFactory(GaaExpandedViewHolder.Factory.create(R.layout.gaa_layout_expanded));

        if (savedInstanceState != null) {
            mRecyclerView.setExpandedItemId(savedInstanceState.getLong(KEY_EXPANDED_ID, Item.INVALID_ID));
        }

        mGaaViewModel = ViewModelProviders.of(this).get(GradesAndAbsence.class);

        mContext = getActivity().getApplicationContext();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        setRetainInstance(true);
        super.onActivityCreated(savedInstanceState);
        loadData(getArguments().getString(ARG_YEARS));
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong(KEY_EXPANDED_ID, mRecyclerView.getExpandedItemId());
    };

    private void loadData(String year) {
        List<ExpandableItemHolder> items = new ArrayList<>();
        mRecyclerView.setVisibility(View.INVISIBLE);
        mGaaViewModel.getGradeAndAbsence(year).observe(this, t -> {
            for (GradesAndAbsence.GradeAndAbsence e : t) {
                ExpandableItemHolder itemHolder;
                itemHolder = new ExpandableItemHolder(e);
                items.add(itemHolder);
            }

            mRecyclerView.setAdapterItems(items);
            mRecyclerView.setVisibility(View.VISIBLE);
            mSpinnerView.setVisibility(View.GONE);
            CredentialUtil.refreshCredential(getContext());
        });
    }

}
