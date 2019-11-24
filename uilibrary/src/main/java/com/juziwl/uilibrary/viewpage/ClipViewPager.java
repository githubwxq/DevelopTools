package com.juziwl.uilibrary.viewpage;

import android.content.Context;
import androidx.viewpager.widget.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;


//支持左右滑动切换的切换头像一页显示多个item的空间viewpage
public class ClipViewPager extends ViewPager {
    public ClipViewPager(Context context) {
        super(context);
    }

    public ClipViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    float startX = 0;

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            startX = ev.getRawX();
        }

        if (ev.getAction() == MotionEvent.ACTION_UP) {
            int childCount = getChildCount();
            float endX = ev.getRawX();
            if (childCount > 0) {

                if (getCurrentItem() == 0) {
                    if (startX < endX) {

                        return super.dispatchTouchEvent(ev);
                    }
                } else if (getCurrentItem() == childCount - 1) {
                    if (startX > endX) {
                        return super.dispatchTouchEvent(ev);
                    }
                }
            }


            View view = viewoOfClickOnScreen(ev);
            if (view != null) {
                int index = indexOfChild(view);
                if (getCurrentItem() != index) {
                    setCurrentItem(indexOfChild(view));
                }
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * @param ev
     * @return
     */
    private View viewoOfClickOnScreen(MotionEvent ev) {
        int childCount = getChildCount();
        int[] location = new int[2];
        for (int i = 0; i < childCount; i++) {
            View v = getChildAt(i);
            v.getLocationOnScreen(location);

            int minX = location[0];
            int minY = getTop();

            int maxX = location[0] + v.getWidth();
            int maxY = getBottom();

            float x = ev.getRawX();
            float y = ev.getY();

            if ((x > minX && x < maxX) && (y > minY && y < maxY)) {
                return v;
            }
        }
        return null;
    }
}
//
//<LinearLayout
//
//            android:id="@+id/ll_out_view"
//                    android:layout_width="match_parent"
//                    android:layout_height="wrap_content"
//                    android:layout_below="@id/top_heard"
//                    android:layout_centerHorizontal="true"
//                    android:layout_marginTop="20dp"
//                    android:clipChildren="false"
//                    android:gravity="center_horizontal"
//                    android:layerType="software">
//
//<com.juziwl.uilibrary.viewpage.ClipViewPager
//        android:id="@+id/child_vp"
//        android:layout_width="wrap_content"
//        android:layout_height="107dp"
//        android:clipChildren="false"></com.juziwl.uilibrary.viewpage.ClipViewPager>
//
//</LinearLayout>


//        ll_out_view.setOnTouchListener(new View.OnTouchListener() {
//@Override
//public boolean onTouch(View v, MotionEvent event) {
//        return childVp.dispatchTouchEvent(event);
//        }
//        });


//    public void setDataForViewPage(List<Child> childList) {
//        mlist.clear();
//        mlist = childList;
//        childVp.setOffscreenPageLimit(8); //缓存8个
//        childVp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//
//            }
//        });
//
//        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
//                DisplayUtils.dip2px(107), DisplayUtils.dip2px(107));
//        childVp.setLayoutParams(params);
//        childVp.setPageMargin(DisplayUtils.sp2px(25));
//        childVp.setAdapter(new BaseViewPagerAdapter<Child>(mlist) {
//            @Override
//            public View newView(int position) {
////                View view = LayoutInflater.from(getActivity()).inflate(R.layout.hualang_item_viewpager_layout, null);
//
//                RectImageView rectImageView = new RectImageView(context);
//                rectImageView.setType(RectImageView.TYPE_CIRCLE);
//                LoadingImgUtil.loadimg(mlist.get(position).pic, rectImageView, true);
//                return rectImageView;
//            }
//        });
//        childVp.setPageTransformer(true, new ZoomOutPageTransformer());
//
//    }