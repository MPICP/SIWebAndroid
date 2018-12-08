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
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sysdata.widget.accordion.CollapsedViewHolder;
import com.sysdata.widget.accordion.ExpandableItemHolder;
import com.sysdata.widget.accordion.ItemAdapter;

import org.w3c.dom.Text;

import mo.edu.ipm.siweb.R;
import mo.edu.ipm.siweb.data.model.GradesAndAbsence;
import mo.edu.ipm.siweb.ui.GradeAndAbsenceListFragment;

public final class GaaCollapsedViewHolder extends CollapsedViewHolder {

    private TextView mDescTextView;
    private TextView mTitleTextView;

    private GaaCollapsedViewHolder(View itemView) {
        super(itemView);

        mTitleTextView = (TextView) itemView.findViewById(R.id.gaa_layout_collapsed_title);
        mDescTextView = itemView.findViewById(R.id.gaa_layout_collapsed_desc);

        itemView.setOnLongClickListener(t -> {
            GradeAndAbsenceListFragment.showDetails((GradesAndAbsence.GradeAndAbsence) getItemHolder().item);
            return true;
        });
    }

    @Override
    protected void onBindItemView(ExpandableItemHolder itemHolder) {
        mTitleTextView.setText(((GradesAndAbsence.GradeAndAbsence) itemHolder.item).getTitle());
        mDescTextView.setText(((GradesAndAbsence.GradeAndAbsence) itemHolder.item).getDescription());
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
            return new GaaCollapsedViewHolder(itemView);
        }

        @Override
        public int getItemViewLayoutId() {
            return mItemViewLayoutId;
        }
    }
}
