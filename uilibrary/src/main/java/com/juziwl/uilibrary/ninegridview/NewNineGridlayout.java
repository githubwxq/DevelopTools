package com.juziwl.uilibrary.ninegridview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.juziwl.uilibrary.R;

import java.util.Arrays;
import java.util.List;


/**
 * @author wxq
 * @version V_5.0.0
 * @modify Neil
 * @date 2017/2/17 0017
 * @modifydate 2017/11/1
 * @description
 */
public class NewNineGridlayout extends LinearLayout {

    private Context mcontext;
    private NineGridlayout classPic;
    private static final int NUMBER_2 = 2;
    private static final int NUMBER_10 = 10;

    public NewNineGridlayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public NewNineGridlayout(Context context) {
        this(context, null, 0);
    }

    public NewNineGridlayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);

    }

    /**
     * 初始化控件
     *
     * @param context
     */
    private void init(Context context) {
        ViewGroup view = (ViewGroup) View.inflate(context, R.layout.common_new_nine_grid, this);
        classPic = (NineGridlayout) view.getChildAt(0);
        mcontext = context;
    }

    /**
     * 设置图片宽度以及监听等
     */
    public void showPic(int nineGridViewWidth, final String imgUrl, NineGridlayout.onNineGirdItemClickListener listener) {
        String[] picUrlStr2 = imgUrl.split(";");
        List<String> list = Arrays.asList(picUrlStr2);
        classPic.setTotalWidth(nineGridViewWidth);
        classPic.setImagesData(list, false, 0, 0);
        classPic.setonNineGirdItemClickListener(listener);
    }

    /**
     * 设置图片宽度以及监听等
     */
    public void showPic(int nineGridViewWidth, final String imgUrl, NineGridlayout.onNineGirdItemClickListener listener,
                        NineGridlayout.OnNineGirdItemLongClickListener longClickListener) {
        String[] picUrlStr2 = imgUrl.split(";");
        List<String> list = Arrays.asList(picUrlStr2);
        classPic.setTotalWidth(nineGridViewWidth);
        classPic.setImagesData(list, false, 0, 0);
        classPic.setonNineGirdItemClickListener(listener);
        classPic.setOnNineGirdItemLongClickListener(longClickListener);
    }

    /**
     * 该方法是空间用的
     * b 是否来自空间
     */
    public void showPic(int nineGridViewWidth, final String imgUrl, NineGridlayout.onNineGirdItemClickListener listener, boolean b, int width, int height) {
        String[] picUrlStr2 = imgUrl.split(";");
        List<String> list = Arrays.asList(picUrlStr2);
        classPic.setTotalWidth(nineGridViewWidth);
        classPic.setImagesData(list, b, width, height);
        classPic.setonNineGirdItemClickListener(listener);
    }


}
