package com.juziwl.uilibrary.recycler;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.juziwl.uilibrary.R;
import com.juziwl.uilibrary.easycommonadapter.BaseAdapterHelper;
import com.juziwl.uilibrary.easycommonadapter.CommonRecyclerAdapter;
import com.juziwl.uilibrary.recycler.itemdecoration.GrideItemDecorationForKeyBoard;
import com.juziwl.uilibrary.utils.ConvertUtils;
import com.juziwl.uilibrary.utils.ScreenUtils;

import java.util.ArrayList;
import java.util.List;

import tv.danmaku.ijk.media.player_x86.Pragma;

/**
 * 键盘空间
 */
public class KeyBoardView extends LinearLayout {

    private Context mContext = null;
    private ArrayList<View> dotViews = new ArrayList<>();
    private float size = 6;
    private int marginSize = 15;
    private int pointSize;
    private int marginLeft;
    private LayoutParams layoutParams = null;
    private int normalId = R.drawable.white_point;
    private int selectId = R.drawable.green_point;

    View view;

    RecyclerView rvList;

    private int horizonWidth = 12;
    private int verticalWidth = 6;

    public int totalNumber; //为0 显示完成

    public List<String> mlist = new ArrayList<>();

    CommonRecyclerAdapter<String> adapter;

    public KeyBoardView(Context context) {
        this(context, null);
    }

    public KeyBoardView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public KeyBoardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();
    }

    private void init() {
        view = View.inflate(mContext, R.layout.key_board_layout, this);
        rvList = (RecyclerView) view.findViewById(R.id.rv_list);
        mlist.add("1");
        mlist.add("4");
        mlist.add("7");
        mlist.add("0");
        mlist.add("2");
        mlist.add("5");
        mlist.add("8");
        mlist.add(".");
        mlist.add("3");
        mlist.add("6");
        mlist.add("9");
        mlist.add("00");
        mlist.add("删除");
        mlist.add("完成");
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 4, GridLayoutManager.HORIZONTAL, false);
        rvList.setLayoutManager(gridLayoutManager);
        rvList.addItemDecoration(new GrideItemDecorationForKeyBoard(getContext()));
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (position == mlist.size() - 1) {
                    return 3;
                } else {
                    return 1;
                }

            }
        });
        rvList.setAdapter(adapter = new CommonRecyclerAdapter<String>(getContext(), R.layout.snap_recycle_item, mlist) {
            @Override
            public void onUpdate(BaseAdapterHelper helper, String item, int position) {
                helper.setText(R.id.tv_tip, item);
                LinearLayout.LayoutParams tvTopTip = (LayoutParams) helper.getView(R.id.tv_tip).getLayoutParams();
                tvTopTip.width = (ScreenUtils.getScreenWidth(mContext) - dp2px(5 * horizonWidth, mContext)) / 4;
                if (position == mlist.size() - 1) {
                    helper.setBackgroundRes(R.id.tv_tip, R.drawable.keybroadcast_next_selector);
                    helper.setTextColor(R.id.tv_tip, mContext.getResources().getColor(R.color.white));
                } else {
                    helper.setBackgroundRes(R.id.tv_tip, R.drawable.keybroadcast_selector);
                    helper.setTextColor(R.id.tv_tip, mContext.getResources().getColor(R.color.common_999999));
                }
                helper.getView(R.id.tv_tip).setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (listener != null) {
                            if (position <= 11) {
                                listener.clickNumber(item);
                            } else if (position == 12) {
                                listener.clickDelete(item);
                            } else {
                                listener.clickNextOrFinish(item);
                            }
                        }
                    }
                });
            }
        });
    }

    /**
     * 改变最后一个键的名称
     *
     * @param newName
     */
    public void setLastKeyName(String newName) {
        //改变最后一个建的名称
        mlist.remove(mlist.size() - 1);
        mlist.add(newName);
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }


    public static int dp2px(final float dpValue, Context context) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }


    public void setListener(KeyClickListener listener) {
        this.listener = listener;
    }

    private KeyClickListener listener;

    public interface KeyClickListener {
        void clickNumber(String number);

        void clickDelete(String delete);

        void clickNextOrFinish(String nextOrFinish);
    }
}