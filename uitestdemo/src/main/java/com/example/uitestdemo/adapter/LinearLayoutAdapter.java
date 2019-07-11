package com.example.uitestdemo.adapter;

import android.content.Context;
import android.support.annotation.Nullable;

import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.example.uitestdemo.R;
import com.juziwl.uilibrary.utils.ConvertUtils;
import com.juziwl.uilibrary.vlayout.BaseDelegateAdapter;
import com.juziwl.uilibrary.vlayout.BaseDelegateViewHolder;
import com.wxq.commonlibrary.util.DensityUtil;

import java.util.List;

/**
 * 测试
 */
public class LinearLayoutAdapter extends BaseDelegateAdapter<String, BaseDelegateViewHolder> {

    public static final int CURRENTTYPE=2;

    public LinearLayoutAdapter(Context context, @Nullable List<String> data ) {
        super(context,R.layout.adapter_item, data);
    }

    @Override
    public LayoutHelper getLayoutHelp() {
        LinearLayoutHelper linearLayoutHelper=   new LinearLayoutHelper(ConvertUtils.dp2px(15,mContext));
        linearLayoutHelper.setMarginLeft(DensityUtil.dip2px(mContext, 20));
        linearLayoutHelper.setMarginRight(DensityUtil.dip2px(mContext, 20));
        linearLayoutHelper.setMarginTop(DensityUtil.dip2px(mContext, 20));

        //设置间隔高度
        linearLayoutHelper.setDividerHeight(5);
        //设置布局底部与下个布局的间隔
        linearLayoutHelper.setMarginBottom(20);
        return linearLayoutHelper;
    }

    @Override
    public void convert(BaseDelegateViewHolder holder, String item) {
                holder.setText(R.id.tv_name,item);
    }

    @Override
    protected void onBindViewHolderWithOffset(BaseDelegateViewHolder holder, int position, int offsetTotal) {
        super.onBindViewHolderWithOffset(holder, position, offsetTotal);
//        VirtualLayoutManager.LayoutParams layoutParams = new VirtualLayoutManager.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 240);
//        if (position == 0 || (position == getData().size() - 1 && position % 2 == 1)) {//首位和 最后一位为双数时 是小图
//            layoutParams.mAspectRatio = 1f;
//        }
//        else {
//            layoutParams.mAspectRatio = 0.57f;
//        }
//        holder.itemView.setLayoutParams(layoutParams);
    }


        @Override
    public int getItemViewType(int position) {
        return CURRENTTYPE;
    }
}
