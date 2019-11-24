package com.juziwl.uilibrary.viewpage.pageindicator;

import android.content.Context;
import androidx.core.content.ContextCompat;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.juziwl.uilibrary.R;
import com.wxq.commonlibrary.util.ConvertUtils;

import java.util.ArrayList;
import java.util.List;

public class PreviewIndicator extends LinearLayout {

    //指示器个数
    private final int INDICATOR_COUNT = 3;
    private List<ImageView> mImageList = new ArrayList<>();

    public PreviewIndicator(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PreviewIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        for (int i = 0; i < INDICATOR_COUNT; i++) {
            ImageView imageView = new ImageView(getContext());
            if (i == 0) {
                imageView.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.bg_circle_selected));
            } else {
                imageView.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.bg_circle_unselected));
            }
            LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(ConvertUtils.dp2px( 10), 0, ConvertUtils.dp2px(10), 0);
            addView(imageView, params);
            mImageList.add(imageView);
        }
    }

    public void setSelected(int position) {
        for (int i = 0; i < mImageList.size(); i++) {
            ImageView imageView = mImageList.get(i);
            if (i == position) {
                imageView.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.bg_circle_selected));
            } else {
                imageView.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.bg_circle_unselected));
            }
        }
    }

}
