//package com.wxq.commonlibrary.util;
//
//import android.annotation.TargetApi;
//import android.app.Activity;
//import android.content.Context;
//import android.content.res.Resources;
//import android.graphics.Color;
//import android.os.Build;
//import android.support.annotation.ColorInt;
//import android.support.annotation.IntRange;
//import android.support.design.widget.CoordinatorLayout;
//import android.support.v4.widget.DrawerLayout;
//import android.util.Log;
//import android.view.View;
//import android.view.ViewGroup;
//import android.view.Window;
//import android.view.WindowManager;
//import android.widget.LinearLayout;
//
//import com.juziwl.commonlibrary.R;
//
//import java.lang.reflect.Field;
//import java.lang.reflect.Method;
//import java.util.regex.Pattern;
//
//import static com.juziwl.commonlibrary.utils.DisplayUtils.getStatusBarHeight;
//
//
///**
// * Created by Jaeger on 16/2/14.
// * <p>
// * Email: chjie.jaeger@gmail.com
// * GitHub: https://github.com/laobie
// */
//public class StatusBarUtil {
//
//    public static final int DEFAULT_STATUS_BAR_ALPHA = 112;
//    private static final int FAKE_STATUS_BAR_VIEW_ID = R.id.common_statusbarutil_fake_status_bar_view;
//    private static final int FAKE_TRANSLUCENT_VIEW_ID = R.id.common_statusbarutil_translucent_view;
//    private static final int TAG_KEY_HAVE_SET_OFFSET = -123;
//
//    /**
//     * 设置状态栏颜色
//     *
//     * @param activity 需要设置的 activity
//     * @param color    状态栏颜色值
//     */
//    public static void setColor(Activity activity, @ColorInt int color) {
//        setColor(activity, color, DEFAULT_STATUS_BAR_ALPHA);
//    }
//
//    /**
//     * 设置状态栏颜色
//     *
//     * @param activity       需要设置的activity
//     * @param color          状态栏颜色值
//     * @param statusBarAlpha 状态栏透明度
//     */
//    public static void setColor(Activity activity, @ColorInt int color, @IntRange(from = 0, to = 255) int statusBarAlpha) {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            activity.getWindow().setStatusBarColor(calculateStatusColor(color, statusBarAlpha));
//        }
////        else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
////            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
////            ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
////            View fakeStatusBarView = decorView.findViewById(FAKE_STATUS_BAR_VIEW_ID);
////            if (fakeStatusBarView != null) {
////                if (fakeStatusBarView.getVisibility() == View.GONE) {
////                    fakeStatusBarView.setVisibility(View.VISIBLE);
////                }
////                fakeStatusBarView.setBackgroundColor(calculateStatusColor(color, statusBarAlpha));
////            } else {
////                decorView.addView(createStatusBarView(activity, color, statusBarAlpha));
////            }
////            setRootView(activity);
////        }
//    }
//
//    public static void clearColor(Activity activity) {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//        }
//    }
//
//    /**
//     * 为滑动返回界面设置状态栏颜色
//     *
//     * @param activity 需要设置的activity
//     * @param color    状态栏颜色值
//     */
//    public static void setColorForSwipeBack(Activity activity, int color) {
//        setColorForSwipeBack(activity, color, DEFAULT_STATUS_BAR_ALPHA);
//    }
//
//    /**
//     * 为滑动返回界面设置状态栏颜色
//     *
//     * @param activity       需要设置的activity
//     * @param color          状态栏颜色值
//     * @param statusBarAlpha 状态栏透明度
//     */
//    public static void setColorForSwipeBack(Activity activity, @ColorInt int color,
//                                            @IntRange(from = 0, to = 255) int statusBarAlpha) {
//        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
//
//            ViewGroup contentView = ((ViewGroup) activity.findViewById(android.R.id.content));
//            View rootView = contentView.getChildAt(0);
//            int statusBarHeight = getStatusBarHeight();
//            if (rootView != null && rootView instanceof CoordinatorLayout) {
//                final CoordinatorLayout coordinatorLayout = (CoordinatorLayout) rootView;
//                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
//                    coordinatorLayout.setFitsSystemWindows(false);
//                    contentView.setBackgroundColor(calculateStatusColor(color, statusBarAlpha));
//                    boolean isNeedRequestLayout = contentView.getPaddingTop() < statusBarHeight;
//                    if (isNeedRequestLayout) {
//                        contentView.setPadding(0, statusBarHeight, 0, 0);
//                        coordinatorLayout.post(new Runnable() {
//                            @Override
//                            public void run() {
//                                coordinatorLayout.requestLayout();
//                            }
//                        });
//                    }
//                } else {
//                    coordinatorLayout.setStatusBarBackgroundColor(calculateStatusColor(color, statusBarAlpha));
//                }
//            } else {
//                contentView.setPadding(0, statusBarHeight, 0, 0);
//                contentView.setBackgroundColor(calculateStatusColor(color, statusBarAlpha));
//            }
//            setTransparentForWindow(activity);
//        }
//    }
//
//    /**
//     * 设置状态栏纯色 不加半透明效果
//     *
//     * @param activity 需要设置的 activity
//     * @param color    状态栏颜色值
//     */
//    public static void setColorNoTranslucent(Activity activity, @ColorInt int color) {
//        setColor(activity, color, 0);
//    }
//
//    /**
//     * 设置状态栏颜色(5.0以下无半透明效果,不建议使用)
//     *
//     * @param activity 需要设置的 activity
//     * @param color    状态栏颜色值
//     */
//    @Deprecated
//    public static void setColorDiff(Activity activity, @ColorInt int color) {
//        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT) {
//            return;
//        }
//        transparentStatusBar(activity);
//        ViewGroup contentView = (ViewGroup) activity.findViewById(android.R.id.content);
//        // 移除半透明矩形,以免叠加
//        View fakeStatusBarView = contentView.findViewById(FAKE_STATUS_BAR_VIEW_ID);
//        if (fakeStatusBarView != null) {
//            if (fakeStatusBarView.getVisibility() == View.GONE) {
//                fakeStatusBarView.setVisibility(View.VISIBLE);
//            }
//            fakeStatusBarView.setBackgroundColor(color);
//        } else {
//            contentView.addView(createStatusBarView(activity, color));
//        }
//        setRootView(activity);
//    }
//
//    /**
//     * 使状态栏半透明
//     * <p>
//     * 适用于图片作为背景的界面,此时需要图片填充到状态栏
//     *
//     * @param activity 需要设置的activity
//     */
//    public static void setTranslucent(Activity activity) {
//        setTranslucent(activity, DEFAULT_STATUS_BAR_ALPHA);
//    }
//
//    /**
//     * 使状态栏半透明
//     * <p>
//     * 适用于图片作为背景的界面,此时需要图片填充到状态栏
//     *
//     * @param activity       需要设置的activity
//     * @param statusBarAlpha 状态栏透明度
//     */
//    public static void setTranslucent(Activity activity, @IntRange(from = 0, to = 255) int statusBarAlpha) {
//        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT) {
//            return;
//        }
//        setTransparent(activity);
//        addTranslucentView(activity, statusBarAlpha);
//    }
//
//    /**
//     * 针对根布局是 CoordinatorLayout, 使状态栏半透明
//     * <p>
//     * 适用于图片作为背景的界面,此时需要图片填充到状态栏
//     *
//     * @param activity       需要设置的activity
//     * @param statusBarAlpha 状态栏透明度
//     */
//    public static void setTranslucentForCoordinatorLayout(Activity activity, @IntRange(from = 0, to = 255) int statusBarAlpha) {
//        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT) {
//            return;
//        }
//        transparentStatusBar(activity);
//        addTranslucentView(activity, statusBarAlpha);
//    }
//
//    /**
//     * 设置状态栏全透明
//     *
//     * @param activity 需要设置的activity
//     */
//    public static void setTransparent(Activity activity) {
//        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT) {
//            return;
//        }
//        transparentStatusBar(activity);
//        setRootView(activity);
//    }
//
//    /**
//     * 使状态栏透明(5.0以上半透明效果,不建议使用)
//     * <p>
//     * 适用于图片作为背景的界面,此时需要图片填充到状态栏
//     *
//     * @param activity 需要设置的activity
//     */
//    @Deprecated
//    public static void setTranslucentDiff(Activity activity) {
//        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
//            // 设置状态栏透明
//            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            setRootView(activity);
//        }
//    }
//
//    /**
//     * 为DrawerLayout 布局设置状态栏变色
//     *
//     * @param activity     需要设置的activity
//     * @param drawerLayout DrawerLayout
//     * @param color        状态栏颜色值
//     */
//    public static void setColorForDrawerLayout(Activity activity, DrawerLayout drawerLayout, @ColorInt int color) {
//        setColorForDrawerLayout(activity, drawerLayout, color, DEFAULT_STATUS_BAR_ALPHA);
//    }
//
//    /**
//     * 为DrawerLayout 布局设置状态栏颜色,纯色
//     *
//     * @param activity     需要设置的activity
//     * @param drawerLayout DrawerLayout
//     * @param color        状态栏颜色值
//     */
//    public static void setColorNoTranslucentForDrawerLayout(Activity activity, DrawerLayout drawerLayout, @ColorInt int color) {
//        setColorForDrawerLayout(activity, drawerLayout, color, 0);
//    }
//
//    /**
//     * 为DrawerLayout 布局设置状态栏变色
//     *
//     * @param activity       需要设置的activity
//     * @param drawerLayout   DrawerLayout
//     * @param color          状态栏颜色值
//     * @param statusBarAlpha 状态栏透明度
//     */
//    public static void setColorForDrawerLayout(Activity activity, DrawerLayout drawerLayout, @ColorInt int color,
//                                               @IntRange(from = 0, to = 255) int statusBarAlpha) {
//        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT) {
//            return;
//        }
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
//        } else {
//            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//        }
//        // 生成一个状态栏大小的矩形
//        // 添加 statusBarView 到布局中
//        ViewGroup contentLayout = (ViewGroup) drawerLayout.getChildAt(0);
//        View fakeStatusBarView = contentLayout.findViewById(FAKE_STATUS_BAR_VIEW_ID);
//        if (fakeStatusBarView != null) {
//            if (fakeStatusBarView.getVisibility() == View.GONE) {
//                fakeStatusBarView.setVisibility(View.VISIBLE);
//            }
//            fakeStatusBarView.setBackgroundColor(color);
//        } else {
//            contentLayout.addView(createStatusBarView(activity, color), 0);
//        }
//        // 内容布局不是 LinearLayout 时,设置padding top
//        if (!(contentLayout instanceof LinearLayout) && contentLayout.getChildAt(1) != null) {
//            contentLayout.getChildAt(1)
//                    .setPadding(contentLayout.getPaddingLeft(), DisplayUtils.getStatusBarHeight() + contentLayout.getPaddingTop(),
//                            contentLayout.getPaddingRight(), contentLayout.getPaddingBottom());
//        }
//        // 设置属性
//        setDrawerLayoutProperty(drawerLayout, contentLayout);
//        addTranslucentView(activity, statusBarAlpha);
//    }
//
//    /**
//     * 设置 DrawerLayout 属性
//     *
//     * @param drawerLayout              DrawerLayout
//     * @param drawerLayoutContentLayout DrawerLayout 的内容布局
//     */
//    private static void setDrawerLayoutProperty(DrawerLayout drawerLayout, ViewGroup drawerLayoutContentLayout) {
//        ViewGroup drawer = (ViewGroup) drawerLayout.getChildAt(1);
//        drawerLayout.setFitsSystemWindows(false);
//        drawerLayoutContentLayout.setFitsSystemWindows(false);
//        drawerLayoutContentLayout.setClipToPadding(true);
//        drawer.setFitsSystemWindows(false);
//    }
//
//    /**
//     * 为DrawerLayout 布局设置状态栏变色(5.0以下无半透明效果,不建议使用)
//     *
//     * @param activity     需要设置的activity
//     * @param drawerLayout DrawerLayout
//     * @param color        状态栏颜色值
//     */
//    @Deprecated
//    public static void setColorForDrawerLayoutDiff(Activity activity, DrawerLayout drawerLayout, @ColorInt int color) {
//        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
//            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            // 生成一个状态栏大小的矩形
//            ViewGroup contentLayout = (ViewGroup) drawerLayout.getChildAt(0);
//            View fakeStatusBarView = contentLayout.findViewById(FAKE_STATUS_BAR_VIEW_ID);
//            if (fakeStatusBarView != null) {
//                if (fakeStatusBarView.getVisibility() == View.GONE) {
//                    fakeStatusBarView.setVisibility(View.VISIBLE);
//                }
//                fakeStatusBarView.setBackgroundColor(calculateStatusColor(color, DEFAULT_STATUS_BAR_ALPHA));
//            } else {
//                // 添加 statusBarView 到布局中
//                contentLayout.addView(createStatusBarView(activity, color), 0);
//            }
//            // 内容布局不是 LinearLayout 时,设置padding top
//            if (!(contentLayout instanceof LinearLayout) && contentLayout.getChildAt(1) != null) {
//                contentLayout.getChildAt(1).setPadding(0, DisplayUtils.getStatusBarHeight(), 0, 0);
//            }
//            // 设置属性
//            setDrawerLayoutProperty(drawerLayout, contentLayout);
//        }
//    }
//
//    /**
//     * 为 DrawerLayout 布局设置状态栏透明
//     *
//     * @param activity     需要设置的activity
//     * @param drawerLayout DrawerLayout
//     */
//    public static void setTranslucentForDrawerLayout(Activity activity, DrawerLayout drawerLayout) {
//        setTranslucentForDrawerLayout(activity, drawerLayout, DEFAULT_STATUS_BAR_ALPHA);
//    }
//
//    /**
//     * 为 DrawerLayout 布局设置状态栏透明
//     *
//     * @param activity     需要设置的activity
//     * @param drawerLayout DrawerLayout
//     */
//    public static void setTranslucentForDrawerLayout(Activity activity, DrawerLayout drawerLayout,
//                                                     @IntRange(from = 0, to = 255) int statusBarAlpha) {
//        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT) {
//            return;
//        }
//        setTransparentForDrawerLayout(activity, drawerLayout);
//        addTranslucentView(activity, statusBarAlpha);
//    }
//
//    /**
//     * 为 DrawerLayout 布局设置状态栏透明
//     *
//     * @param activity     需要设置的activity
//     * @param drawerLayout DrawerLayout
//     */
//    public static void setTransparentForDrawerLayout(Activity activity, DrawerLayout drawerLayout) {
//        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT) {
//            return;
//        }
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
//        } else {
//            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//        }
//
//        ViewGroup contentLayout = (ViewGroup) drawerLayout.getChildAt(0);
//        // 内容布局不是 LinearLayout 时,设置padding top
//        if (!(contentLayout instanceof LinearLayout) && contentLayout.getChildAt(1) != null) {
//            contentLayout.getChildAt(1).setPadding(0, DisplayUtils.getStatusBarHeight(), 0, 0);
//        }
//
//        // 设置属性
//        setDrawerLayoutProperty(drawerLayout, contentLayout);
//    }
//
//    /**
//     * 为 DrawerLayout 布局设置状态栏透明(5.0以上半透明效果,不建议使用)
//     *
//     * @param activity     需要设置的activity
//     * @param drawerLayout DrawerLayout
//     */
//    @Deprecated
//    public static void setTranslucentForDrawerLayoutDiff(Activity activity, DrawerLayout drawerLayout) {
//        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
//            // 设置状态栏透明
//            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            // 设置内容布局属性
//            ViewGroup contentLayout = (ViewGroup) drawerLayout.getChildAt(0);
//            contentLayout.setFitsSystemWindows(true);
//            contentLayout.setClipToPadding(true);
//            // 设置抽屉布局属性
//            ViewGroup vg = (ViewGroup) drawerLayout.getChildAt(1);
//            vg.setFitsSystemWindows(false);
//            // 设置 DrawerLayout 属性
//            drawerLayout.setFitsSystemWindows(false);
//        }
//    }
//
//    /**
//     * 为头部是 ImageView 的界面设置状态栏全透明
//     *
//     * @param activity       需要设置的activity
//     * @param needOffsetView 需要向下偏移的 View
//     */
//    public static void setTransparentForImageView(Activity activity, View needOffsetView) {
//        setTranslucentForImageView(activity, 0, needOffsetView);
//    }
//
//    /**
//     * 为头部是 ImageView 的界面设置状态栏透明(使用默认透明度)
//     *
//     * @param activity       需要设置的activity
//     * @param needOffsetView 需要向下偏移的 View
//     */
//    public static void setTranslucentForImageView(Activity activity, View needOffsetView) {
//        setTranslucentForImageView(activity, DEFAULT_STATUS_BAR_ALPHA, needOffsetView);
//    }
//
//    /**
//     * 为头部是 ImageView 的界面设置状态栏透明
//     *
//     * @param activity       需要设置的activity
//     * @param statusBarAlpha 状态栏透明度
//     * @param needOffsetView 需要向下偏移的 View
//     */
//    public static void setTranslucentForImageView(Activity activity, @IntRange(from = 0, to = 255) int statusBarAlpha,
//                                                  View needOffsetView) {
//        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT) {
//            return;
//        }
//        setTransparentForWindow(activity);
//        addTranslucentView(activity, statusBarAlpha);
//        if (needOffsetView != null) {
//            Object haveSetOffset = needOffsetView.getTag(TAG_KEY_HAVE_SET_OFFSET);
//            if (haveSetOffset != null && (Boolean) haveSetOffset) {
//                return;
//            }
//            ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) needOffsetView.getLayoutParams();
//            layoutParams.setMargins(layoutParams.leftMargin, layoutParams.topMargin + DisplayUtils.getStatusBarHeight(),
//                    layoutParams.rightMargin, layoutParams.bottomMargin);
//            needOffsetView.setTag(TAG_KEY_HAVE_SET_OFFSET, true);
//        }
//    }
//
//    /**
//     * 为 fragment 头部是 ImageView 的设置状态栏透明
//     *
//     * @param activity       fragment 对应的 activity
//     * @param needOffsetView 需要向下偏移的 View
//     */
//    public static void setTranslucentForImageViewInFragment(Activity activity, View needOffsetView) {
//        setTranslucentForImageViewInFragment(activity, DEFAULT_STATUS_BAR_ALPHA, needOffsetView);
//    }
//
//    /**
//     * 为 fragment 头部是 ImageView 的设置状态栏透明
//     *
//     * @param activity       fragment 对应的 activity
//     * @param needOffsetView 需要向下偏移的 View
//     */
//    public static void setTransparentForImageViewInFragment(Activity activity, View needOffsetView) {
//        setTranslucentForImageViewInFragment(activity, 0, needOffsetView);
//    }
//
//    /**
//     * 为 fragment 头部是 ImageView 的设置状态栏透明
//     *
//     * @param activity       fragment 对应的 activity
//     * @param statusBarAlpha 状态栏透明度
//     * @param needOffsetView 需要向下偏移的 View
//     */
//    public static void setTranslucentForImageViewInFragment(Activity activity, @IntRange(from = 0, to = 255) int statusBarAlpha,
//                                                            View needOffsetView) {
//        setTranslucentForImageView(activity, statusBarAlpha, needOffsetView);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
//            clearPreviousSetting(activity);
//        }
//    }
//
//    /**
//     * 隐藏伪状态栏 View
//     *
//     * @param activity 调用的 Activity
//     */
//    public static void hideFakeStatusBarView(Activity activity) {
//        ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
//        View fakeStatusBarView = decorView.findViewById(FAKE_STATUS_BAR_VIEW_ID);
//        if (fakeStatusBarView != null) {
//            fakeStatusBarView.setVisibility(View.GONE);
//        }
//        View fakeTranslucentView = decorView.findViewById(FAKE_TRANSLUCENT_VIEW_ID);
//        if (fakeTranslucentView != null) {
//            fakeTranslucentView.setVisibility(View.GONE);
//        }
//    }
//
//    ///////////////////////////////////////////////////////////////////////////////////
//
//    @TargetApi(Build.VERSION_CODES.KITKAT)
//    private static void clearPreviousSetting(Activity activity) {
//        ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
//        View fakeStatusBarView = decorView.findViewById(FAKE_STATUS_BAR_VIEW_ID);
//        if (fakeStatusBarView != null) {
//            decorView.removeView(fakeStatusBarView);
//            ViewGroup rootView = (ViewGroup) ((ViewGroup) activity.findViewById(android.R.id.content)).getChildAt(0);
//            rootView.setPadding(0, 0, 0, 0);
//        }
//    }
//
//    /**
//     * 添加半透明矩形条
//     *
//     * @param activity       需要设置的 activity
//     * @param statusBarAlpha 透明值
//     */
//    private static void addTranslucentView(Activity activity, @IntRange(from = 0, to = 255) int statusBarAlpha) {
//        ViewGroup contentView = (ViewGroup) activity.findViewById(android.R.id.content);
//        View fakeTranslucentView = contentView.findViewById(FAKE_TRANSLUCENT_VIEW_ID);
//        if (fakeTranslucentView != null) {
//            if (fakeTranslucentView.getVisibility() == View.GONE) {
//                fakeTranslucentView.setVisibility(View.VISIBLE);
//            }
//            fakeTranslucentView.setBackgroundColor(Color.argb(statusBarAlpha, 0, 0, 0));
//        } else {
//            contentView.addView(createTranslucentStatusBarView(activity, statusBarAlpha));
//        }
//    }
//
//    /**
//     * 生成一个和状态栏大小相同的彩色矩形条
//     *
//     * @param activity 需要设置的 activity
//     * @param color    状态栏颜色值
//     * @return 状态栏矩形条
//     */
//    private static View createStatusBarView(Activity activity, @ColorInt int color) {
//        return createStatusBarView(activity, color, 0);
//    }
//
//    /**
//     * 生成一个和状态栏大小相同的半透明矩形条
//     *
//     * @param activity 需要设置的activity
//     * @param color    状态栏颜色值
//     * @param alpha    透明值
//     * @return 状态栏矩形条
//     */
//    private static View createStatusBarView(Activity activity, @ColorInt int color, int alpha) {
//        // 绘制一个和状态栏一样高的矩形
//        View statusBarView = new View(activity);
//        LinearLayout.LayoutParams params =
//                new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DisplayUtils.getStatusBarHeight(activity));
//        statusBarView.setLayoutParams(params);
//        statusBarView.setBackgroundColor(calculateStatusColor(color, alpha));
//        statusBarView.setId(FAKE_STATUS_BAR_VIEW_ID);
//        return statusBarView;
//    }
//
//    /**
//     * 设置根布局参数
//     */
//    private static void setRootView(Activity activity) {
//        ViewGroup parent = (ViewGroup) activity.findViewById(android.R.id.content);
//        for (int i = 0, count = parent.getChildCount(); i < count; i++) {
//            View childView = parent.getChildAt(i);
//            if (childView instanceof ViewGroup) {
////                childView.setFitsSystemWindows(true);
////                ((ViewGroup) childView).setClipToPadding(true);
//            }
//        }
//    }
//
//    /**
//     * 设置透明
//     */
//    public static void setTransparentForWindow(Activity activity) {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
//            activity.getWindow()
//                    .getDecorView()
//                    .setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
//        }
//    }
//
//    /**
//     * 使状态栏透明
//     */
//    @TargetApi(Build.VERSION_CODES.KITKAT)
//    private static void transparentStatusBar(Activity activity) {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//            activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
//        }
//    }
//
//    /**
//     * 创建半透明矩形 View
//     *
//     * @param alpha 透明值
//     * @return 半透明 View
//     */
//    private static View createTranslucentStatusBarView(Activity activity, int alpha) {
//        // 绘制一个和状态栏一样高的矩形
//        View statusBarView = new View(activity);
//        LinearLayout.LayoutParams params =
//                new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DisplayUtils.getStatusBarHeight());
//        statusBarView.setLayoutParams(params);
//        statusBarView.setBackgroundColor(Color.argb(alpha, 0, 0, 0));
//        statusBarView.setId(FAKE_TRANSLUCENT_VIEW_ID);
//        return statusBarView;
//    }
//
//    /**
//     * 计算状态栏颜色
//     *
//     * @param color color值
//     * @param alpha alpha值
//     * @return 最终的状态栏颜色
//     */
//    private static int calculateStatusColor(@ColorInt int color, int alpha) {
//        if (alpha == 0) {
//            return color;
//        }
//        float a = 1 - alpha / 255f;
//        int red = color >> 16 & 0xff;
//        int green = color >> 8 & 0xff;
//        int blue = color & 0xff;
//        red = (int) (red * a + 0.5);
//        green = (int) (green * a + 0.5);
//        blue = (int) (blue * a + 0.5);
//        return 0xff << 24 | red << 16 | green << 8 | blue;
//    }
//
//    public static boolean setStatusBarDarkMode(Window window, boolean isDark, @ColorInt int... transitionColorIfNotDark) {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            if (isMIUI9Later()) {
//                setStatusBarDarkModeForM(window, isDark, transitionColorIfNotDark);
//            } else if (isMIUI6Later()) {
//                setStatusBarDarkModeForMIUI6(window, isDark);
//            } else {
//                setStatusBarDarkModeForM(window, isDark, transitionColorIfNotDark);
//            }
//        } else if (isFlyme4Later()) {
//            setStatusBarDarkModeForFlyme4(window, isDark);
//        } else if (isMIUI6Later() && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            setStatusBarDarkModeForMIUI6(window, isDark);
//        } else if (Build.DISPLAY.contains("Flyme 6")) {
//            MeiZuStatusbarColorUtils.setStatusBarDarkIcon(window, isDark);
//        } else if (isOPPO3Later() && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            setOPPOLightStatusBarIcon(window, isDark);
//        } else {
//            return false;
//        }
//        return true;
//    }
//
//    public static boolean setStatusBarDarkModeForCoordinatorLayout(Window window, boolean isDark) {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            if (isMIUI9Later()) {
//                setStatusBarDarkModeForMForCoordinatorLayout(window, isDark);
//            } else if (isMIUI6Later()) {
//                setStatusBarDarkModeForMIUI6(window, isDark);
//            } else {
//                setStatusBarDarkModeForMForCoordinatorLayout(window, isDark);
//            }
//        } else if (isFlyme4Later()) {
//            setStatusBarDarkModeForFlyme4(window, isDark);
//        } else if (isMIUI6Later() && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            setStatusBarDarkModeForMIUI6(window, isDark);
//        } else if (Build.DISPLAY.contains("Flyme 6")) {
//            MeiZuStatusbarColorUtils.setStatusBarDarkIcon(window, isDark);
//        } else {
//            return false;
//        }
//        return true;
//    }
//
//    /**
//     * 判断是否Flyme4以上
//     */
//    private static boolean isFlyme4Later() {
//        return Build.FINGERPRINT.contains("Flyme_OS_4")
//                || Build.VERSION.INCREMENTAL.contains("Flyme_OS_4")
//                || Pattern.compile("Flyme OS [4|5]", Pattern.CASE_INSENSITIVE).matcher(Build.DISPLAY).find();
//    }
//
//    private static int getMIUIVersion() {
//        try {
//            Class<?> clz = Class.forName("android.os.SystemProperties");
//            Method mtd = clz.getMethod("get", String.class);
//            String val = (String) mtd.invoke(null, "ro.miui.ui.version.name");
//            val = val.replaceAll("[vV]", "");
//            return Integer.parseInt(val);
//        } catch (Exception e) {
//            return 0;
//        }
//    }
//
//    private static boolean isOPPO3Later() {
//        try {
//            Class<?> clz = Class.forName("android.os.SystemProperties");
//            Method mtd = clz.getMethod("get", String.class);
//            String val = (String) mtd.invoke(null, "ro.build.version.opporom");
//            val = val.replaceAll("[vV]", "");
//            return Double.parseDouble(val) >= 3;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
//
//    private static boolean isMIUI9Later() {
//        return getMIUIVersion() >= 9;
//    }
//
//    /**
//     * 判断是否为MIUI6以上
//     */
//    private static boolean isMIUI6Later() {
//        return getMIUIVersion() >= 6;
//    }
//
//    private static int preSystemUiVisibility = 0;
//    private static boolean isDarkStatus = false;
//
//    /**
//     * android 6.0设置状态栏字体颜色
//     */
//    @TargetApi(Build.VERSION_CODES.M)
//    private static void setStatusBarDarkModeForM(Window window, boolean isDark, @ColorInt int... transitionColorIfNotDark) {
//        if (isDark) {
//            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            window.setStatusBarColor(Color.TRANSPARENT);
//
//            int systemUiVisibility = window.getDecorView().getSystemUiVisibility();
//            systemUiVisibility |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
//            window.getDecorView().setSystemUiVisibility(systemUiVisibility);
//        } else {
////            window.clearFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
////            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            if (transitionColorIfNotDark != null && transitionColorIfNotDark.length > 0) {
//                window.setStatusBarColor(transitionColorIfNotDark[0]);
//            } else {
//                window.setStatusBarColor(Color.BLACK);
//            }
//            window.getDecorView().setSystemUiVisibility(0);
//        }
//    }
//
//    @TargetApi(Build.VERSION_CODES.M)
//    private static void setStatusBarDarkModeForMForCoordinatorLayout(Window window, boolean isDark) {
//        if (isDark) {
//            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            window.setStatusBarColor(Color.TRANSPARENT);
//
//            int systemUiVisibility = window.getDecorView().getSystemUiVisibility();
//            systemUiVisibility |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
//            window.getDecorView().setSystemUiVisibility(systemUiVisibility);
//        } else {
//            window.clearFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            window.setStatusBarColor(Color.BLACK);
//            window.getDecorView().setSystemUiVisibility(0);
//        }
//    }
//
//    /**
//     * 设置Flyme4+的darkMode,darkMode时候字体颜色及icon变黑
//     * http://open-wiki.flyme.cn/index.php?title=Flyme%E7%B3%BB%E7%BB%9FAPI
//     */
//    private static boolean setStatusBarDarkModeForFlyme4(Window window, boolean dark) {
//        boolean result = false;
//        if (window != null) {
//            try {
//                WindowManager.LayoutParams e = window.getAttributes();
//                Field darkFlag = WindowManager.LayoutParams.class.getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
//                Field meizuFlags = WindowManager.LayoutParams.class.getDeclaredField("meizuFlags");
//                darkFlag.setAccessible(true);
//                meizuFlags.setAccessible(true);
//                int bit = darkFlag.getInt(null);
//                int value = meizuFlags.getInt(e);
//                if (dark) {
//                    value |= bit;
//                } else {
//                    value &= ~bit;
//                }
//
//                meizuFlags.setInt(e, value);
//                window.setAttributes(e);
//                result = true;
//            } catch (Exception var8) {
//                Log.e("StatusBar", "setStatusBarDarkIcon: failed");
//            }
//        }
//
//        return result;
//    }
//
//    /**
//     * 设置MIUI6-MIUI8的状态栏是否为darkMode,darkMode时候字体颜色及icon变黑
//     * http://dev.xiaomi.com/doc/p=4769/
//     */
//    private static void setStatusBarDarkModeForMIUI6(Window window, boolean darkmode) {
//        Class<? extends Window> clazz = window.getClass();
//        try {
//            int darkModeFlag = 0;
//            Class<?> layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
//            Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
//            darkModeFlag = field.getInt(layoutParams);
//            Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
//            extraFlagField.invoke(window, darkmode ? darkModeFlag : 0, darkModeFlag);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//
//    /**
//     * 生成一个和状态栏大小相同的矩形条 * * @param activity 需要设置的activity *
//     *
//     * @param color 状态栏颜色值 *
//     * @return 状态栏矩形条
//     */
//    private static View createStatusView(Activity activity, int color) {
//        // 获得状态栏高度
//        int resourceId = activity.getResources().getIdentifier("status_bar_height", "dimen", "android");
//        int statusBarHeight = activity.getResources().getDimensionPixelSize(resourceId);
//
//        // 绘制一个和状态栏一样高的矩形
//        View statusView = new View(activity);
//        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
//                statusBarHeight);
//        statusView.setLayoutParams(params);
//        statusView.setBackgroundColor(color);
//        return statusView;
//    }
//
//    /**
//     * 设置状态栏颜色 * * @param activity 需要设置的activity * @param color 状态栏颜色值
//     */
//    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
//    public static void setColor2(Activity activity, int color) {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            // 设置状态栏透明
//            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            // 生成一个状态栏大小的矩形
//            View statusView = createStatusView(activity, color);
//            // 添加 statusView 到布局中
//            ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
//            decorView.addView(statusView);
//            // 设置根布局的参数
//            ViewGroup rootView = (ViewGroup) ((ViewGroup) activity.findViewById(android.R.id.content)).getChildAt(0);
//            rootView.setFitsSystemWindows(true);
//            rootView.setClipToPadding(true);
//        }
//    }
//
//
//    /**
//     * 隐藏虚拟按键，并且全屏
//     */
//    public static void hideBottomUIMenu(Activity activity) {
//        //隐藏虚拟按键，并且全屏
//        if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) { // lower api
//            View v = activity.getWindow().getDecorView();
//            v.setSystemUiVisibility(View.GONE);
//        } else if (Build.VERSION.SDK_INT >= 19) {
//            //for new api versions.
//            View decorView = activity.getWindow().getDecorView();
//            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
//                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN;
//            decorView.setSystemUiVisibility(uiOptions);
//        }
//    }
//
//    /**
//     * 获取是否存在NavigationBar
//     */
//    public static boolean checkDeviceHasNavigationBar(Context context) {
//        boolean hasNavigationBar = false;
//        Resources rs = context.getResources();
//        int id = rs.getIdentifier("config_showNavigationBar", "bool", "android");
//        if (id > 0) {
//            hasNavigationBar = rs.getBoolean(id);
//        }
//        try {
//            Class systemPropertiesClass = Class.forName("android.os.SystemProperties");
//            Method m = systemPropertiesClass.getMethod("get", String.class);
//            String navBarOverride = (String) m.invoke(systemPropertiesClass, "qemu.hw.mainkeys");
//            if ("1".equals(navBarOverride)) {
//                hasNavigationBar = false;
//            } else if ("0".equals(navBarOverride)) {
//                hasNavigationBar = true;
//            }
//        } catch (Exception e) {
//
//        }
//        return hasNavigationBar;
//
//    }
//
//    public final static int SYSTEM_UI_FLAG_OP_STATUS_BAR_TINT = 0x00000010;
//
//    /**
//     * 对Android5.1版本并且是ColorOS3.0的OPPO机型设置状态栏字体黑色
//     * 接口传入值ture时状态栏图标为黑色，接口转入值为false状态栏图标为白色
//     */
//    private static void setOPPOLightStatusBarIcon(Window window, boolean lightMode) {
//        int vis = window.getDecorView().getSystemUiVisibility();
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                if (lightMode) {
//                    vis |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
//                } else {
//                    vis &= ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
//                }
//            } else {
//                if (lightMode) {
//                    vis |= SYSTEM_UI_FLAG_OP_STATUS_BAR_TINT;
//                } else {
//                    vis &= ~SYSTEM_UI_FLAG_OP_STATUS_BAR_TINT;
//                }
//            }
//        }
//        window.getDecorView().setSystemUiVisibility(vis);
//    }
//
//}
