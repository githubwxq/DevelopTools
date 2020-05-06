package com.example.uitestdemo.adapter;

import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.example.uitestdemo.bean.PostItem;
import com.juziwl.uilibrary.MyItemTouchCallback;

import java.util.Collections;
import java.util.List;

public class PostPublishAdapter extends BaseMultiItemQuickAdapter<PostItem, BaseViewHolder> implements PostItemTouchCallback.ItemTouchAdapter {
        public static final int TYPE_LEVEL_0 = 0;
        public static final int TYPE_LEVEL_1 = 1;
        public static final int TYPE_LEVEL_2 = 2;
        public static final int TYPE_LEVEL_3 = 3;

        public PostPublishAdapter(List<PostItem> list) {
            super(list);
            for (MultiItemEntity multiItemEntity : list) {
                addItemType(multiItemEntity.getItemType(), multiItemEntity.getItemType());
            }
        }

        @Override
        protected void convert(BaseViewHolder helper, PostItem item) {
            item.bindViewWidthData(helper);
        }


    @Override
    public void onMove(int fromPosition, int toPosition, RecyclerView.ViewHolder viewHolder) {
        Collections.swap(mData, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
    }

    @Override
    public void onSwiped(int position) {
        mData.remove(position);
    }

    @Override
    public void onFinishDrag(int position, RecyclerView.ViewHolder viewHolder) {

    }

    @Override
    public void onStartDrag(int position, RecyclerView.ViewHolder viewHolder) {

    }
}
