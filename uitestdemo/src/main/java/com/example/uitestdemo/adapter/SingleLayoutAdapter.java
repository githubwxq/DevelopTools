package com.example.uitestdemo.adapter;

import android.content.Context;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.ViewGroup;

import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.alibaba.android.vlayout.layout.SingleLayoutHelper;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.uitestdemo.R;
import com.juziwl.uilibrary.vlayout.BaseDelegateAdapter;
import com.juziwl.uilibrary.vlayout.BaseDelegateViewHolder;
import com.wxq.commonlibrary.util.DensityUtil;

import java.util.List;

/**
 * 测试
 */
public class SingleLayoutAdapter extends BaseDelegateAdapter<String, BaseDelegateViewHolder> {

    public static final int CURRENTTYPE=3;

    public SingleLayoutAdapter(Context context, @Nullable List<String> data ) {
        super(context,R.layout.adapter_item_recycleview, data);
    }

    @Override
    public LayoutHelper getLayoutHelp() {
        SingleLayoutHelper linearLayoutHelper=   new SingleLayoutHelper();
        linearLayoutHelper.setMarginLeft(DensityUtil.dip2px(mContext, 20));
        linearLayoutHelper.setMarginRight(DensityUtil.dip2px(mContext, 20));
        linearLayoutHelper.setMarginTop(DensityUtil.dip2px(mContext, 20));
        return linearLayoutHelper;
    }

    @Override
    public void convert(BaseDelegateViewHolder holder, String item) {
//                holder.setText(R.id.tv_name,item);
        RecyclerView recyclerView=holder.getView(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext,LinearLayoutManager.HORIZONTAL,false));
        recyclerView.setAdapter(new BaseQuickAdapter<String,BaseViewHolder>(R.layout.adapter_item,getData()) {
            @Override
            protected void convert(BaseViewHolder helper, String item) {
                helper.setText(R.id.tv_name,item);
            }
        });


    }

    @Override
    protected void onBindViewHolderWithOffset(BaseDelegateViewHolder holder, int position, int offsetTotal) {
        super.onBindViewHolderWithOffset(holder, position, offsetTotal);
        VirtualLayoutManager.LayoutParams layoutParams = new VirtualLayoutManager.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 240);
        if (position == 0 || (position == getData().size() - 1 && position % 2 == 1)) {//首位和 最后一位为双数时 是小图
            layoutParams.mAspectRatio = 1f;
        }
        else {
            layoutParams.mAspectRatio = 0.57f;
        }
        holder.itemView.setLayoutParams(layoutParams);
    }


        @Override
    public int getItemViewType(int position) {
        return CURRENTTYPE;
    }
}
