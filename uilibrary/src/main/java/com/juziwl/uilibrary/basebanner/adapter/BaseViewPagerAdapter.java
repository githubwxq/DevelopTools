package com.juziwl.uilibrary.basebanner.adapter;


import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;


import java.util.List;

public abstract class BaseViewPagerAdapter<T> extends PagerAdapter {

    public List<T> mData;

    public SparseArray<View> mViews;  //根据位置存入view  外层能拿到所有的view

    int mLayoutId;

    Context mContext;


    public BaseViewPagerAdapter(Context context, List<T> data, int layoutId) {  //传入数据mData
        mData = data;
        mLayoutId = layoutId;
        mContext=context;
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
        ViewPageHolder holder = ViewPageHolder.get(mContext, container, container,
                mLayoutId, position);
        convert(holder, getItem(position));
        container.addView(holder.getConvertView());
        return holder.getConvertView();
    }

    protected abstract void convert(ViewPageHolder holder, T item);


  
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