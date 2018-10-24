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

import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sysdata.widget.accordion.CollapsedViewHolder;
import com.sysdata.widget.accordion.ExpandableItemHolder;
import com.sysdata.widget.accordion.ItemAdapter;

import mo.edu.ipm.cp.siweb.R;

public final class CourseCollapsedViewHolder extends CollapsedViewHolder {

    private TextView mCourseStatistics;
    private TextView mTitleTextView;

    private CourseCollapsedViewHolder(View itemView) {
        super(itemView);

        mTitleTextView = (TextView) itemView.findViewById(R.id.sample_layout_collapsed_title);
        mCourseStatistics = (TextView) itemView.findViewById(R.id.course_statistics);
    }

    @Override
    protected void onBindItemView(ExpandableItemHolder itemHolder) {
        mTitleTextView.setText(((CourseItem) itemHolder.item).getTitle());
        mCourseStatistics.setText(((CourseItem) itemHolder.item).getCourseStatistics());
    }

    @Override
    protected void onRecycleItemView() {
        // do nothing
    }

    @Override
    protected ItemAdapter.ItemViewHolder.Factory getViewHolderFactory() {
        return null;
    }

    public static class Factory implements ItemAdapter.ItemViewHolder.Factory {

        public static Factory create(@LayoutRes int itemViewLayoutId) {
            return new Factory(itemViewLayoutId);
        }

        @LayoutRes
        private final int mItemViewLayoutId;

        Factory(@LayoutRes int itemViewLayoutId) {
            mItemViewLayoutId = itemViewLayoutId;
        }

        @Override
        public ItemAdapter.ItemViewHolder<?> createViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false /* attachToRoot */);
            return new mo.edu.ipm.cp.siweb.Component.CourseCollapsedViewHolder(itemView);
        }

        @Override
        public int getItemViewLayoutId() {
            return mItemViewLayoutId;
        }
    }
}
