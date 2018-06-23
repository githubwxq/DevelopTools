//package com.juziwl.uilibrary.recycler;
//
//import android.annotation.SuppressLint;
//import android.content.Context;
//import android.support.annotation.DrawableRes;
//import android.support.annotation.LayoutRes;
//import android.support.annotation.Nullable;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.util.AttributeSet;
//import android.view.View;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//
//import com.juziwl.uilibrary.R;
//import com.juziwl.uilibrary.easycommonadapter.BaseAdapterHelper;
//import com.juziwl.uilibrary.easycommonadapter.CommonRecyclerNewAdapter;
//import com.yan.pullrefreshlayout.FootView2;
//import com.yan.pullrefreshlayout.Header;
//import com.yan.pullrefreshlayout.PullRefreshLayout;
//import com.yan.pullrefreshlayout.RefreshShowHelper;
//
//import java.util.ArrayList;
//import java.util.List;
//
////import com.yan.pullrefreshlayout.FootView2;
//
///**
// * Created by 王晓清 on 2018/1/19 0019.  请求结束后
// */
//
//public class PullRefreshRecycleView extends LinearLayout {
//
//    int page = 1;
//
//    int rows = 10;
//
//    int adapterLayoutId; //适配器的布局id
//
//    Context mContext;
//
//    View view;
//
//    PullRefreshLayout pullRefreshLayout;
//
//
//    BaseWrapRecyclerView rv_list;
//
//    LinearLayout nest_nodata;
//
//    ImageView nodate_imageView;
//
//    TextView nodata_message;
//
//    Header header;
//
//    FootView2 footer;
//
//
//    RecyclerView.LayoutManager layoutManager;
//
//    PullRefreshListener pullRefrshListener;
//
//    CommonRecyclerNewAdapter adapter;
//
//
//    boolean isRefrishAndLoadMoreEnable = true; // 默认下拉刷新和家长更多都有
//
//
//    private void initView(Context context) {
//        mContext = context;
//        //加载布局
//        view = View.inflate(context, R.layout.layout_pull_refrish_list, this);
//        pullRefreshLayout = (PullRefreshLayout) findViewById(R.id.refreshLayout);
//        rv_list = (BaseWrapRecyclerView) findViewById(R.id.rv_list);
//        nest_nodata = (LinearLayout) findViewById(R.id.nest_nodata);
//        nodate_imageView = (ImageView) findViewById(R.id.nodate_imageView);
//        nodata_message = (TextView) findViewById(R.id.nodata_message);
//        header = new Header(context);
//        footer = new FootView2(context);
//        layoutManager = new LinearLayoutManager(this.getContext()); //默认是线性向下 可以手动给重新设置
//        //添加头和尾
//        rv_list.setLayoutManager(layoutManager);
//        pullRefreshLayout.setHeaderShowGravity(RefreshShowHelper.STATE_PLACEHOLDER);
//        pullRefreshLayout.setHeaderView(header);
//        pullRefreshLayout.setFooterView(footer);
//        pullRefreshLayout.setLoadMoreEnable(true);
//
//        pullRefreshLayout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//
//                if (pullRefrshListener != null) {
//                    pullRefrshListener.OnRefreshListener(page, rows);
//                }
//
//            }
//
//            @Override
//            public void onLoading() {
//                if (pullRefrshListener != null) {
//                    pullRefrshListener.OnLoadingListener(page, rows);
//                }
//            }
//        });
//
//
//    }
//
//    //设置空布局显示文字
//    public PullRefreshRecycleView setEmptyLayoutTV(String text) {
//        nodata_message.setText(text);
//        return this;
//    }
//
//    //设置空布局显示图片
//    public PullRefreshRecycleView setEmptyLayoutIV(@DrawableRes int drawableid) {
//        nodate_imageView.setImageDrawable(getResources().getDrawable(drawableid));
//        return this;
//    }
//
//    //设置空布局layout
//    @SuppressLint("ResourceType")
//    public PullRefreshRecycleView setEmptyLayout(@LayoutRes int layoutResId) {
//        nest_nodata.removeAllViews();
//        nest_nodata.setBackgroundResource(layoutResId);
//        return this;
//    }
//
//    //设置适配器
//    public <T> void setAdaper(int adapterLayoutId, List<T> list, PullRefreshListener<T> listener) {
//        pullRefrshListener = listener;
//        if (list != null && list.size() > 0) {
//            nest_nodata.setVisibility(GONE);
//        } else {
//            nest_nodata.setVisibility(VISIBLE);
//        }
//        if (adapter == null) {
//            adapter = new CommonRecyclerNewAdapter<T>(this.getContext(), adapterLayoutId, list) {
//                @Override
//                public void onUpdate(BaseAdapterHelper helper, T item, int position) {
//                    // 子类实现方法
//                    pullRefrshListener.convert(helper, item, position);
//                }
//
//                @Override
//                public int getItemViewType(int position) {
//                    //加载不同布局的
//                    return super.getItemViewType(position);
//                }
//            };
//            rv_list.setAdapter(adapter);
//        } else {
//            //更新  可以换成其他的
//            adapter.replaceAll(list);
//        }
//
////       List<T> currentData= adapter.getData();
//
//    }
//
////    、、重载这个方法实现其他完善
//
//    //设置数据集（替换）
//    public <T> void setRecycleViewData(List<T> list) {
//        if (adapter == null) {
//            return;
//        }
//        if (list != null && list.size() > 0) {
//            nest_nodata.setVisibility(GONE);
//        } else {
//            if (list == null) {
//                list = new ArrayList<T>();
//            }
//            nest_nodata.setVisibility(VISIBLE);
//        }
//        adapter.notifyDataSetChanged();
//
//        completeRefrishOrLoadMore();
//    }
//
//
//    /**
//     * 删除某一个item内容
//     *
//     * @param item
//     * @param <T>
//     */
//    public <T> void removeItem(T item) {
//        if (adapter == null) {
//            return;
//        }
//        adapter.remove(item);
//    }
//
//    /**
//     * 删除某一个item 位置
//     *
//     * @param position
//     */
//    public void removeItem(int position) {
//        if (adapter == null) {
//            return;
//        }
//        adapter.remove(position);
//    }
//
//
//    /**
//     * 获取适配器的集合
//     */
//    public <T> List<T> getData() {
//        if (adapter == null) {
//            return null;
//        }
//        return adapter.getData();
//    }
//
//
//    public CommonRecyclerNewAdapter getAdapter() {
//        return adapter;
//    }
//
//    public PullRefreshRecycleView addItemDecoration(RecyclerView.ItemDecoration decor) {
//        rv_list.addItemDecoration(decor);
//        return this;
//    }
//
//
//    public void addHeardVew(View view) {
//        rv_list.addHeaderView(view);
//    }
//
//
//    public void addFootView(View view) {
//        rv_list.addFootView(view);
//    }
//
//
//    public interface PullRefreshListener<T> {
//
//        void convert(BaseAdapterHelper helper, T item, int position);
//
//        void OnRefreshListener(int page, int rows);
//
//        void OnLoadingListener(int page, int rows);
//
//    }
//
//    public PullRefreshRecycleView(Context context) {
//        super(context);
//        initView(context);
//    }
//
//    public PullRefreshRecycleView(Context context, @Nullable AttributeSet attrs) {
//        super(context, attrs);
//        initView(context);
//    }
//
//    public PullRefreshRecycleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
//        super(context, attrs, defStyleAttr);
//        initView(context);
//    }
//
//    public PullRefreshListener getPullRefrshListener() {
//        return pullRefrshListener;
//    }
//
//    public void setPullRefrshListener(PullRefreshListener pullRefrshListener) {
//        this.pullRefrshListener = pullRefrshListener;
//    }
//
//
//    //设置能否加载更多
//    public PullRefreshRecycleView setLoadMoreEnable(boolean enable) {
//        pullRefreshLayout.setLoadMoreEnable(enable);
//        footer.isShowComplete(!enable);
//        return this;
//    }
//
//
//    public PullRefreshRecycleView setRefrishEnable(boolean enable) {
//        pullRefreshLayout.setRefreshEnable(enable);
//        if (!enable) {
//            pullRefreshLayout.removeView(header);
//        } else {
//            pullRefreshLayout.setHeaderView(header);
//        }
//        return this;
//    }
//
//
//    public PullRefreshRecycleView completeRefrishOrLoadMore() {
//
//        pullRefreshLayout.refreshComplete();
//
//        pullRefreshLayout.loadMoreComplete();
//        return this;
//    }
//
//    public int getAdapterLayoutId() {
//        return adapterLayoutId;
//    }
//
//    public PullRefreshRecycleView setAdapterLayoutId(int adapterLayoutId) {
//        this.adapterLayoutId = adapterLayoutId;
//        return this;
//    }
//
//
//    /**
//     * 设置不需要下拉刷新和加载更多
//     *
//     * @param enable
//     */
//    public PullRefreshRecycleView setIsRefrishAndLoadMoreEnable(boolean enable) {
//        isRefrishAndLoadMoreEnable = enable;
//        if (!isRefrishAndLoadMoreEnable) {
////            pullRefreshLayout.removeView(footer);
//            pullRefreshLayout.setRefreshEnable(false);
//            pullRefreshLayout.setLoadMoreEnable(false);
//        }
//        return this;
//    }
//
//
//    public PullRefreshRecycleView setLayoutManager(RecyclerView.LayoutManager layoutManager) {
//        rv_list.setLayoutManager(layoutManager);
//        return this;
//    }
//
//
//    public BaseWrapRecyclerView getRecycleView() {
//        return rv_list;
//    }
//
//
///**
// * 重置数据
// *
// * @param
// */
////设置适配器
////public <T> void getAdapter(List<T> list) {
////    if (list != null && list.size() > 0) {
////        nest_nodata.setVisibility(GONE);
////    } else {
////        if (list == null) {
////            list = new ArrayList<T>();
////        }
////        nest_nodata.setVisibility(VISIBLE);
////    }
////    adapter.replaceAll(list);
////
////    completeRefrishOrLoadMore();
////}
//}
