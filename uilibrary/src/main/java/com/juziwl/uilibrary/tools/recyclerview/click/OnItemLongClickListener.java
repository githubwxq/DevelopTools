package com.juziwl.uilibrary.tools.recyclerview.click;

/**
 * Created by Administrator on 2016/8/27.
 */

import android.view.View;
import android.view.ViewGroup;

/**
 * Interface definition for a callback to be invoked when an item in the
 * RecyclerView has been clicked and held.
 */
public interface OnItemLongClickListener<T> {
    /**
     * Callback method to be invoked when an item in the RecyclerView
     * has been clicked and held.
     *
     * @param parent   The RecyclerView where the click happened
     * @param view     The view within the RecyclerView that was clicked
     * @param position The position of the view in the list
     * @return true if the callback consumed the long click, false otherwise
     */
    boolean onItemLongClick(ViewGroup parent, View view, T data, int position);
}