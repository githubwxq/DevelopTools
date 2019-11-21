package com.juziwl.uilibrary.basebanner.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.util.Linkify;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.Checkable;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;


public class ViewPageHolder {
    private SparseArray<View> mViews;
    private int mPosition;
    private View mConvertView;
    private Context mContext;
    private int mLayoutId;

    public ViewPageHolder(Context context, View itemView, ViewGroup parent, int position) {
    
        mContext = context;
        mConvertView = itemView;
        mPosition = position;
        mViews = new SparseArray<View>();
        mConvertView.setTag(itemView.getId(), this);

    }


    public static ViewPageHolder get(final Context context, View convertView,
                                     ViewGroup parent, int layoutId, int position) {
            View itemView = LayoutInflater.from(context).inflate(layoutId,parent,false);
            ViewPageHolder holder = new ViewPageHolder(context, itemView, parent, position);
            holder.mLayoutId = layoutId;
            return holder;
    }

    /**
     * 通过viewId获取控件
     *
     * @param viewId
     * @return
     */
    public <T extends View> T getView(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    public View getConvertView() {
        return mConvertView;
    }

    /**
     * 设置TextView的值
     *
     * @param viewId
     * @param text
     * @return
     */
    public ViewPageHolder setText(int viewId, CharSequence text) {
        TextView tv = getView(viewId);
        tv.setText(text);
        return this;
    }

    public ViewPageHolder setText(int viewId, Number text) {
        TextView tv = getView(viewId);
        tv.setText(String.valueOf(text));
        return this;
    }

    public ViewPageHolder setImageResource(int viewId, int resId) {
        ImageView view = getView(viewId);
        view.setImageResource(resId);
        return this;
    }

    public ViewPageHolder setImageBitmap(int viewId, Bitmap bitmap) {
        ImageView view = getView(viewId);
        view.setImageBitmap(bitmap);
        return this;
    }


    public ViewPageHolder setImageBitmap(int viewId,int picImg){
        ImageView view = getView(viewId);
        view.setImageResource(picImg);
        return this;
    }

//    public ViewPageHolder setImagePath(int viewId,String path){
//        LoadingImgUtil.loadimg(path,getView(viewId),true);
//        return this;
//    }
//
//    public ViewPageHolder setImagePath(int viewId,String path,boolean isShowHead){
//        LoadingImgUtil.loadimg(path,getView(viewId),isShowHead);
//        return this;
//    }



    public ViewPageHolder setImageDrawable(int viewId, Drawable drawable) {
        ImageView view = getView(viewId);
        view.setImageDrawable(drawable);
        return this;
    }

    public ViewPageHolder setBackgroundColor(int viewId, int color) {
        View view = getView(viewId);
        view.setBackgroundColor(color);
        return this;
    }

    public ViewPageHolder setBackgroundRes(int viewId, int backgroundRes) {
        View view = getView(viewId);
        view.setBackgroundResource(backgroundRes);
        return this;
    }


    public ViewPageHolder setTextColor(int viewId, int textColor) {
        TextView view = getView(viewId);
        view.setTextColor(textColor);
        return this;
    }

    public ViewPageHolder setTextColorRes(int viewId, int textColorRes) {
        TextView view = getView(viewId);
        view.setTextColor(mContext.getResources().getColor(textColorRes));
        return this;
    }

    @SuppressLint("NewApi")
    public ViewPageHolder setAlpha(int viewId, float value) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            getView(viewId).setAlpha(value);
        } else {
            // Pre-honeycomb hack to set Alpha value
            AlphaAnimation alpha = new AlphaAnimation(value, value);
            alpha.setDuration(0);
            alpha.setFillAfter(true);
            getView(viewId).startAnimation(alpha);
        }
        return this;
    }

    public ViewPageHolder setVisible(int viewId, boolean visible) {
        View view = getView(viewId);
        view.setVisibility(visible ? View.VISIBLE : View.GONE);
        return this;
    }

    public ViewPageHolder setVisible(View view, boolean visible) {
        view.setVisibility(visible ? View.VISIBLE : View.GONE);
        return this;
    }

    public ViewPageHolder linkify(int viewId) {
        TextView view = getView(viewId);
        Linkify.addLinks(view, Linkify.ALL);
        return this;
    }

    public ViewPageHolder setTypeface(Typeface typeface, int... viewIds) {
        for (int viewId : viewIds) {
            TextView view = getView(viewId);
            view.setTypeface(typeface);
            view.setPaintFlags(view.getPaintFlags() | Paint.SUBPIXEL_TEXT_FLAG);
        }
        return this;
    }

    public ViewPageHolder setProgress(int viewId, int progress) {
        ProgressBar view = getView(viewId);
        view.setProgress(progress);
        return this;
    }

    public ViewPageHolder setProgress(int viewId, int progress, int max) {
        ProgressBar view = getView(viewId);
        view.setMax(max);
        view.setProgress(progress);
        return this;
    }

    public ViewPageHolder setMax(int viewId, int max) {
        ProgressBar view = getView(viewId);
        view.setMax(max);
        return this;
    }

    public ViewPageHolder setRating(int viewId, float rating) {
        RatingBar view = getView(viewId);
        view.setRating(rating);
        return this;
    }

    public ViewPageHolder setRating(int viewId, float rating, int max) {
        RatingBar view = getView(viewId);
        view.setMax(max);
        view.setRating(rating);
        return this;
    }

    public ViewPageHolder setTag(int viewId, Object tag) {
        View view = getView(viewId);
        view.setTag(tag);
        return this;
    }

    public ViewPageHolder setTag(int viewId, int key, Object tag) {
        View view = getView(viewId);
        view.setTag(key, tag);
        return this;
    }

    public ViewPageHolder setChecked(int viewId, boolean checked) {
        Checkable view = (Checkable) getView(viewId);
        view.setChecked(checked);
        return this;
    }

    public void setViewSize(View view, int size) {
        ViewGroup.LayoutParams params = view.getLayoutParams();
        if (params == null) {
            params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        params.height = size;
        params.width = size;
        view.setLayoutParams(params);
        view.setMinimumWidth(size);
        view.setMinimumHeight(size);
    }

    /**
     * 关于事件的
     */
    public ViewPageHolder setOnClickListener(int viewId,
                                         View.OnClickListener listener) {
        View view = getView(viewId);
        view.setOnClickListener(listener);
        return this;
    }

    public ViewPageHolder setOnTouchListener(int viewId,
                                         View.OnTouchListener listener) {
        View view = getView(viewId);
        view.setOnTouchListener(listener);
        return this;
    }

    public ViewPageHolder setOnLongClickListener(int viewId,
                                             View.OnLongClickListener listener) {
        View view = getView(viewId);
        view.setOnLongClickListener(listener);
        return this;
    }


    public void updatePosition(int position) {
        mPosition = position;
    }

    public int getmPosition() {
        return mPosition;
    }

    public int getLayoutId() {
        return mLayoutId;
    }
}
