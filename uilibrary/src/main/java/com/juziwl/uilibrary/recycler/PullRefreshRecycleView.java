package com.juziwl.uilibrary.recycler;


import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.DrawableRes;
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
import com.juziwl.uilibrary.pullrefreshlayout.PullRefreshLayout;
import com.juziwl.uilibrary.pullrefreshlayout.ShowGravity;
import com.juziwl.uilibrary.pullrefreshlayout.widget.DiDiHeader;
import com.juziwl.uilibrary.pullrefreshlayout.widget.FootView2;

import java.util.List;

/**
 * 新组合控件rv
 */
public class PullRefreshRecycleView extends LinearLayout {

    Context mContext;

    View view;

    PullRefreshLayout pullRefreshLayout;

    RecyclerView rv_list;

    DiDiHeader header;

    FootView2 footer;


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

        header = new DiDiHeader(context,pullRefreshLayout);
        footer = new FootView2(context);
        layoutManager = new LinearLayoutManager(this.getContext()); //默认是线性向下 可以手动给重新设置
        //添加头和尾
        rv_list.setLayoutManager(layoutManager);
        pullRefreshLayout.setHeaderShowGravity(ShowGravity.PLACEHOLDER_CENTER);
        pullRefreshLayout.setHeaderView(header);
        pullRefreshLayout.setFooterView(footer);
        pullRefreshLayout.setLoadMoreEnable(true);
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
    public void setAdapter(BaseQuickAdapter adapter, PullRefreshLayout.OnRefreshListener onRefreshListener) {
        pullRefreshLayout.setOnRefreshListener(onRefreshListener);
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

    ;


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
    public PullRefreshRecycleView removeHeaderAndFootVew() {
        adapter.removeAllFooterView();
        adapter.removeAllHeaderView();
        return this;
    }

    public PullRefreshRecycleView addHeardVew(View view) {
        adapter.addHeaderView(view);
        return this;
    }

    public PullRefreshRecycleView addHeadVew(View view) {
        adapter.addHeaderView(view);
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
    public PullRefreshRecycleView setLoadMoreEnable(boolean enable) {
        pullRefreshLayout.setLoadMoreEnable(enable);
        footer.isShowComplete(!enable);
        return this;
    }

    public PullRefreshRecycleView setFooterCompleteText(String text) {
        footer.setCompleteText(text);
        return this;
    }

    public PullRefreshRecycleView setRefreshEnable(boolean enable) {
        pullRefreshLayout.setRefreshEnable(enable);
        if (!enable) {
            pullRefreshLayout.removeView(header);
        } else {
            pullRefreshLayout.setHeaderView(header);
        }
        return this;
    }


    public PullRefreshRecycleView completeRefrishOrLoadMore() {
        pullRefreshLayout.refreshComplete();
        pullRefreshLayout.loadMoreComplete();
        this.adapter.setEmptyView(emptyView);
        return this;
    }


    public PullRefreshRecycleView autoRefresh() {
        pullRefreshLayout.autoRefresh();
        return this;
    }

    public PullRefreshRecycleView autoRefresh(boolean isAuto) {
        pullRefreshLayout.autoRefresh(isAuto);
        return this;
    }


    public PullRefreshRecycleView autoLoading(boolean isAuto) {
        pullRefreshLayout.autoLoading(isAuto);
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
            pullRefreshLayout.removeView(footer);
            pullRefreshLayout.setRefreshEnable(false);
            pullRefreshLayout.setLoadMoreEnable(false);
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

    public PullRefreshLayout getPullRefreshLayout() {
        return pullRefreshLayout;
    }


/**
 * 重置数据
 *
 * @param
 */
//设置适配器
//public <T> void getAdapter(List<T> list) {
//    if (list != null && list.size() > 0) {
//        nest_nodata.setVisibility(GONE);
//    } else {
//        if (list == null) {
//            list = new ArrayList<T>();
//        }
//        nest_nodata.setVisibility(VISIBLE);
//    }
//    adapter.replaceAll(list);
//
//    completeRefrishOrLoadMore();
//}
}
