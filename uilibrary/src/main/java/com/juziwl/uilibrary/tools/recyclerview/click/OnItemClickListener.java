package com.juziwl.uilibrary.tools.recyclerview.click;

import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Administrator on 2016/8/27.
 */

public interface OnItemClickListener<T> {
    /**
     * Callback method to be invoked when an item in the RecyclerView
     * has been clicked.
     *
     * @param parent   The RecyclerView where the click happened.
     * @param position The position of the view in the adapter.
     */
    void onItemClick(ViewGroup parent, View view, T data, int position);
}
