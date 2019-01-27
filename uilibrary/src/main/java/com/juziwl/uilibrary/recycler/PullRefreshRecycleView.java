package com.juziwl.uilibrary.recycler;


import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.juziwl.uilibrary.R;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreator;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.List;

/**
 * 新组合控件rv
 */
public class PullRefreshRecycleView extends LinearLayout {

    Context mContext;

    View view;

    SmartRefreshLayout pullRefreshLayout;

    RecyclerView rv_list;

    RecyclerView.LayoutManager layoutManager;


    BaseQuickAdapter adapter;


    boolean isRefrishAndLoadMoreEnable = true; // 默认下拉刷新和家长更多都有

    /**
     * 空布局相关
     */
    private View loadView;//加载布局
    private View emptyView;
    private ImageView mIvEmpty;
    private TextView mTvEmpty;


    private void initView(Context context) {
        mContext = context;
        //加载布局
        view = View.inflate(context, R.layout.layout_pull_refrish_list, this);
        pullRefreshLayout = findViewById(R.id.refreshLayout);
        rv_list = findViewById(R.id.rv_list);
        loadView = LayoutInflater.from(context).inflate(R.layout.layout_load_view, null, false);
        emptyView = LayoutInflater.from(context).inflate(R.layout.layout_empty_view, null, false);
        mIvEmpty = emptyView.findViewById(R.id.iv_empty);
        mTvEmpty = emptyView.findViewById(R.id.tv_empty);

        layoutManager = new LinearLayoutManager(this.getContext()); //默认是线性向下 可以手动给重新设置
        //添加头和尾
        rv_list.setLayoutManager(layoutManager);


        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
            @NonNull
            @Override
            public RefreshHeader createRefreshHeader(@NonNull Context context, @NonNull RefreshLayout layout) {
                //全局设置主题颜色（优先级第二低，可以覆盖 DefaultRefreshInitializer 的配置，与下面的ClassicsHeader绑定）
//                layout.setPrimaryColorsId(R.color.color_0093e8, android.R.color.white);
                return new ClassicsHeader(context);
            }
        });
        SmartRefreshLayout.setDefaultRefreshFooterCreator(new DefaultRefreshFooterCreator() {
            @NonNull
            @Override
            public RefreshFooter createRefreshFooter(@NonNull Context context, @NonNull RefreshLayout layout) {
//                layout.setPrimaryColorsId(R.color.white);
                return new ClassicsFooter(context);
            }
        });

    }


    //设置空布局是否显示
    @SuppressLint("ResourceType")
    public PullRefreshRecycleView setEmptyVisibility(boolean isShow) {
        getAdapter().isUseEmpty(isShow);
        return this;
    }

    //设置空布局---图片
    public PullRefreshRecycleView setEmptyLayoutIV(@DrawableRes int drawableid) {
        mIvEmpty.setImageDrawable(getResources().getDrawable(drawableid));
        return this;
    }

    public PullRefreshRecycleView setEmptyLayout(@DrawableRes int drawableid, String text) {
        mIvEmpty.setImageDrawable(getResources().getDrawable(drawableid));
        mTvEmpty.setText(text);
        return this;
    }

    //设置空布局--文字
    public PullRefreshRecycleView setEmptyLayoutTV(String text) {
        mTvEmpty.setText(text);
        return this;
    }

    public PullRefreshRecycleView setEmptyLayout(View view) {
        emptyView = view;
        return this;
    }

    public PullRefreshRecycleView showHeaderAndFooterView() {
        adapter.setHeaderFooterEmpty(true, true);
        return this;
    }

    public PullRefreshRecycleView showHeaderView() {
        adapter.setHeaderFooterEmpty(true, false);
        return this;
    }
    public void smoothToLastPosition() {
        layoutManager.scrollToPosition(adapter.getData().size()-1);
    }

    public OnItemClickListener mOnItemClickListener;


    public interface OnItemClickListener {
        <T> void onItemClick(T item, int position, View view);
    }


    public OnItemChildClickListener mOnItemChildClickListener;

    public interface OnItemChildClickListener {
        void onItemChildClick(BaseQuickAdapter adapter, int position, View view);
    }

    /**
     * 设置点击事件-- {适用于  item被点击的时候调用}
     * 不适用场景：重写后findviewbyid获取子控件 设置点击事件 会产生错乱的问题
     *
     * @param onItemClickListener
     */
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;

        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(adapter.getItem(position), position, view);
                }
            }
        });
    }


    /**
     * 设置点击事件---{适用于 item中的子view 被点击的时候调用}
     * 先 在adapter中 helper. addOnClickListener（view）
     * view.getid  获取资源id 分别做业务处理
     *
     * @param onItemChildClickListener
     */
    public void setOnItemChildClickListener(OnItemChildClickListener onItemChildClickListener) {
        mOnItemChildClickListener = onItemChildClickListener;

        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (mOnItemChildClickListener != null) {
                    mOnItemChildClickListener.onItemChildClick(adapter, position, view);
                }
            }
        });
    }


    /**
     * 设置适配器（没有刷新和加载）
     *
     * @param adapter 自己处理数据源问题
     */
    public void setAdapter(BaseQuickAdapter adapter) {
        setRefreshEnable(false);
        setLoadMoreEnable(false);
        this.adapter = adapter;
        this.adapter.setEmptyView(loadView);
        rv_list.setAdapter(this.adapter);

    }


    /**
     * 设置适配器（自己设置是否 刷新和加载）
     *
     * @param adapter           自己处理数据源问题
     * @param onRefreshListener
     */
    public void setAdapter(BaseQuickAdapter adapter, OnRefreshLoadMoreListener onRefreshListener) {
        pullRefreshLayout.setOnRefreshLoadMoreListener(onRefreshListener);
        this.adapter = adapter;
        this.adapter.setEmptyView(loadView);
        rv_list.setAdapter(this.adapter);

    }


    /**
     * 设置数据集（替换）  --- 适用于 刷新后 或者平时 调用
     */
    public <T> void setRecycleViewData(List<T> list) {
        if (adapter == null) {
            return;
        }
        adapter.replaceData(list);
        completeRefrishOrLoadMore();
    }


    /**
     * 增加数据源  --- 适用于 上拉加载 调用
     */
    public <T> void addRecycleViewData(List<T> list) {
        if (adapter == null) {
            return;
        }
        adapter.addData(list);
        completeRefrishOrLoadMore();
    }


    /**
     * 全部刷新
     */
    public void notifyDataSetChanged() {
        if (adapter == null) {
            return;
        }
        adapter.notifyDataSetChanged();
        completeRefrishOrLoadMore();
    }

    /**
     * 刷新某一个
     */
    public void notifyItemChanged(int position) {
        if (adapter == null) {
            return;
        }
        adapter.notifyItemChanged(position);
        completeRefrishOrLoadMore();
    }

    ;

    /**
     * 删除某一个item 位置
     *
     * @param position
     */
    public void removeItem(int position) {
        if (adapter == null) {
            return;
        }
        adapter.remove(position);
    }


    /**
     * 获取适配器的集合
     */
    public <T> List<T> getData() {
        if (adapter == null) {
            return null;
        }
        return adapter.getData();
    }


    public BaseQuickAdapter getAdapter() {
        return adapter;
    }

    public PullRefreshRecycleView addItemDecoration(RecyclerView.ItemDecoration decor) {
        rv_list.addItemDecoration(decor);
        return this;
    }

    /**
     * 删除头
     */
    public PullRefreshRecycleView removeHeadView(View view) {
        adapter.removeHeaderView(view);
        return this;
    }


    /**
     * 删除全部的头和尾
     */
    public PullRefreshRecycleView removeHeaderAndFootView() {
        adapter.removeAllFooterView();
        adapter.removeAllHeaderView();
        return this;
    }

    public PullRefreshRecycleView addHeaderView(View view, boolean isShowHeader) {
        adapter.addHeaderView(view);
        adapter.setHeaderAndEmpty(isShowHeader);
        return this;
    }


    public void addFootView(View view) {
        adapter.addFooterView(view);
    }


    public PullRefreshRecycleView(Context context) {
        super(context);
        initView(context);
    }

    public PullRefreshRecycleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public PullRefreshRecycleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    //设置能否加载更多
    @SuppressLint("RestrictedApi")
    public PullRefreshRecycleView setLoadMoreEnable(boolean enable) {
        pullRefreshLayout.setEnableLoadMore(enable);
        pullRefreshLayout.getRefreshFooter().setNoMoreData(!enable);
        return this;
    }

//    public PullRefreshRecycleView setFooterCompleteText(String text) {
//        footer.setCompleteText(text);
//        return this;
//    }

    public PullRefreshRecycleView setRefreshEnable(boolean enable) {
        pullRefreshLayout.setEnableRefresh(enable);
        return this;
    }


    public PullRefreshRecycleView completeRefrishOrLoadMore() {
        pullRefreshLayout.finishRefresh();//结束刷新
        pullRefreshLayout.finishLoadMore();//结束加载
        adapter.setEmptyView(emptyView);
        return this;
    }


    public PullRefreshRecycleView autoRefresh() {
        pullRefreshLayout.autoRefresh();
        return this;
    }

    public PullRefreshRecycleView autoRefresh(boolean isAuto) {
        if (isAuto) {
            pullRefreshLayout.autoRefresh();
        }
        return this;
    }


    public PullRefreshRecycleView autoLoading(boolean isAuto) {
        if (isAuto) {
            pullRefreshLayout.autoLoadMore();
        }
        return this;
    }

    /**
     * 设置不需要下拉刷新和加载更多
     *
     * @param enable
     */
    public PullRefreshRecycleView setIsRefrishAndLoadMoreEnable(boolean enable) {
        isRefrishAndLoadMoreEnable = enable;
        if (!isRefrishAndLoadMoreEnable) {
//            pullRefreshLayout.removeView(footer);
            pullRefreshLayout.setEnableRefresh(false);
            pullRefreshLayout.setEnableLoadMore(false);
        }
        return this;
    }


    public PullRefreshRecycleView setLayoutManager(RecyclerView.LayoutManager layoutManager) {
        rv_list.setLayoutManager(layoutManager);
        return this;
    }


    public RecyclerView getRecycleView() {
        return rv_list;
    }

    public SmartRefreshLayout getPullRefreshLayout() {
        return pullRefreshLayout;
    }

}
