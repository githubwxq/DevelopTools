package com.juziwl.uilibrary.tools.recyclerview;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.juziwl.uilibrary.tools.ViewHolder;
import com.juziwl.uilibrary.tools.recyclerview.click.OnItemClickListener;
import com.juziwl.uilibrary.tools.recyclerview.click.OnItemLongClickListener;

import java.util.List;

/**
 * Created by zhy on 16/4/9.
 */
public abstract class CommonAdapter<T> extends RecyclerView.Adapter<ViewHolder> {
    protected Context mContext;
    protected int mLayoutId;
    protected List<T> mDatas;
    OnItemClickListener onItemClickListener;
    OnItemLongClickListener onItemLongClickListener;
    int headSize = 0;

    public CommonAdapter(Context context, int layoutId, List<T> datas) {
        mContext = context;
        mLayoutId = layoutId;
        mDatas = datas;
        setHasStableIds(true);
    }

    public void setmDatas(List<T> mDatas) {
        this.mDatas = mDatas;
    }

    public int getmLayoutId() {
        return mLayoutId;
    }

    @Override
    public long getItemId(int position) {
        return mDatas.get(position).hashCode();
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        ViewHolder viewHolder = ViewHolder.get(mContext, null, parent, mLayoutId, -1);
        setListener(parent, viewHolder, viewType);
        return viewHolder;
    }

    protected int getPosition(RecyclerView.ViewHolder viewHolder) {
        return viewHolder.getAdapterPosition();
    }

    protected boolean isEnabled(int viewType) {
        return true;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.updatePosition(position);
        convert(holder, mDatas.get(position));
    }

    public abstract void convert(ViewHolder holder, T t);

    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }


    protected void setListener(final ViewGroup parent, final ViewHolder viewHolder, int viewType) {
        if (!isEnabled(viewType)) return;
        viewHolder.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    int position = getPosition(viewHolder);
                    if (position != -1){
                        onItemClickListener.onItemClick(parent, v, mDatas.get(position - headSize), position - headSize);
                    }

                }
            }
        });


        viewHolder.getConvertView().setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (onItemLongClickListener != null) {
                    int position = getPosition(viewHolder);
                    if (position != -1) {
                        return onItemLongClickListener.onItemLongClick(parent, v, mDatas.get(position - headSize), position - headSize);
                    }
                    }
                return false;
            }
        });
    }

    public void setHeadSize(int headSize) {
        this.headSize = headSize;
    }

    public int getHeadSize() {
        return headSize;
    }

    public T getItemData(int position) {
        if (mDatas != null && mDatas.size() <= position) {
            return null;
        }
        return mDatas.get(position);
    }

    public List<T> getDatas() {
        return mDatas;
    }
}


////多布局文件
//private final class MultipleLayoutAdapter extends CommonAdapter<News>{
//
//    public MultipleLayoutAdapter(Context context, int layoutResId, List<News> data) {
//        super(context, layoutResId, data);
//    }
//    //多种布局重写此方法即可
//    @Override public int getLayoutResId(News item, int position) {
//        int layoutResId = -1;
//        switch (item.getNewsType()){
//            case News.TYPE_NONE_PICTURE: //布局样式一
//                layoutResId = R.layout.item_none_picture;
//                break;
//            case News.TYPE_SINGLE_PICTURE: //布局样式二
//                layoutResId = R.layout.item_single_picture;
//                break;
//            case News.TYPE_MULTIPLE_PICTURE: //布局样式三
//                layoutResId = R.layout.item_multiple_picture;
//                break;
//
//            更多的布局样式 ...
//        }
//        return layoutResId;
//    }
//
//    @Override public void onUpdate(BaseAdapterHelper helper, News item, int position) {
//        switch (item.getNewsType()){
//            case News.TYPE_NONE_PICTURE: //布局样式一
//                helper.setText(R.id.xxx, item.getTitle())
//                        .setImageUrl(R.id.xxx,item.getCoverUrl());
//                break;
//            case News.TYPE_SINGLE_PICTURE: //布局样式二
//                helper.setText(R.id.xxx, item.getTitle())
//                        .setImageUrl(R.id.xxx,item.getCoverUrl());
//                break;
//            case News.TYPE_MULTIPLE_PICTURE: //布局样式三
//                helper.setText(R.id.xxx, item.getTitle())
//                        .setImageUrl(R.id.xxx,item.getCoverUrl());
//                break;
//
//            更多的布局样式 ...
//        }
//    }
//}