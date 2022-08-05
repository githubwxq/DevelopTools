package com.example.uitestdemo.fragment.components.expandtextview;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.uitestdemo.R;
import com.example.uitestdemo.fragment.components.qrcode.QrDetailFragment;
import com.facebook.stetho.common.android.HandlerUtil;
import com.juziwl.uilibrary.niceplayer.LogUtil;
import com.juziwl.uilibrary.textview.expandtextview.ExpandTextView;
import com.wxq.commonlibrary.base.BaseFragment;
import com.wxq.commonlibrary.base.BasePresenter;
import com.wxq.commonlibrary.util.LogUtils;
import com.wxq.commonlibrary.util.UIHandler;

import butterknife.BindView;


public class ExpandTextViewFragment extends BaseFragment {

    @BindView(R.id.expand_textview)
    ExpandTextView mContent;

    @BindView(R.id.tv_single)
    TextView tv_single;


    @BindView(R.id.tv_more)
    TextView tv_more;



    @Override
    protected BasePresenter initPresenter() {
        return null;
    }


    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_expand_text_view;
    }
    public static ExpandTextViewFragment newInstance() {
        ExpandTextViewFragment fragment = new ExpandTextViewFragment();

        return fragment;
    }
    @Override
    protected void initViews() {


        mContent.initWidth(1080);
        mContent.setMaxLines(3);
        mContent.setHasAnimation(true);
        mContent.setCloseInNewLine(false);

        mContent.setOpenSuffixColor(getResources().getColor(R.color.teal_200));
        mContent.setCloseSuffixColor(getResources().getColor(R.color.teal_200));
        mContent.setOriginalText("世界上总有一些人总有一两条漏网之鱼总有一两条漏网之鱼的出生是不被期待的。" +
                "在那个年代如火如荼的计划生育中，总有一两条漏网之鱼。" +
                "南方的春天阴雨绵绵，空气中弥漫着浓郁的湿气，让人不由得觉得烦躁，难受。" +
                "一个女人挺着大肚子，东躲西藏，四处打听，吃尽了闭门羹。所幸皇天不负有心人，" +
                "几番周折后，她终于在临盆前的一天找到了愿意接生的医生。"
        );

//        mContent.setOriginalText("世界上总有一些人总有一两条漏");

        mContent.setOpenAndCloseCallback(new ExpandTextView.OpenAndCloseCallback() {
            @Override
            public void onOpen() {
                int height2 = mContent.getHeight();
                LogUtils.e("onOpen"+height2);
            }

            @Override
            public void onClose() {
                int height2 = mContent.getHeight();
                LogUtils.e("onClose"+height2);
            }
        });
        UIHandler.getInstance().postDelayed(new Runnable() {
            @Override
            public void run() {
                int height1 = tv_single.getHeight();
                int height = tv_more.getHeight();
                LogUtils.e("tv_single"+height1);
                LogUtils.e("tv_more"+height);

                int height2 = mContent.getHeight();
                //237
                LogUtils.e("mContent"+height2);

            }
        },2000);




    }


    public static int dp2px(Context context, float dpValue) {
        int res = 0;
        final float scale = context.getResources().getDisplayMetrics().density;
        if (dpValue < 0)
            res = -(int) (-dpValue * scale + 0.5f);
        else
            res = (int) (dpValue * scale + 0.5f);
        return res;
    }


}