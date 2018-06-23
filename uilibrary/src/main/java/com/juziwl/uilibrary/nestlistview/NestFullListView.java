package com.juziwl.uilibrary.nestlistview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhangxutong
 * @modify Neil
 * 介绍：完全伸展开的ListView（LinearLayout）
 * 作者：zhangxutong
 * 邮箱：zhangxutong@imcoming.com
 * 时间： 2016/9/9.
 */
public class NestFullListView extends LinearLayout {
    private LayoutInflater mInflater;
    /**
     * 缓存ViewHolder,按照add的顺序缓存
     */
    private List<NestFullViewHolder> mVHCahces;
    /**
     * 子项点击事件
     */
    private OnItemClickListener mOnItemClickListener;

    /**
     * 子项长按事件
     */
    private OnItemLongClickListener mOnItemLongClickListener;

    public NestFullListView(Context context) {
        this(context, null);
    }

    public NestFullListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NestFullListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mInflater = LayoutInflater.from(context);
        mVHCahces = new ArrayList<NestFullViewHolder>();
        setOrientation(VERTICAL);
        //annotate by zhangxutong 2016 09 23 for 让本控件能支持水平布局，项目的意外收获= =
        //setOrientation(VERTICAL);
    }

    /**
     * 设置点击事件
     *
     * @param listener
     */
    public void setOnItemClickListener(OnItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    /**
     * 设置长按事件
     */
    public void setOnItemLongClickListener(OnItemLongClickListener listener){
        mOnItemLongClickListener = listener;
    }

    private NestFullListViewAdapter mAdapter;

    /**
     * 外部调用  同时刷新视图
     *
     * @param mAdapter
     */
    public void setAdapter(NestFullListViewAdapter mAdapter) {
        this.mAdapter = mAdapter;
        updateUI();
    }

    public void updateUI() {
        if (null != mAdapter) {
            if (null != mAdapter.getDatas() && !mAdapter.getDatas().isEmpty()) {
                //数据源有数据
                //数据源大于现有子View不清空
                if (mAdapter.getDatas().size() > getChildCount() - getFooterCount()) {
                    //数据源小于现有子View，删除后面多的
                } else if (mAdapter.getDatas().size() < getChildCount() - getFooterCount()) {
                    removeViews(mAdapter.getDatas().size(), getChildCount() - mAdapter.getDatas().size() - getFooterCount());
                    //删除View也清缓存
                    while (mVHCahces.size() > mAdapter.getDatas().size()) {
                        mVHCahces.remove(mVHCahces.size() - 1);
                    }
                }
                for (int i = 0; i < mAdapter.getDatas().size(); i++) {
                    NestFullViewHolder holder;
                    //说明有缓存，不用inflate，否则inflate
                    if (mVHCahces.size() - 1 >= i) {
                        holder = mVHCahces.get(i);
                    } else {
                        holder = new NestFullViewHolder(getContext(), mInflater.inflate(mAdapter.getItemLayoutId(), this, false));
                        //inflate 出来后 add进来缓存
                        mVHCahces.add(holder);
                    }
                    mAdapter.onBind(i, holder);
                    //如果View没有父控件 添加
                    if (null == holder.getConvertView().getParent()) {
                        this.addView(holder.getConvertView(), getChildCount() - getFooterCount());
                    }

                    /* 增加子项点击事件 */
                    final int mPosition = i;
                    holder.getConvertView().setOnClickListener(new OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            if (mOnItemClickListener != null && mAdapter != null) {
                                mOnItemClickListener.onItemClick(NestFullListView.this, v, mPosition);
                            }
                        }
                    });

                    holder.getConvertView().setOnLongClickListener(new OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View v) {
                            if (mOnItemLongClickListener != null &&mAdapter != null){
                                mOnItemLongClickListener.onItemLongClick(NestFullListView.this, v, mPosition);
                            }
                            return true;
                        }
                    });

                }
            } else {
                //数据源没数据 清空视图
                removeViews(0, getChildCount() - getFooterCount());
            }
        } else {
            //适配器为空 清空视图
            removeViews(0, getChildCount() - getFooterCount());
        }
    }

    /******
     * 2016 10 11 add 增加FooterView
     * 暂时用存View，觉得可以不存。还没考虑好
     */
    private List<View> mFooterViews;

    public int getFooterCount() {
        return mFooterViews != null ? mFooterViews.size() : 0;
    }

    public void addFooterView(View footer) {
        if (null == mFooterViews) {
            mFooterViews = new ArrayList<>();
        }
        mFooterViews.add(footer);
        addView(footer);
    }

    /**
     * 在指定位置插入footerview
     *
     * @param pos
     * @param footer
     */
    public void setFooterView(int pos, View footer) {
        if (null == mFooterViews || mFooterViews.size() <= pos) {
            addFooterView(footer);
        } else {
            mFooterViews.set(pos, footer);
            //5 item 1 footer , pos 0, getchildcout =6, remove :
            int realPos = getChildCount() - getFooterCount() + pos;
            removeViewAt(realPos);
            addView(footer, realPos);
        }
    }


    /**
     * 子项点击事件的接口
     */
    public interface OnItemClickListener {

        /**
         * 子项点击事件
         *
         * @param parent
         * @param view
         * @param position
         */
        void onItemClick(NestFullListView parent, View view, int position);
    }

    /**
     * 子项长按点击事件的接口
     */
    public interface OnItemLongClickListener {

        /**
         * 子项点击事件
         *
         * @param parent
         * @param view
         * @param position
         */
        void onItemLongClick(NestFullListView parent, View view, int position);
    }

}
//使用方法
//
//holder1.ll_addcomment.setAdapter(new NestFullListViewAdapter<NewRewardAnswerData.QuetionDataBean.AnswerListBean>(R.layout.rewardanswercomment, answerLists) {
//@Override
//public void onBind(int pos, final NewRewardAnswerData.QuetionDataBean.AnswerListBean answerListBean, NestFullViewHolder holder) {
//        MTextView   userinfo = (MTextView) holder.getView(R.id.userinfo);
//        ArrayList<String> picList = new ArrayList<String>();
//        userinfo.setMText(smileyParser.replace(answerListBean.getUserName() + ": " + answerListBean.getS_content().trim(), userinfo),
//        0, 0, answerListBean.getUserName().length() + 1, mContext.getResources().getColor(R.color.ljqdno));
//        GridView  grid = (GridView) holder.getView(R.id.gv_grid);
//        RelativeLayout  rl_grid = (RelativeLayout)  holder.getView(R.id.rl_grid);
//        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//@Override
//public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        Bundle b = new Bundle();
//        b.putInt("ID", position);
//        b.putString("pics", answerListBean.getS_pic());
//        CommonTools.startActivity(mContext, ClazzPhotoActivity.class, b);
//        }
//        });
//        if (CommonTools.isEmpty(answerListBean.getS_pic())) {
//        rl_grid.setVisibility(View.GONE);
//        } else {
//        rl_grid.setVisibility(View.VISIBLE);
//        String[] split = answerListBean.getS_pic().split(";");
//        for (int i = 0; i < split.length; i++) {
//        picList.add(Global.baseURL + split[i]);
//        }
//        }
//
//
//        grid.setAdapter(new PicGridAdapter(rl_grid,picList));
//
//        }
//        });
//
//<com.juziwl.xiaoxin.view.nestlistview.NestFullListView
//        android:id="@+id/ll_addcomment"
//        android:layout_width="match_parent"
//        android:layout_height="wrap_content"
//        android:orientation="vertical" />