package mo.edu.ipm.siweb.ui;

import android.app.ProgressDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONException;

import mo.edu.ipm.siweb.R;
import mo.edu.ipm.siweb.data.model.GradesAndAbsence;
import mo.edu.ipm.siweb.util.CredentialUtil;

public class GradeAndAbsenceFragment extends Fragment {
    // TODO save the state of this fragment

    private GaaPageAdapter mGaaPageAdapter;
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private GradesAndAbsence mGaaViewModel;
    private JSONArray mYears;
    private static String TAG = "GradeAndAbsenceFragment";

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_grade_and_absence, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        mGaaPageAdapter =
                new GaaPageAdapter(
                        getChildFragmentManager());
        mViewPager = (ViewPager) view.findViewById(R.id.gaa_pager_view);
        mTabLayout = view.findViewById(R.id.gaa_tabs);


        mGaaViewModel = ViewModelProviders.of(this).get(GradesAndAbsence.class);
        ProgressDialog dialog = ProgressDialog.show(getContext(), "",
                "Loading. Please wait...", true);
        mGaaViewModel.getYears().observe(this, t -> {
            try {
                if (t.length() == 0) return;
                for (int i = 0; i != t.length(); ++i)
                    mTabLayout.addTab(mTabLayout.newTab().setText(t.getString(i)));
                mYears = t;
                initViews();
                dialog.dismiss();
                CredentialUtil.refreshCredential(getContext());
            } catch (JSONException jsone) {
                Snackbar.make(getView(), "Error retrieving years", Snackbar.LENGTH_LONG);
            }
        });
    }

    private void initViews() {
        mViewPager.setAdapter(mGaaPageAdapter);
        mViewPager.setOffscreenPageLimit(mGaaPageAdapter.getCount());
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                TabLayout.Tab tab = mTabLayout.getTabAt(position);
                tab.select();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition(), true);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle(R.string.title_grade_and_absence);
    }


    public class GaaPageAdapter extends FragmentStatePagerAdapter {
        public GaaPageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            Fragment fragment = new GradeAndAbsenceListFragment();
            Bundle args = new Bundle();
            try {
                args.putString(GradeAndAbsenceListFragment.ARG_YEARS, mYears.getString(i));
            } catch (JSONException jsone) {

            }
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public int getCount() {
            return mYears.length();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            try {
                return mYears.getString(position);
            } catch (JSONException je) {
                return "NULL";
            }
        }
    }
}
