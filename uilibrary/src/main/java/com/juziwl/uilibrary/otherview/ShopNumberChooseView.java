package com.juziwl.uilibrary.otherview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.juziwl.uilibrary.R;
import com.wxq.commonlibrary.util.ToastUtils;

public class ShopNumberChooseView extends LinearLayout {
    ImageView iv_jian;
    ImageView iv_add;
    TextView tv_result;

    SizeChangeListener sizeChangeListener;

    public int getCurrentCount() {
        return currentCount;
    }

    int currentCount=1;
    int totalCount=-1; //-1 情况不限制数量最多

    public void init(int currentCount,int totalCount,SizeChangeListener listener) {
        this.currentCount = currentCount;
        this.sizeChangeListener = listener;
        this.totalCount = totalCount;
    }

   public void init(int currentCount,SizeChangeListener listener) {
        this.currentCount = currentCount;
        this.sizeChangeListener = sizeChangeListener;
    }

    public interface SizeChangeListener{
        public void sizeChange(int currentCount);
    }

    public ShopNumberChooseView(Context context) {
        super(context);
        initView(context);
    }
    public ShopNumberChooseView(Context context,  AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }
    public ShopNumberChooseView(Context context,  AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }
    private void initView(Context context) {
        //加载布局
        View view = View.inflate(context, R.layout.shop_choose_count_layout, this);
        iv_add=view.findViewById(R.id.iv_add);
        iv_jian=view.findViewById(R.id.iv_jian);
        tv_result=view.findViewById(R.id.tv_result);
        tv_result.setText(currentCount+"");
        iv_add.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (totalCount==-1) {
                    // 没有最大限制
                    currentCount++;
                    sizeChangeListener.sizeChange(currentCount);
                }else {
                    if (currentCount>=totalCount) {
                        ToastUtils.showShort("已经最多了");
                    }else {
                        currentCount++;
                        sizeChangeListener.sizeChange(currentCount);
                    }

                }

                tv_result.setText(currentCount+"");

            }
        });
        iv_jian.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentCount<=1) {
                    ToastUtils.showShort("不能减少了");
                }else {
                    currentCount--;
                    sizeChangeListener.sizeChange(currentCount);
                }
                tv_result.setText(currentCount+"");
            }

        });



    }




}
