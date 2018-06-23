package com.juziwl.uilibrary.listview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;


import com.juziwl.uilibrary.R;

import java.text.SimpleDateFormat;
import java.util.Date;



/**
 * @author 王晓清
 * @modify Neil
 * 支持下拉刷新和上拉刷新 可自定义上啦和下拉过程的操作,推荐使用AsyncTask 需自定义Foot的View,然后只需在addFoot方法中添加即可
 */
public class CustomListView extends ListView implements OnScrollListener {
    private static final int DONE = 0;
    private static final int PULL_TO_REFRESH = 1;
    private static final int RELEASE_TO_REFRESH = 2;
    private static final int REFRESHING = 3;
    /**
     * 用来设置实际间距和上边距之间的比例
     */
    private static final float RATIO = 3;
    /**
     * 当前下拉刷新的状态
     */
    public int state;
    public int proState;
    /**
     * 在listview中第一个可以看见的item
     */
    private int firstVisibleIndex;
    private View headView;
    private ProgressBar progressBar;
    private ImageView headTitle;
    private ImageView headLastUpdate;
    public int headContentHeight;
    /**
     * 刷新监听器
     */
    private OnRefreshListner refreshListner;
    /**
     * 用来记录第一次按下坐标点,在整个滑动的过程中 只记录一次
     */
    private boolean isRecored = false;
    private float startY;
    /**
     * 是从 松开刷新状态 来到的 下拉刷新状态
     */
    private boolean isBack = false;
    /**
     * 若true，当count>=num才能上拉加载
     */
    private boolean isCanLoad = true;
    private int num = 10;
    /**
     * 最后一个可见的item的位置
     */
    private int lastPos;
    /**
     * item总数,注意不是当前可见的item总数
     */
    private int count;
    /**
     * 是否有了Foot
     */
    private boolean hasFoot = false;

    public void setNum(int num) {
        this.num = num;
    }

    public CustomListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CustomListView(Context context) {
        this(context, null);
    }

    private void init(Context context) {
        // listview 设置滑动时缓冲背景色
        setCacheColorHint(0x00000000);
        headView = View.inflate(context, R.layout.common_header, null);

        progressBar = (ProgressBar) headView.findViewById(R.id.progressbar);
        headTitle = (ImageView) headView.findViewById(R.id.head_title);
        headLastUpdate = (ImageView) headView
                .findViewById(R.id.head_last_update);
        MeasureView(headView);
        headContentHeight = headView.getMeasuredHeight();
        headView.setPadding(0, -1 * headContentHeight, 0, 0);
        // 为listView加入顶部View
        addHeaderView(headView);
        setOnScrollListener(this);
        // 设置当前headView的状态
        state = DONE;
    }

    /**
     * 测量headView的 宽高
     *
     * @param child
     */
    private void MeasureView(View child) {
        ViewGroup.LayoutParams lp = child.getLayoutParams();

        if (null == lp) {
            lp = new ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT,
                    LayoutParams.WRAP_CONTENT);
        }

        int measureChildWidth = ViewGroup.getChildMeasureSpec(0, 0, lp.width);
        int measureChildHeight;

        if (lp.height > 0) {
            measureChildHeight = MeasureSpec.makeMeasureSpec(lp.height,
                    MeasureSpec.EXACTLY);
        } else {
            measureChildHeight = MeasureSpec.makeMeasureSpec(0,
                    MeasureSpec.UNSPECIFIED);
        }

        child.measure(measureChildWidth, measureChildHeight);

    }

    @SuppressLint("NewApi")
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (firstVisibleIndex == 0 && !isRecored) {
                    startY = event.getY();
                    isRecored = true;
                }
                break;

            case MotionEvent.ACTION_MOVE:
                float tempY = event.getY();
                if (firstVisibleIndex == 0 && !isRecored) {
                    startY = tempY;
                    isRecored = true;
                }
                if (state != REFRESHING) {
                    if (state == PULL_TO_REFRESH) {
                        // 向下拉了 从下拉刷新的状态 来到 松开刷新的状态
                        if ((tempY - startY) / RATIO >= headContentHeight
                                && (tempY - startY) > 0) {
                            state = RELEASE_TO_REFRESH;

                            changeHeadViewOfState();
                        }
                        // 向上推了 从下拉刷新的状态 来到 刷新完成的状态
                        else if ((tempY - startY) <= 0) {
                            state = DONE;

                            changeHeadViewOfState();
                        }

                    } else if (state == RELEASE_TO_REFRESH) {
                        // 向上推了 还没有完全将HEADVIEW 隐藏掉（可以看到一部分）
                        // 从松开刷新的状态 来到 下拉刷新的状态
                        if ((tempY - startY) / RATIO < headContentHeight
                                && (tempY - startY) > 0) {
                            state = PULL_TO_REFRESH;

                            changeHeadViewOfState();
                            isBack = true;
                        }
                        // 向上推了 一下子推到了最上面 从松开刷新的状态 来到 刷新完成的状态 （数据不刷新的）
                        else if ((tempY - startY) <= 0) {
                            state = DONE;

                            changeHeadViewOfState();
                        }

                    } else if (state == DONE) {
                        // 刷新完成的状态 来到 下拉刷新的状态
                        if ((tempY - startY) > 0) {
                            state = PULL_TO_REFRESH;

                            changeHeadViewOfState();
                        }
                    }
                    if (state == PULL_TO_REFRESH) {
                        headView.setPadding(
                                0,
                                (int) ((tempY - startY) / RATIO - headContentHeight),
                                0, 0);
                    }

                    if (state == RELEASE_TO_REFRESH) {
                        headView.setPadding(
                                0,
                                (int) ((tempY - startY) / RATIO - headContentHeight),
                                0, 0);
                    }

                }

                break;

            case MotionEvent.ACTION_UP:
                if (state != REFRESHING) {
                    if (state == PULL_TO_REFRESH) {
                        // 松手
                        state = DONE;

                        changeHeadViewOfState();
                    } else if (state == RELEASE_TO_REFRESH) {
                        // 松手
                        state = REFRESHING;
                        changeHeadViewOfState();

                        // 执行数据刷新方法
                        onRefresh();
                    }
                }
                isRecored = false;
                isBack = false;
                break;
            default:
                break;
        }
        try {
            return super.onTouchEvent(event);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 执行下拉刷新
     */
    private void onRefresh() {
        if (refreshListner != null) {
            refreshListner.onRefresh();
        }
    }

    /**
     * HeadView的状态变化效果
     */
    public void changeHeadViewOfState() {
        //
        switch (state) {

            case PULL_TO_REFRESH:
                progressBar.setVisibility(View.GONE);
                headTitle.setVisibility(View.VISIBLE);
                headLastUpdate.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
                // 由 松开刷新 到 下拉刷新
                if (isBack) {
                    isBack = false;
                }

                break;

            case RELEASE_TO_REFRESH:
                progressBar.setVisibility(View.GONE);
                headTitle.setVisibility(View.VISIBLE);
                headLastUpdate.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);

                break;

            case REFRESHING:
                progressBar.setVisibility(View.VISIBLE);
                headTitle.setVisibility(View.VISIBLE);
                headLastUpdate.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);

                headView.setPadding(0, 0, 0, 0);

                break;

            case DONE:
                progressBar.setVisibility(View.GONE);
                headTitle.setVisibility(View.GONE);
                headLastUpdate.setVisibility(View.GONE);
                progressBar.setVisibility(View.GONE);

                headView.setPadding(0, -1 * headContentHeight, 0, 0);

                break;

            case 5:
                progressBar.setVisibility(View.GONE);
                headTitle.setVisibility(View.GONE);
                headLastUpdate.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
                headView.setPadding(0, -1 * headContentHeight, 0, 0);

                break;
            default:
                break;
        }

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem,
                         int visibleItemCount, int totalItemCount) {
        firstVisibleIndex = firstVisibleItem;
        lastPos = getLastVisiblePosition();
        count = totalItemCount;
//        if (PictureFragment.list != null && state == 3 && proState == 3) {
//            onRefreshComplete();
//        }
        proState = state;
        // 因为刚进入的时候,lastPos=-1,count=0,这个时候不能让它执行onAddFoot方法
        if (count >= (isCanLoad ? num : RELEASE_TO_REFRESH) && lastPos == count - 1 && !hasFoot && lastPos != -1
                && lastPos != 0) {
            hasFoot = true;
            onAddFoot();
        }
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
//        switch (scrollState){
//            case SCROLL_STATE_IDLE:
//                LoadingImgUtil.resumeLoading();
//                break;
//            case SCROLL_STATE_TOUCH_SCROLL:
//                LoadingImgUtil.resumeLoading();
//                break;
//            case SCROLL_STATE_FLING:
//                LoadingImgUtil.pauseLoading();
//                break;
//        }
        if (isFootLoading) {
            return;
        }

        if (hasFoot && scrollState == SCROLL_STATE_IDLE) {
            isFootLoading = true;
            onFootLoading();
        }
    }

    public int getfirstVisibleIndex() {
        return firstVisibleIndex;
    }

    /**
     * 设置下拉刷新监听
     *
     * @param listener
     */
    public void setOnRefreshListner(OnRefreshListner listener) {
        // 设置下拉刷新可用
        refreshListner = listener;

    }

    /**
     * 执行底部加载
     */
    public void onFootLoading() {
        if (footLoadingListener != null && isFootLoading) {
            footLoadingListener.onFootLoading();
        }
    }

    public void setOnAddFootListener(OnAddFootListener addFootListener) {
        onAddFootListener = addFootListener;
    }

    /**
     * 执行添加foot
     */
    public void onAddFoot() {
        if (onAddFootListener != null && hasFoot) {
            onAddFootListener.addFoot();
        }
    }

    /**
     * 是否添加Foot的监听器,如果写在OnFootLoadingListener中会有延迟,效果不好
     * 应该是先进入添加Foot的状态,再进入FootLoading的状态
     */
    public OnAddFootListener onAddFootListener;
    /**
     * 是否进入从底部加载数据的状态的监听器
     */
    public OnFootLoadingListener footLoadingListener;
    /**
     * 正在加载底部数据
     */
    public boolean isFootLoading = false;

    public void setOnFootLoadingListener(OnFootLoadingListener footLoading) {
        footLoadingListener = footLoading;
    }

    /**
     * 下拉刷新监听器
     *
     * @author lxj
     */
    public interface OnRefreshListner {
        /**
         * 下拉刷新的时候,在这里执行获取数据的过程
         */
        void onRefresh();
    }

    /**
     * 上拉刷新监听器
     *
     * @author lxj
     */
    public interface OnFootLoadingListener {
        /**
         * 这里是执行后台获取数据的过程
         */
        void onFootLoading();
    }

    /**
     * 添加Foot的监听器
     *
     * @author lxj
     */
    public interface OnAddFootListener {
        /**
         * 这里是用户addFootView的操作
         */
        void addFoot();
    }

    /**
     * 底部数据加载完成,用户需要加入一个removeFootView的操作
     */
    public void onFootLoadingComplete() {
        hasFoot = false;
        isFootLoading = false;
    }

    /**
     * 上拉刷新完成时 所执行的操作,更改状态,隐藏head
     */
    public void onRefreshComplete() {
        state = DONE;
        changeHeadViewOfState();

        headLastUpdate.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
    }

    /**
     * 获取当前时间
     */
    public String getCurrentTime() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return format.format(new Date());
    }

    @Override
    public void setAdapter(ListAdapter adapter) {

        super.setAdapter(adapter);
    }

    public void setIsCanLoad(boolean isCanLoad) {
        this.isCanLoad = isCanLoad;
    }
}
