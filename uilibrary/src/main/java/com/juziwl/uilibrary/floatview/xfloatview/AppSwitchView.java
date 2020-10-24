package com.juziwl.uilibrary.floatview.xfloatview;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.TextView;

import com.juziwl.uilibrary.R;
import com.wxq.commonlibrary.util.AppUtils;
import com.wxq.commonlibrary.util.StringUtils;

import java.util.List;

/**
 * 应用切换悬浮窗
 *
 * @author xuexiang
 * @since 2019/1/21 上午11:53
 */
public class AppSwitchView extends XFloatView {
    /**
     * 应用名
     */
    private TextView mTvAppName;
    /**
     * 包名
     */
    private TextView mTvPackageName;

    private Handler mMainHandler = new Handler(Looper.getMainLooper());

    private String mPackageName;
    /**
     * 构造器
     *
     * @param context
     */
    public AppSwitchView(Context context) {
        super(context);
    }

    /**
     * @return 获取根布局的ID
     */
    @Override
    protected int getLayoutId() {
        return R.layout.layout_float_view;
    }

    /**
     * @return 能否移动或者触摸响应
     */
    @Override
    protected boolean canMoveOrTouch() {
        return true;
    }

    /**
     * 初始化悬浮控件
     */
    @Override
    protected void initFloatView() {
        mTvAppName = findViewById(R.id.tv_app_name);
        mTvPackageName = findViewById(R.id.tv_package_name);
    }

    /**
     * 初始化监听
     */
    @Override
    protected void initListener() {
        setOnFloatViewClickListener(v -> {
            if (StringUtils.isEmpty(mPackageName)) {
                AppUtils.launchApp(getContext().getPackageName());
            } else {
                if (!isAppForeground( getContext(), mPackageName)) {
                    AppUtils.launchApp(mPackageName);
                }
            }
        });
    }

    /**
     * 判断 App 是否处于前台
     *
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isAppForeground(  Context context, String packageName) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> info = manager.getRunningAppProcesses();
        if (info == null || info.size() == 0) {
            return false;
        }
        for (ActivityManager.RunningAppProcessInfo aInfo : info) {
            if (aInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                return aInfo.processName.equals(packageName);
            }
        }
        return false;
    }


    public void updateAppInfo(final String appName, final String packageName) {
        mPackageName = packageName;
        if (Looper.myLooper() == Looper.getMainLooper()) {
            mTvAppName.setText(String.format("应用：%s", appName));
            mTvPackageName.setText(String.format("包名：%s", packageName));
        } else {
            mMainHandler.post(() -> {
                mTvAppName.setText(String.format("应用：%s", appName));
                mTvPackageName.setText(String.format("包名：%s", packageName));
            });
        }
    }

    /**
     * @return 设置悬浮框是否吸附在屏幕边缘
     */
    @Override
    protected boolean isAdsorbView() {
        return true;
    }

    @Override
    public void clear() {
        super.clear();
        mMainHandler.removeCallbacksAndMessages(null);
    }
}
