package com.juziwl.uilibrary.easycommonadapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import static com.juziwl.uilibrary.easycommonadapter.BaseAdapterHelper.get;


/**
 * 项目名称: CommonAdapter
 * 包 名 称: com.classic.adapter
 *
 * @author null
 * @modify Neil
 * 类 描 述: 通用Adapter,适用于RecyclerView,简化大量重复代码
 * 创 建 人: 续写经典
 * 创建时间: 2016/1/27 17:50.
 */
@SuppressWarnings({"WeakerAccess", "unused"})
public abstract class CommonRecyclerNewAdapter<T>
        extends RecyclerView.Adapter implements IAdapter<T>, IData<T> {

    public Context mContext;
    public List<T> mData;

    private final int mLayoutResId;
    private OnItemClickListener mItemClickListener;
    private OnItemLongClickListener mItemLongClickListener;

    public CommonRecyclerNewAdapter(Context context, int layoutResId) {
        this(context, layoutResId, null);
    }

    public CommonRecyclerNewAdapter(Context context, int layoutResId, List<T> data) {
        this.mData = data;
        this.mContext = context;
        this.mLayoutResId = layoutResId;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final BaseAdapterHelper helper = get(mContext, null, parent, viewType, -1);
        return new RecyclerViewHolder(helper.getView(), helper);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        BaseAdapterHelper helper = ((RecyclerViewHolder) holder).mAdapterHelper;
        helper.setAssociatedObject(getItem(position));
        onUpdate(helper, getItem(position), position);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position, List payloads) {
        if (null != payloads && payloads.size() > 0) {
            BaseAdapterHelper helper = ((RecyclerViewHolder) holder).mAdapterHelper;
            helper.setAssociatedObject(getItem(position));
            onItemContentChanged(helper, payloads);
        } else {
            super.onBindViewHolder(holder, position, payloads);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return getLayoutResId(getItem(position), position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public int getLayoutResId(T item, int position) {
        return this.mLayoutResId;
    }

    @Override
    public List<T> getData() {
        return mData;
    }

    @Override
    public void add(T item) {
        mData.add(item);
        notifyItemInserted(mData.size());
    }

    @Override
    public void addAll(List<T> list) {
        mData.addAll(list);
        //notifyItemRangeInserted会显示一个插入动画
        //notifyItemRangeInserted(mData.size() - list.size(), list.size());
        notifyDataSetChanged();
    }

    @Override
    public void addAll(int index, List<T> list) {
        mData.addAll(index, list);
        //notifyItemRangeInserted会显示一个插入动画
        //notifyItemRangeInserted(mData.size() - list.size(), list.size());
        notifyDataSetChanged();
    }

    @Override
    public void set(T oldItem, T newItem) {
        set(mData.indexOf(oldItem), newItem);
    }

    @Override
    public void set(int index, T item) {
        mData.set(index, item);
        notifyItemChanged(index);
    }

    @Override
    public void remove(T item) {
        final int position = mData.indexOf(item);
        mData.remove(item);
        notifyItemRemoved(position);
//        notifyDataSetChanged();
    }

    @Override
    public void remove(int index) {
        mData.remove(index);
        notifyItemRemoved(index);
//        notifyDataSetChanged();
    }

    @Override
    public void replaceAll(List<T> item) {
        replaceAll(item, true);
    }

    @Override
    public boolean contains(T item) {
        return mData.contains(item);
    }

    @Override
    public void clear() {
        mData.clear();
        notifyDataSetChanged();
    }

    public void replaceAll(List<T> elem, boolean notifyDataSetChanged) {
        mData.clear();  //
        mData.addAll(elem);
        if (notifyDataSetChanged) {
            notifyDataSetChanged();
        }
    }

    /**
     * 更小粒度的更新，比如某个对象的某个属性值改变了，只改变此属性
     * <pre>
     * 此回调执行的前提是：
     * 使用{@link android.support.v7.util.DiffUtil.Callback}进行数据更新，
     * 并且重写了{@link android.support.v7.util.DiffUtil.Callback#getChangePayload}方法
     * 使用方法见{https://github.com/qyxxjd/CommonAdapter/blob/master/app/src/main/java/com/classic/adapter/simple/activity/RecyclerViewSimpleActivity.java}
     * </pre>
     *
     * @param helper
     * @param payloads
     */
    public void onItemContentChanged(@NonNull BaseAdapterHelper helper,
                                     @NonNull List<Object> payloads) {

    }

    public T getItem(int position) {
        return position >= mData.size() ? null : mData.get(position);
    }

    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        this.mItemClickListener = itemClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener itemLongClickListener) {
        this.mItemLongClickListener = itemLongClickListener;
    }

    public interface OnItemClickListener {
        /**
         * 条目点击事件
         *
         * @param viewHolder
         * @param view
         * @param position
         */
        void onItemClick(RecyclerView.ViewHolder viewHolder, View view, int position);
    }

    public interface OnItemLongClickListener {
        /**
         * 条目长点击事件
         *
         * @param viewHolder
         * @param view
         * @param position
         */
        void onItemLongClick(RecyclerView.ViewHolder viewHolder, View view, int position);
    }

    private final class RecyclerViewHolder extends RecyclerView.ViewHolder {
        BaseAdapterHelper mAdapterHelper;

        public RecyclerViewHolder(View itemView, BaseAdapterHelper adapterHelper) {
            super(itemView);
            this.mAdapterHelper = adapterHelper;



            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (null != mItemClickListener) {
                        mItemClickListener.onItemClick(RecyclerViewHolder.this, itemView, getAdapterPosition());
                    }
                }
            });
            itemView.setOnLongClickListener(v -> {
                if (null != mItemLongClickListener) {
                    mItemLongClickListener.onItemLongClick(RecyclerViewHolder.this, v,
                            getAdapterPosition());
                    return true;
                }
                return false;
            });
        }
    }

    public static abstract class AbsScrollControl extends RecyclerView.OnScrollListener
            implements IScrollHideListener {
        //滑动隐藏的偏移量
        private static final int DEFAULT_SCROLL_HIDE_OFFSET = 20;

        private int mCurrentScrollOffset;
        private boolean isControlVisible;

        /**
         * 自定义LayoutManager需要实现此方法
         */
        protected int getFirstVisibleItemPositions() {
            return 0;
        }

        /**
         * 获取滑动隐藏的偏移量
         */
        protected int getScrollHideOffset() {
            return DEFAULT_SCROLL_HIDE_OFFSET;
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);

            final int firstVisibleItemPosition = findFirstVisibleItemPosition(
                    recyclerView.getLayoutManager());

            if (firstVisibleItemPosition == 0 && !isControlVisible) {
                onShow();
                isControlVisible = true;
            } else if (firstVisibleItemPosition != 0 &&
                    mCurrentScrollOffset > getScrollHideOffset() &&
                    isControlVisible) {
                //向上滚动,并且视图为显示状态
                onHide();
                isControlVisible = false;
                mCurrentScrollOffset = 0;
            } else if (firstVisibleItemPosition != 0 &&
                    mCurrentScrollOffset < -getScrollHideOffset() &&
                    !isControlVisible) {
                //向下滚动,并且视图为隐藏状态
                onShow();
                isControlVisible = true;
                mCurrentScrollOffset = 0;
            }

            //dy>0:向上滚动
            //dy<0:向下滚动
            if ((isControlVisible && dy > 0) || (!isControlVisible && dy < 0)) {
                mCurrentScrollOffset += dy;
            }
        }

        private int findFirstVisibleItemPosition(RecyclerView.LayoutManager layoutManager) {
            if (layoutManager instanceof GridLayoutManager) {
                return ((GridLayoutManager) layoutManager).findFirstVisibleItemPosition();
            } else if (layoutManager instanceof LinearLayoutManager) {
                return ((LinearLayoutManager) layoutManager).findFirstVisibleItemPosition();
            } else if (layoutManager instanceof StaggeredGridLayoutManager) {
                return ((StaggeredGridLayoutManager) layoutManager).findFirstVisibleItemPositions(
                        null)[0];
            } else {
                return getFirstVisibleItemPositions();
            }
        }
    }
}
