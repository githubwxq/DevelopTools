package com.wxq.commonlibrary.weiget;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.customview.widget.ViewDragHelper;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.hulab.debugkit.DebugFunction;
import com.hulab.debugkit.DevTool;
import com.hulab.debugkit.DevToolFragment;
import com.wxq.commonlibrary.R;
import com.wxq.commonlibrary.util.DensityUtil;
import com.wxq.commonlibrary.util.ScreenUtils;
import com.wxq.commonlibrary.util.StringUtils;

/**
 * @author ArcherYc
 * @date on 2018/9/27  上午9:16
 * @mail 247067345@qq.com
 */
public class DebugView extends FrameLayout {

    private ViewDragHelper mViewDragHelper;

    private Context mContext;

    private AppCompatActivity mActivity;

    private ImageView imvDebug;

    private DevTool.Builder builder;


    public DebugView(AppCompatActivity activity) {
        this(activity, null);
        this.mContext = activity;
        this.mActivity = activity;
        builder = new DevTool.Builder(activity);
        setBackgroundColor(Color.TRANSPARENT);
        init();
    }

    private DebugView(Context context) {
        this(context, null);
    }

    private DebugView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    private DebugView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void init() {
        initDebugBuilder();

        initFloatButton();
    }

    private void initDebugBuilder() {
        int screenWidth = ScreenUtils.getScreenWidth();
        int screenHeight = ScreenUtils.getScreenHeight();
        int consoleWidth = screenWidth / 3 * 2;
        int consoleHeight = screenHeight / 2;
        builder.getTool().setConsoleWidth(DensityUtil.px2dip(mContext, consoleWidth));
        builder.getTool().setConsoleHeight(DensityUtil.px2dip(mContext, consoleHeight));
//        builder.addFunction(new DebugFunction("跳转调试页面") {
//            @Override
//            public String call() throws Exception {
//                mContext.startActivity(new Intent(mContext, ChooseCityActivity.class));
//                return null;
//            }
//        });
//        builder.addFunction(new DebugFunction("测试js") {
//            @Override
//            public String call() throws Exception {
//
//                return null;
//            }
//        });
        builder.addFunction(new DebugFunction("activity") {
            @Override
            public String call() throws Exception {
                return "\n" + mActivity.getClass().getSimpleName();
            }
        }).addFunction(new DebugFunction("fragment") {
            @Override
            public String call() throws Exception {
                FragmentManager fragmentManager = mActivity.getSupportFragmentManager();
                StringBuilder sb = new StringBuilder("\n");
                addFragmentStr(sb, fragmentManager);
                if (StringUtils.isEmpty(sb.toString())) {
                    return "no fragment";
                } else {
                    return sb.toString();
                }
            }
        });
        builder.setTheme(DevToolFragment.DevToolTheme.DARK)
                .displayAt(screenWidth / 2 - consoleWidth / 2, screenHeight / 2 - consoleHeight / 2);
    }

    private void addFragmentStr(StringBuilder sb, FragmentManager activityFragmentManager) {
        for (Fragment fragment : activityFragmentManager.getFragments()) {
            View view = fragment.getView();
            Rect rect = new Rect();
            boolean visible = false;
            if (view != null) {
                visible = view.getGlobalVisibleRect(rect);
            }
            if (visible && fragment.isVisible() && fragment.getChildFragmentManager().getFragments().size() == 0) {
                sb.append(fragment.getClass().getSimpleName() + "--->visible" + "\n");
            } else {
                sb.append(fragment.getClass().getSimpleName() + "\n");
            }
            if (fragment.isVisible() && fragment.getChildFragmentManager().getFragments().size() > 0) {
                addFragmentStr(sb, fragment.getChildFragmentManager());
            }
        }
    }

    private void initFloatButton() {
        imvDebug = new ImageView(mContext);
        imvDebug.setImageResource(R.mipmap.icon_debug);
        LayoutParams lp = new LayoutParams(DensityUtil.dip2px(mContext, 45),
                DensityUtil.dip2px(mContext, 45));
        lp.gravity = Gravity.END | Gravity.CENTER_VERTICAL;
        imvDebug.setLayoutParams(lp);
        imvDebug.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!builder.getTool().isAdded()) {
                    builder.build();
                }
            }
        });

        addView(imvDebug);
        mViewDragHelper = ViewDragHelper.create(this, new ViewDragHelper.Callback() {
            @Override
            public boolean tryCaptureView(@NonNull View view, int i) {
                if (view.equals(imvDebug)) {
                    return true;
                } else {
                    return false;
                }
            }

            @Override
            public int clampViewPositionHorizontal(@NonNull View child, int left, int dx) {
                return left;
            }

            @Override
            public int clampViewPositionVertical(@NonNull View child, int top, int dy) {
                return top;
            }

            @Override
            public int getViewVerticalDragRange(@NonNull View child) {
                return getContext().getResources().getDisplayMetrics().heightPixels;
            }

            @Override
            public int getViewHorizontalDragRange(@NonNull View child) {
                return getContext().getResources().getDisplayMetrics().widthPixels;
            }
        });
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (mViewDragHelper != null) {
            return mViewDragHelper.shouldInterceptTouchEvent(ev);
        }
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mViewDragHelper != null) {
            mViewDragHelper.processTouchEvent(event);
        }
        boolean inX = event.getX() > imvDebug.getX() && event.getX() < imvDebug.getX() + imvDebug.getWidth();
        boolean inY = event.getY() > imvDebug.getY() && event.getY() < imvDebug.getY() + imvDebug.getHeight();
        return inX && inY;
    }

    public void dismissDebugView() {
        if (builder != null) {
            if (builder.getTool().isAdded()) {
                try {
                    mActivity.getFragmentManager()
                            .beginTransaction()
                            .remove(builder.getTool())
                            .commit();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public DevTool.Builder getDebugTool() {
        return builder;
    }

}
