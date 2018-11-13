package mo.edu.ipm.siweb.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import mo.edu.ipm.siweb.R;

public class GradeAndAbsenceFragment extends Fragment {

    private GaaPageAdapter mGaaPageAdapter;
    private ViewPager mViewPager;
    private TabLayout mTabLayout;

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
        mViewPager.setAdapter(mGaaPageAdapter);
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
                mViewPager.setCurrentItem(tab.getPosition());
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
            // Our object is just an integer :-P
            args.putInt(GradeAndAbsenceListFragment.ARG_OBJECT, i + 1);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public int getCount() {
            return 5;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return "OBJECT " + (position + 1);
        }
    }
}
