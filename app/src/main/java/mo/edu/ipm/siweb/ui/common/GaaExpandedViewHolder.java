
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

package mo.edu.ipm.siweb.ui.common;

import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sysdata.widget.accordion.ExpandableItemHolder;
import com.sysdata.widget.accordion.ExpandedViewHolder;
import com.sysdata.widget.accordion.ItemAdapter;

import org.w3c.dom.Text;

import mo.edu.ipm.siweb.R;
import mo.edu.ipm.siweb.data.model.GradesAndAbsence;
import mo.edu.ipm.siweb.ui.GradeAndAbsenceListFragment;

/**
 * Created on 06/04/17.
 *
 * @author Umberto Marini
 */
public final class GaaExpandedViewHolder extends ExpandedViewHolder {

    private TextView mTitleTextView;
    private TextView mDescriptionTextView;
    private TextView mLastEntryInfoView;
    private TextView mInfoTextView;

    private GaaExpandedViewHolder(View itemView) {
        super(itemView);

        mTitleTextView = (TextView) itemView.findViewById(R.id.gaa_layout_expanded_title);
        mDescriptionTextView = (TextView) itemView.findViewById(R.id.gaa_layout_expanded_desc);
        mInfoTextView = (TextView) itemView.findViewById(R.id.gaa_layout_expanded_grade);
        mLastEntryInfoView = (TextView) itemView.findViewById(R.id.gaa_layout_expanded_last_entry);

        itemView.setOnLongClickListener(t -> {
            GradeAndAbsenceListFragment.showDetails((GradesAndAbsence.GradeAndAbsence) getItemHolder().item);
            return true;
        });
    }

    @Override
    protected void onBindItemView(ExpandableItemHolder itemHolder) {
        mTitleTextView.setText(((GradesAndAbsence.GradeAndAbsence) itemHolder.item).getTitle());
        mDescriptionTextView.setText(((GradesAndAbsence.GradeAndAbsence) itemHolder.item).getDescription());
        mInfoTextView.setText(((GradesAndAbsence.GradeAndAbsence) itemHolder.item).getInfo());
        String lastEntry = ((GradesAndAbsence.GradeAndAbsence) itemHolder.item).getLastEntryDate();
        if (lastEntry == null) {
            mLastEntryInfoView.setVisibility(View.GONE);
        } else {
            mLastEntryInfoView.setText(lastEntry);
        }
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

        public static GaaExpandedViewHolder.Factory create(@LayoutRes int itemViewLayoutId) {
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
            return new GaaExpandedViewHolder(itemView);
        }

        @Override
        public int getItemViewLayoutId() {
            return mItemViewLayoutId;
        }
    }
}
