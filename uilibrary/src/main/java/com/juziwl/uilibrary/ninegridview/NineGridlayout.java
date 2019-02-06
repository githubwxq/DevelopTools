package com.juziwl.uilibrary.ninegridview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.juziwl.uilibrary.R;
import com.juziwl.uilibrary.imageview.RectImageView;
import com.wxq.commonlibrary.constant.GlobalContent;
import com.wxq.commonlibrary.glide.LoadingImgUtil;
import com.wxq.commonlibrary.util.StringUtils;


import java.util.List;


/**
 * Created by Pan_ on 2015/2/2.
 *
 * @author Pan_
 * @modify Neil
 * @modifydate 2017/11/1
 */
public class NineGridlayout extends ViewGroup {

    /**
     * 图片之间的间隔
     */
    private int gap = 8;
    private int columns;
    private int rows;
    private List<String> listData;
    private int totalWidth = 0;
    private static final int NUMBER_1 = 1;
    private static final int NUMBER_2 = 2;
    private static final int NUMBER_3 = 3;
    private static final int NUMBER_4 = 4;
    private static final int NUMBER_6 = 6;
    private static final int NUMBER_10 = 10;

    private boolean isSpace;
    private double oneWidth;
    private double oneHeight;

    private int singleWidth = 0;
    private int singleHeight = 0;

    private View v;


    public NineGridlayout(Context context) {
        this(context, null);
    }

    public NineGridlayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        singleWidth = 0;
        singleHeight = 0;
        if (totalWidth == 0) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            return;
        }
        if (listData.size() == 1) {
            if (isSpace) {
                //空间单张图片出现的效果
                onePicView();
            } else {
                singleWidth = totalWidth;
                singleHeight = (totalWidth - NUMBER_10) / NUMBER_2;
            }
        }
       /* if (listData.size() == NUMBER_2) {
            singleWidth = (totalWidth - NUMBER_10) / NUMBER_2;
            singleHeight = (totalWidth - NUMBER_10) / NUMBER_2;
        }*/
        if ((listData.size() > NUMBER_1)) {
            singleWidth = (totalWidth - gap * (NUMBER_3 - 1)) / NUMBER_3;
            singleHeight = singleWidth;
        }

        for (int i = 0; i < getChildCount(); i++) {
            if (getChildAt(i).equals(v)) {
                measureChild(v, widthMeasureSpec, heightMeasureSpec);
            }
        }
        setMeasuredDimension(totalWidth, singleHeight * rows + gap * (rows - 1));
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (totalWidth == 0) {
            return;
        }
        double radio = 1.1;


        for (int i = 0; i < getChildCount(); i++) {
            int[] position = findPosition(i);
            int left = (singleWidth + gap) * position[1];
            int top = (singleHeight + gap) * position[0];
            int right = left + singleWidth;
            int bottom = top + singleHeight;
            if (getChildAt(i) instanceof ImageView) {
                ImageView childrenView = (ImageView) getChildAt(i);
                String image = listData.get(i);

                // TODO: 2018/6/23 0023  占时注释掉
                if (StringUtils.isEmpty(image)) {
                    childrenView.setImageResource(R.mipmap.common_falseimg);
                } else {
                    LoadingImgUtil.loadimg(image
                          , childrenView, false);
                }childrenView.layout(left, top, right, bottom);
            } else if (getChildAt(i) instanceof LinearLayout) {
                getChildAt(i).layout(getWidth() - getChildAt(i).getMeasuredWidth() - 10, getWidth() - getChildAt(i).getMeasuredHeight() - 10, getWidth() - 10, getWidth() - 10);
            }
        }
    }

    private void onePicView() {
        if (oneWidth == 0 || oneHeight == 0) {
            singleWidth = totalWidth;
            singleHeight = (totalWidth - NUMBER_10) / NUMBER_2;
        } else {
            double maxWidth = dp2px(180);
            double maxHeight = dp2px(180);
            if (oneWidth > oneHeight) {
//                        ImageView img = (ImageView) getChildAt(0);
                if (oneWidth / 3 >= oneHeight) {
                    singleWidth = totalWidth / 2;
                    singleHeight = totalWidth / 2 / 3;
                } else {
//                    if (oneWidth >= maxWidth) {
                    singleWidth = (int) maxWidth;
                    singleHeight = (int) (oneHeight * maxWidth / oneWidth);
//                    } else {
//                        singleWidth = (int) oneWidth;
//                        singleHeight = (int) oneHeight;
//                    }
                }
            } else if (oneWidth < oneHeight) {
                if (oneHeight / 3 >= oneWidth) {
                    singleWidth = totalWidth / 2 / 3;
                    singleHeight = totalWidth / 2;
                } else {
//                    if (oneHeight >= maxHeight) {
                    singleHeight = (int) maxHeight;
                    singleWidth = (int) (oneWidth * maxHeight / oneHeight);
//                    } else {
//                        singleHeight = (int) oneHeight;
//                        singleWidth = (int) oneWidth;
//                    }
                }
            } else {
                singleHeight = (int) maxHeight;
                singleWidth = (int) (oneWidth * maxHeight / oneHeight);
            }
        }
    }

    private int[] findPosition(int childNum) {
        int[] position = new int[NUMBER_2];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if ((i * columns + j) == childNum) {
                    //行
                    position[0] = i;
                    //列
                    position[1] = j;
                    break;
                }
            }
        }
        return position;
    }

    public int getGap() {
        return gap;
    }

    public void setGap(int gap) {
        this.gap = gap;
    }

    public void setTotalWidth(int width) {
        totalWidth = width;
        //间距5dp
        gap = dp2px(5);
    }

    public void setImagesData(List<String> lists, boolean b, int width, int height) {
        if (lists == null || lists.isEmpty()) {
            removeAllViews();
            return;
        }
        isSpace = b;
        oneWidth = width;
        oneHeight = height;
        removeAllViews();
        //初始化布局
        generateChildrenLayout(lists.size());
        int i = 0;
        while (i < lists.size()) {
            ImageView iv = generateImageView(i);
            if (i < 9) {
                iv.setOnClickListener(new MyClickListener(i));
                addView(iv, generateDefaultLayoutParams());
            }

            i++;
        }

        if (lists.size() > 9) {
            v = inflate(getContext(), R.layout.nine_imgage, null);
            TextView tv = (TextView) v.findViewById(R.id.tv_img_number);
            tv.setText(lists.size() + "");
            addView(v);
        }

        listData = lists;
        invalidate();
    }

    /**
     * 根据图片个数确定行列数量
     * 对应关系如下
     * num	row	column
     * 1	   1	1
     * 2	   1	2
     * 3	   1	3
     * 4	   2	2
     * 5	   2	3
     * 6	   2	3
     * 7	   3	3
     * 8	   3	3
     * 9	   3	3
     *
     * @param length
     */
    private void generateChildrenLayout(int length) {
        if (length <= NUMBER_3) {
            rows = 1;
            columns = length;
        } else if (length <= NUMBER_6) {
            rows = NUMBER_2;
            columns = NUMBER_3;
            //四張時候對應的烈屬
            if (isSpace) {
                if (length == NUMBER_4) {
                    columns = NUMBER_2;
                }
            }

        } else {
            rows = NUMBER_3;
            columns = NUMBER_3;
        }
    }

    private ImageView generateImageView(final int position) {
        RectImageView iv = new RectImageView(getContext());
        //4dp
        iv.setBorderRadius(0);
        iv.setScaleType(ImageView.ScaleType.FIT_XY);
      /*  ImageView iv = new ImageView(getContext());
        iv.setScaleType(ImageView.ScaleType.FIT_XY);*/
        return iv;
    }

    private onNineGirdItemClickListener listener;

    public interface onNineGirdItemClickListener {
        /**
         * 条目点击事件
         *
         * @param position
         */
        public void onItemClick(int position);
    }

    public void setonNineGirdItemClickListener(onNineGirdItemClickListener listener) {
        this.listener = listener;
    }

    private OnNineGirdItemLongClickListener longClickListener;

    public interface OnNineGirdItemLongClickListener {
        /**
         * 条目长按事件
         */
        boolean onItemLongClick();
    }

    public void setOnNineGirdItemLongClickListener(OnNineGirdItemLongClickListener listener) {
        this.longClickListener = listener;
    }

    private class MyClickListener implements OnClickListener {
        public int position = 0;

        MyClickListener(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            if (listener != null) {
                listener.onItemClick(position);
            }
        }
    }

    public  int dp2px(final float dpValue) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
    
}
