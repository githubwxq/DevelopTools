package com.juziwl.uilibrary.flowlayout;

import android.view.View;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public abstract class SuperFlexboxLayoutAdapter<T> {

    private List<T> mDatas;


    private int  mLayoutId;

    private OnDataChangedListener mOnDataChangedListener;
    @Deprecated
    private HashSet<Integer> mCheckedPosList = new HashSet<Integer>();

    public SuperFlexboxLayoutAdapter(int layoutId, List<T> datas) {
        mDatas = datas==null?new ArrayList<T>():datas;
        mLayoutId = layoutId;

    }

    public abstract void bindView(View tagView, int i, T item);


    interface OnDataChangedListener {
        void onChanged();
    }

    void setOnDataChangedListener(OnDataChangedListener listener) {
        mOnDataChangedListener = listener;
    }


    public int getCount() {
        return mDatas.size();
    }

    public void notifyDataChanged() {
        if (mOnDataChangedListener != null)
            mOnDataChangedListener.onChanged();
    }


    public int getmLayoutId() {
        return mLayoutId;
    }
    public T getItem(int position) {
        return mDatas.get(position);
    }

}


/**
 *  使用方法
 *   sfl.setMargin(0,0,10,10).setAdapter(new SuperFlexboxLayoutAdapter<TagModel>(R.layout.recycle_item,tagModels) {
 * @Override
 * public void bindView(View tagView, int i, TagModel item) {
 *         TextView  view=tagView.findViewById(R.id.text);
 *         view.setText(item.name);
 *         if (item.isSelect) {
 *         view.setBackgroundColor(getResources().getColor(R.color.blue100));
 *         }else {
 *         view.setBackgroundColor(getResources().getColor(R.color.yellow_200));
 *         }
 *         view.setOnClickListener(new View.OnClickListener() {
 * @Override
 * public void onClick(View v) {
 *         item.isSelect=!item.isSelect;
 *         notifyDataChanged();
 *         }
 *         });
 *         }
 *         });
 */
