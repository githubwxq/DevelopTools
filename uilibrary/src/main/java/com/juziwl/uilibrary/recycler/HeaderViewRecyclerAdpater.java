package com.juziwl.uilibrary.recycler;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by LK on 2017/6/15.
 * @author LK
 * @modify Neil
 * 动脑学院 版权所有
 */

public class HeaderViewRecyclerAdpater extends RecyclerView.Adapter {
    private ArrayList<FixedViewInfo> mHeaderViewInfos = new ArrayList<>();
    private ArrayList<FixedViewInfo> mFooterViewInfos = new ArrayList<>();
    private RecyclerView.Adapter mAdapter;
    private static final int NUMBER_1000 = 1000;

    public HeaderViewRecyclerAdpater(ArrayList<View> headerViewInfos, ArrayList<View> footerViewInfos, RecyclerView.Adapter adapter) {
        mAdapter = adapter;
        if (mHeaderViewInfos == null) {
            mHeaderViewInfos = new ArrayList<>();
        } else {
            for (View headerViewInfo : headerViewInfos) {
                FixedViewInfo info = new FixedViewInfo();
                info.view = headerViewInfo;
                info.viewType = RecyclerView.INVALID_TYPE - NUMBER_1000 - mHeaderViewInfos.size();
                mHeaderViewInfos.add(info);
            }
        }
        if (mFooterViewInfos == null) {
            mFooterViewInfos = new ArrayList<>();
        } else {
            for (View footerViewInfo : footerViewInfos) {
                FixedViewInfo info = new FixedViewInfo();
                info.view = footerViewInfo;
                info.viewType = RecyclerView.INVALID_TYPE - mFooterViewInfos.size();
                mFooterViewInfos.add(info);
            }
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Headerview
        if (viewType <= RecyclerView.INVALID_TYPE - NUMBER_1000) {
            return new HeaderViewHolder(mHeaderViewInfos.get(RecyclerView.INVALID_TYPE - NUMBER_1000 - viewType).view);
            //FooterView
        } else if (viewType <= RecyclerView.INVALID_TYPE) {
            return new FooterViewHolder(mFooterViewInfos.get(RecyclerView.INVALID_TYPE - viewType).view);
        }
        return mAdapter.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int viewType = getItemViewType(position);
        if (viewType > RecyclerView.INVALID_TYPE) {
            mAdapter.onBindViewHolder(holder, position - mHeaderViewInfos.size());
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (mHeaderViewInfos.size() > position) {
            return mHeaderViewInfos.get(position).viewType;
        }
        if (position >= mHeaderViewInfos.size() + mAdapter.getItemCount()) {
            return mFooterViewInfos.get(position - mHeaderViewInfos.size() - mAdapter.getItemCount()).viewType;
        }
        return mAdapter.getItemViewType(position - mHeaderViewInfos.size());
    }

    @Override
    public int getItemCount() {
        return mAdapter != null ? mAdapter.getItemCount() + mFooterViewInfos.size() + mHeaderViewInfos.size() :
                mFooterViewInfos.size() + mHeaderViewInfos.size();
    }

    public void addHeaderView(View view) {
        FixedViewInfo info = new FixedViewInfo();
        info.view = view;
        info.viewType = RecyclerView.INVALID_TYPE - NUMBER_1000 - mHeaderViewInfos.size();
        mHeaderViewInfos.add(info);
        notifyItemInserted(mHeaderViewInfos.size() - 1);
    }

    public void removeHeaderView(View view) {
        for (int i = mHeaderViewInfos.size() - 1; i >= 0; i--) {
            if (mHeaderViewInfos.get(i).view == view) {
                mHeaderViewInfos.remove(i);
                notifyItemRemoved(mHeaderViewInfos.size());
                break;
            }
        }
    }

    public void addFooterView(View view) {
        FixedViewInfo info = new FixedViewInfo();
        info.view = view;
        info.viewType = RecyclerView.INVALID_TYPE - mFooterViewInfos.size();
        mFooterViewInfos.add(info);
        notifyItemInserted(mHeaderViewInfos.size() + mAdapter.getItemCount() + mFooterViewInfos.size() - 1);
    }

    public void removeFooterView(View view) {
        for (int i = mFooterViewInfos.size() - 1; i >= 0; i--) {
            if (mFooterViewInfos.get(i).view == view) {
                mFooterViewInfos.remove(i);
                notifyItemRemoved(mHeaderViewInfos.size() + mAdapter.getItemCount() + mFooterViewInfos.size());
                break;
            }
        }
    }

    private static class HeaderViewHolder extends RecyclerView.ViewHolder {
        public HeaderViewHolder(View view) {
            super(view);
        }
    }

    private static class FooterViewHolder extends RecyclerView.ViewHolder {
        public FooterViewHolder(View view) {
            super(view);
        }
    }

    public class FixedViewInfo {
        /**
         * The view to add to the list
         */
        public View view;
        /**
         * The data backing the view. This is returned from {RecyclerView.Adapter#getItemViewType(int)}.
         */
        public int viewType;
    }
}
