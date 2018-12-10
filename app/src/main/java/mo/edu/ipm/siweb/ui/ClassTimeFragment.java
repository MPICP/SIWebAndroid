package mo.edu.ipm.siweb.ui;

import android.app.ProgressDialog;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.graphics.RectF;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.alamkanak.weekview.WeekView;
import com.alamkanak.weekview.WeekViewEvent;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import mo.edu.ipm.siweb.R;
import mo.edu.ipm.siweb.data.model.ClassTimeViewModel;
import mo.edu.ipm.siweb.util.CredentialUtil;

public class ClassTimeFragment extends Fragment {

    private WeekView mWeekView;
    private ClassTimeViewModel mViewModel;
    private List<WeekViewEvent> mWeekViewEvents;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_class_time, null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mWeekView = (WeekView) view.findViewById(R.id.weekView);
        mWeekView.goToHour(7);
        mWeekView.setShowNowLine(true);
        mWeekView.setMonthChangeListener((newYear, newMonth) -> {
            List<WeekViewEvent> lst = mViewModel.getEvents(newYear, newMonth);
            CredentialUtil.refreshCredential(getContext());
            return lst;
        });

        mWeekView.setOnEventClickListener((event, eventRect) -> {
            Toast.makeText(getContext(), event.getId() + " " + event.getName() + "\n" +
                            event.getStartTime().getTime().toString() + "\n" + event.getEndTime().getTime().toString(),
                    Toast.LENGTH_LONG).show();
        });
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(ClassTimeViewModel.class);
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle(R.string.title_class_time);
    }
}
