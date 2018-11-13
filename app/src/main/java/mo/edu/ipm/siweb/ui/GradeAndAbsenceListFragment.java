package mo.edu.ipm.siweb.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sysdata.widget.accordion.ExpandableItemHolder;
import com.sysdata.widget.accordion.FancyAccordionView;
import com.sysdata.widget.accordion.Item;
import com.sysdata.widget.accordion.ItemAdapter;

import java.util.ArrayList;
import java.util.List;

import mo.edu.ipm.siweb.R;
import mo.edu.ipm.siweb.ui.common.GaaCollapsedViewHolder;
import mo.edu.ipm.siweb.ui.common.GaaExpandedViewHolder;
import mo.edu.ipm.siweb.ui.common.GaaItem;

public class GradeAndAbsenceListFragment extends Fragment {

    public static final String ARG_OBJECT = "GaaList";
    private static final String KEY_EXPANDED_ID = "GaaExpandedId";
    private FancyAccordionView mRecyclerView;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_grade_and_absence_list, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRecyclerView = (FancyAccordionView) view.findViewById(R.id.gaa_accordion_view);
        mRecyclerView.setCollapsedViewHolderFactory(GaaCollapsedViewHolder.Factory.create(R.layout.gaa_layout_collapsed), mListener);
        mRecyclerView.setExpandedViewHolderFactory(GaaExpandedViewHolder.Factory.create(R.layout.gaa_layout_expanded), mListener);

        if (savedInstanceState != null) {
            mRecyclerView.setExpandedItemId(savedInstanceState.getLong(KEY_EXPANDED_ID, Item.INVALID_ID));
        }

        loadData();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong(KEY_EXPANDED_ID, mRecyclerView.getExpandedItemId());
    }

    private ItemAdapter.OnItemClickedListener mListener = new ItemAdapter.OnItemClickedListener() {
        @Override
        public void onItemClicked(ItemAdapter.ItemViewHolder<?> viewHolder, int id) {
            ItemAdapter.ItemHolder itemHolder = viewHolder.getItemHolder();
            GaaItem item = ((GaaItem) itemHolder.item);

            switch (id) {
                case ItemAdapter.OnItemClickedListener.ACTION_ID_COLLAPSED_VIEW:
                    break;
                case ItemAdapter.OnItemClickedListener.ACTION_ID_EXPANDED_VIEW:
                    break;
                default:
                    // do nothing
                    break;
            }
        }
    };

    private String getItemTitle(int position) {
        return String.format("Item %d", position + 1);
    }

    private String getItemDescription(int position) {
        return String.format("Hello World, I'm an expandable item!", position);
    }

    private void loadData() {
        final int dataCount = 50;
        int index = 0;

        final List<ExpandableItemHolder> itemHolders = new ArrayList<>(dataCount);
        Item itemModel;
        ExpandableItemHolder itemHolder;
        for (; index < dataCount; index++) {
            itemModel = GaaItem.create(getItemTitle(index), getItemDescription(index));
            itemHolder = new ExpandableItemHolder(itemModel);
            itemHolders.add(itemHolder);
        }

        mRecyclerView.setAdapterItems(itemHolders);
    }

}
