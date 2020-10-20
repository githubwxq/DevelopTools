package com.example.uitestdemo.activity;

import android.util.Log;
import android.view.View;

import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.callback.ItemDragAndSwipeCallback;
import com.chad.library.adapter.base.listener.OnItemDragListener;
import com.example.uitestdemo.R;
import com.example.uitestdemo.adapter.ItemDragAdapter;
import com.example.uitestdemo.adapter.PostData;
import com.example.uitestdemo.adapter.PostPublishViewHolder;
import com.wxq.commonlibrary.base.BaseActivity;
import com.wxq.commonlibrary.base.BasePresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 测试发帖页面
 */
public class TestPublishWeightActivity extends BaseActivity {
    private static final String TAG = TestPublishWeightActivity.class.getSimpleName();
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    List<PostData> list = new ArrayList<>();

    @Override
    protected void initViews() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

//        mRecyclerView.
        list.add(new PostData(true, "1"));
        list.add(new PostData(true, "2"));
        list.add(new PostData(true, "3"));
        list.add(new PostData(true, "4"));
        list.add(new PostData(true, "5"));
        list.add(new PostData(true, "6"));

        ItemDragAdapter itemDragAdapter = new ItemDragAdapter(list);
        ItemDragAndSwipeCallback mItemDragAndSwipeCallback = new ItemDragAndSwipeCallback(itemDragAdapter);
        ItemTouchHelper mItemTouchHelper = new ItemTouchHelper(mItemDragAndSwipeCallback);
        mItemTouchHelper.attachToRecyclerView(recyclerView);
        mItemDragAndSwipeCallback.setSwipeMoveFlags(ItemTouchHelper.START | ItemTouchHelper.END);
        itemDragAdapter.enableDragItem(mItemTouchHelper);
        itemDragAdapter.setOnItemDragListener(new OnItemDragListener() {
            @Override
            public void onItemDragStart(RecyclerView.ViewHolder viewHolder, int i) {
                Log.d(TAG, "drag start");
                for (int i1 = 0; i1 < itemDragAdapter.getItemCount(); i1++) {
                    View childAt = recyclerView.getChildAt(i1);
                    PostPublishViewHolder childViewHolder = (PostPublishViewHolder) recyclerView.getChildViewHolder(childAt);
                    childViewHolder.showImage(false);
                }
            }

            @Override
            public void onItemDragMoving(RecyclerView.ViewHolder source, int from, RecyclerView.ViewHolder target, int i1) {
                Log.d(TAG, "move from: " + source.getAdapterPosition() + " to: " + target.getAdapterPosition());
            }

            @Override
            public void onItemDragEnd(RecyclerView.ViewHolder viewHolder, int i) {
                Log.d(TAG, "drag end");
                for (int i1 = 0; i1 < itemDragAdapter.getItemCount(); i1++) {
                    View childAt = recyclerView.getChildAt(i1);
                    PostPublishViewHolder childViewHolder = (PostPublishViewHolder) recyclerView.getChildViewHolder(childAt);
                    childViewHolder.showImage(true);
                }
            }
        });
        recyclerView.setAdapter(itemDragAdapter);

    }


    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_test_publish_weight;
    }

    @Override
    protected BasePresenter initPresent() {
        return null;
    }

}
