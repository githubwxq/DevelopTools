package com.juziwl.uilibrary.popupwindow;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

/**
 * @author wxq
 * @modify Neil
 */
public class PopupWindowViewHolder {
    private final SparseArray<View> mViews;
    private View mDialogView;

    private PopupWindowViewHolder(Context context, int layoutId) {
        this.mViews = new SparseArray<View>();
        mDialogView = LayoutInflater.from(context).inflate(layoutId, null);
    }

    public static PopupWindowViewHolder get(Context context, int layoutId) {
        return new PopupWindowViewHolder(context, layoutId);
    }

    public View getConvertView() {
        return mDialogView;
    }

    /**
     * Set the string for TextView
     *
     * @param viewId
     * @param text
     * @return
     */
    public PopupWindowViewHolder setText(int viewId, CharSequence text) {
        TextView view = getView(viewId);
        view.setText(text);
        return this;
    }

    /**
     * set view visible
     *
     * @param viewId
     * @return
     */
    public PopupWindowViewHolder setViewInViSible(int viewId) {
        TextView view = getView(viewId);
        view.setVisibility(View.INVISIBLE);
        return this;
    }

    /**
     * set view visible
     *
     * @param viewId
     * @return
     */
    public PopupWindowViewHolder setViewViSible(int viewId) {
        TextView view = getView(viewId);
        view.setVisibility(View.VISIBLE);
        return this;
    }

    /**
     * set view gone
     *
     * @param viewId
     * @return
     */
    public PopupWindowViewHolder setViewGone(int viewId) {
        TextView view = getView(viewId);
        view.setVisibility(View.GONE);
        return this;
    }

    /**
     * 设置点击
     */
    public PopupWindowViewHolder setOnClick(int viewId, OnClickListener onClick) {
        View view = getView(viewId);
        view.setOnClickListener(onClick);
        return this;
    }

    /**
     * Through control the Id of the access to control, if not join views
     *
     * @param viewId
     * @return
     */

    public <T extends View> T getView(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = mDialogView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }
}
