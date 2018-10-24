/*
 * Copyright (C) 2017 Sysdata Spa.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package mo.edu.ipm.cp.siweb.Component;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.LayoutRes;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sysdata.widget.accordion.ExpandableItemHolder;
import com.sysdata.widget.accordion.ExpandedViewHolder;
import com.sysdata.widget.accordion.ItemAdapter;

import mo.edu.ipm.cp.siweb.AttendenceHistoryActivity;
import mo.edu.ipm.cp.siweb.GradeAndAbsenseActivity;
import mo.edu.ipm.cp.siweb.HTTP.HTTPRequest;
import mo.edu.ipm.cp.siweb.Model.AttendenceHistoryItem;
import mo.edu.ipm.cp.siweb.Model.Course;
import mo.edu.ipm.cp.siweb.R;

import static android.app.PendingIntent.getActivity;

/**
 * Created on 06/04/17.
 *
 * @author Umberto Marini
 */
public final class CourseExpandedViewHolder extends ExpandedViewHolder {

    private static final String TAG = "CourseExpandedViewHolder";
    private TextView mCourseStatistics;
    private TextView mTitleTextView;
    private TextView mDescriptionTextView;
    private Button mButtonHistory;
    private String cod;
    private String year;
    public static final int SHOW_ATTE_HISTORY_BTN_ONCLICK = 3;

    private CourseExpandedViewHolder(View itemView) {
        super(itemView);

        mTitleTextView = (TextView) itemView.findViewById(R.id.simple_layout_expanded_title);
        mDescriptionTextView = (TextView) itemView.findViewById(R.id.simple_layout_expanded_description);
        mCourseStatistics = (TextView) itemView.findViewById(R.id.simple_layout_expanded_course_statistics);
        mButtonHistory = (Button) itemView.findViewById(R.id.button_attendence_history);
        mButtonHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                notifyItemClicked(SHOW_ATTE_HISTORY_BTN_ONCLICK);
            }
        });
    }

    @Override
    protected void onBindItemView(ExpandableItemHolder itemHolder) {
        mTitleTextView.setText(((CourseItem) itemHolder.item).getTitle());
        mDescriptionTextView.setText(((CourseItem) itemHolder.item).getDescription());
        mCourseStatistics.setText(((CourseItem) itemHolder.item).getCourseStatistics());
        cod = ((CourseItem) itemHolder.item).getCod();
        year = ((CourseItem) itemHolder.item).getYear();
    }

    @Override
    protected void onRecycleItemView() {

    }

    @Override
    protected ItemAdapter.ItemViewHolder.Factory getViewHolderFactory() {
        return null;
    }

    public static class Factory implements ItemAdapter.ItemViewHolder.Factory {

        public static mo.edu.ipm.cp.siweb.Component.CourseExpandedViewHolder.Factory create(@LayoutRes int itemViewLayoutId) {
            return new Factory(itemViewLayoutId);
        }

        @LayoutRes
        private final int mItemViewLayoutId;

        public Factory(@LayoutRes int itemViewLayoutId) {
            mItemViewLayoutId = itemViewLayoutId;
        }

        @Override
        public ItemAdapter.ItemViewHolder<?> createViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false /* attachToRoot */);
            return new mo.edu.ipm.cp.siweb.Component.CourseExpandedViewHolder(itemView);
        }

        @Override
        public int getItemViewLayoutId() {
            return mItemViewLayoutId;
        }
    }
}
