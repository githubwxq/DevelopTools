package com.juziwl.uilibrary.viewpage.adapter;

import android.support.v4.view.PagerAdapter;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public abstract class BaseViewPagerAdapter<T> extends PagerAdapter {
    public List<T> mData;
    public SparseArray<View> mViews;  //根据位置存入view  外层能拿到所有的view
  
    public BaseViewPagerAdapter(List<T> data) {  //传入数据mData
        mData = data;  
        mViews = new SparseArray<View>(data.size());  
    }


    //数据会刷新 每一个条目都会刷新
    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }
    @Override  
    public int getCount() {  
        return mData.size();  
    }  
  
    @Override  
    public boolean isViewFromObject(View view, Object object) {  
        return view == object;  
    }  
  
    @Override  
    public Object instantiateItem(ViewGroup container, int position) {
        View view = mViews.get(position);  
        if (view == null) {  
            view = newView(position);  
            mViews.put(position, view);  
        }  
        container.addView(view);  
        return view;  
    }  
  
    public abstract View newView(int position);  
  
    @Override  
    public void destroyItem(ViewGroup container, int position, Object object) {  
        container.removeView(mViews.get(position));  
    }  
  
    public T getItem(int position) {  
        return mData.get(position);  
    }  
}


//、、使用方法     View view = LayoutInflater.from(this).inflate(R.layout.hualang_item_viewpager_layout, null);

//class OpenResultAdapter extends BaseViewPagerAdapter<OpenResult> {
//
//    public OpenResultAdapter(List<OpenResult> data) {
//        super(data);
//    }
//
//    @Override
//    public View newView(int position) {
//        View view = View.inflate(mContext, R.layout.view_remote_capture, null);
//        ImageView imageView = (ImageView) view.findViewById(R.id.image_view);
//        UIUtil.setLayoutParamsHeight(imageView, R.dimen.padding_common, 4, 3);
//        imageView.setAdjustViewBounds(true);
//        mImageLoader.displayImage(UrlUtil.imageUrl(getItem(position).getImgUrl()), imageView);
//        return view;
//    }
//}