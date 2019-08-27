package com.juziwl.uilibrary.banner;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import com.juziwl.uilibrary.R;
import com.juziwl.uilibrary.banner.loader.ImageLoaderInterface;
import com.juziwl.uilibrary.imageview.RectImageView;
import com.wxq.commonlibrary.glide.LoadingImgUtil;

/**
 * @author dai
 * @version V_1.0.0
 * @date 2017/3/1
 * @description
 */
public class GlideImageLoader implements ImageLoaderInterface<View> {
    public static final int NORMAL = 0;
    public static final int RECT = 1;
    public static final int SIMULATION = 2; // 空间轮播图的默认图

    public int mType;

    public GlideImageLoader(int type) {
        mType = type;
    }


    @Override
    public void displayImage(Context context, Object path, View imageView) {
        if (mType !=  SIMULATION) {
            LoadingImgUtil.displayLoopImageView(path.toString(), (ImageView) imageView, null, 2);
        } else {
            LoadingImgUtil.displayLoopImageView(path.toString(), (ImageView) imageView, null, 3);
        }
    }

    @Override
    public View createImageView(Context context) {
        if (NORMAL == mType) {
            return new ImageView(context);
        } else if (RECT == mType) {
            RectImageView imageView = new RectImageView(context);
            //4dp
            imageView.setBorderRadius(4);
            return imageView;
        } else if (SIMULATION == mType) {
            return new ImageView(context);
        } else {
            View view = new View(context);
            view.setBackgroundResource(R.drawable.corners_4__stroke_dedede);
            return view;
        }
    }
}
