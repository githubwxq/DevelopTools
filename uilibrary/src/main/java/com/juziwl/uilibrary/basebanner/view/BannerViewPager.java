package com.juziwl.uilibrary.basebanner.view;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;


import android.support.v4.view.ViewPager;


import com.juziwl.uilibrary.R;
import com.juziwl.uilibrary.basebanner.anim.DepthPageTransformer;
import com.juziwl.uilibrary.basebanner.anim.MzTransformer;
import com.juziwl.uilibrary.basebanner.anim.ZoomOutPageTransformer;
import com.juziwl.uilibrary.basebanner.callback.Indicator;
import com.juziwl.uilibrary.basebanner.callback.PageHelperListener;
import com.juziwl.uilibrary.basebanner.type.BannerTransType;
import com.juziwl.uilibrary.basebanner.utils.ViewPagerHelperUtils;

import java.util.List;

public class BannerViewPager extends ViewPager implements View.OnTouchListener, LifecycleObserver {

    /**
     * const
     */
    private static final String TAG = "BannerViewPager";

    private static final int LOOP_MSG = 0x1001;

    /**
     * 循环数量
     */
    private static final int LOOP_COUNT = 5000;

    /**
     * 轮循时间间隔
     */
    private int mLoopTime = 3000;

    /**
     * 是否自动轮播
     */
    private boolean isAutoLoop = true;

    /**
     * 是否填充循环
     */
    private boolean isCycle = false;

    /**
     * 切换速度
     */
    private int mSwitchTime = 500;

    /**
     * 超过这个数字时，才会轮播效果
     */
    private int mLoopMaxCount = 1;

    /**
     * viewpage切换的transformer
     */
    private BannerTransType mBannerTransType;

    /**
     * others
     */
    private int mCurrentIndex;

    private LayoutInflater mInflater;

    private View mCurrentContent;

    /**
     * 处理轮播
     */
    private Handler mHandler;

    /**
     * 内部适配器
     */
    private CusViewPagerAdapter adapter;


    /**
     * 当有触摸时停止
     */
    private boolean isDataConfigFinish = false;


    /**
     * 监听banner 页面滑动
     */
    private BannerPageChangeListener bannerPageChangeListener;

    /**
     * 指示器只关心总数和当前位置
     */
    private Indicator indicator;


    public BannerViewPager(@NonNull Context context) {
        this(context, null);
    }

    public BannerViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        //初始化属性
        initAttribute(context, attrs);
        //初始化handler
        initHandler(context);
        setOnTouchListener(this);
    }

    private void initAttribute(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.BannerViewPager);
        isAutoLoop = ta.getBoolean(R.styleable.BannerViewPager_banner_isAutoLoop, true);
        mLoopTime = ta.getInteger(R.styleable.BannerViewPager_banner_looptime, 3000);
        mSwitchTime = ta.getInteger(R.styleable.BannerViewPager_banner_switchtime, 500);
        mLoopMaxCount = ta.getInteger(R.styleable.BannerViewPager_banner_loop_max_count, 1);

        isCycle = ta.getBoolean(R.styleable.BannerViewPager_banner_iscycle, false);
//        if (mLoopMaxCount != -1) {
//            isCycle = false;
//        }
        int type = ta.getInteger(R.styleable.BannerViewPager_banner_transformer, -1);

        // 切换动画
        if (type != -1) {
            mBannerTransType = BannerTransType.values()[type];
        } else {
            mBannerTransType = BannerTransType.UNKNOWN;
        }
        //设置transformer
        setTransformer(mBannerTransType);
        ta.recycle();
        mInflater = LayoutInflater.from(context);
        ViewPagerHelperUtils.initSwitchTime(getContext(), this, mSwitchTime);
    }


    /**
     * 设置trnsformer
     *
     * @param transformer
     */
    private void setTransformer(BannerTransType transformer) {
        switch (transformer) {
            case CARD:
//                setPageTransformer(true,new CardTransformer(cardHeight));
                break;
            case MZ:
                setPageTransformer(false, new MzTransformer());
                break;
            case ZOOM:
                setPageTransformer(false, new ZoomOutPageTransformer());
                break;
            case DEPATH:
                setPageTransformer(false, new DepthPageTransformer());
                break;
            default:
                break;
        }
    }

    private void initHandler(Context context) {
        mHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                if (msg.what == LOOP_MSG) {
                    if (isAutoLoop) {
                        isDataConfigFinish = true;
                        mCurrentIndex = getCurrentItem(); //重新获取index
                        if (mCurrentIndex >= LOOP_COUNT / 2) {
                            mCurrentIndex++;
                        }
                        if (mCurrentIndex > LOOP_COUNT) {
                            mCurrentIndex = LOOP_COUNT / 2;
                        }
                        setCurrentItem(mCurrentIndex);
                        mHandler.sendEmptyMessageDelayed(LOOP_MSG, mLoopTime);
                    }
                }
            }
        };

    }

    int currentPageIndex = -1;

    /**
     * @param lifecycle 监听生命周期
     * @param data      源数据
     * @param layoutId  布局id
     * @param listener  构建数据与view绑定
     * @param <T>       数据格式
     */
    public <T> BannerViewPager setPageData(Lifecycle lifecycle, final List<T> data, int layoutId, final PageHelperListener<T> listener) {
        lifecycle.addObserver(this);
        if ( data.size() > mLoopMaxCount) {
            isCycle = true;
        }
        adapter = null;
        adapter = new CusViewPagerAdapter<T>(data, layoutId, listener);
        adapter.notifyDataSetChanged();
        setAdapter(adapter);
        int startSelectItem = getStartSelectItem(data.size());
        setCurrentItem(startSelectItem);
        setOffscreenPageLimit(1);
        addOnPageChangeListener(new OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                currentPageIndex = position % data.size();
                Log.d(TAG, "onPageSelected: " + position % data.size());
                if (bannerPageChangeListener != null) {
                    bannerPageChangeListener.onPageSelected(currentPageIndex);
                }
                if (indicator != null) {
                    indicator.setSelect(currentPageIndex);
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        /**
         * 有指示器
         */
        if (indicator != null) {
            indicator.setTotalSize(data.size(), true);
        }

        return this;

    }


    /**
     * 重写这个方法确保外部调用的时候返回正确的位子
     * @param listener
     */
    @Override
    public void addOnPageChangeListener(@NonNull OnPageChangeListener listener) {
        super.addOnPageChangeListener(new RealPageChangeListener(listener));
    }


    class RealPageChangeListener implements OnPageChangeListener {


        public RealPageChangeListener(OnPageChangeListener oldLister) {
            this.oldListener = oldLister;
        }

        OnPageChangeListener oldListener;

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            if (isCycle && adapter != null) {
                oldListener.onPageScrolled(position % adapter.getReadDataSize(), positionOffset, positionOffsetPixels);
            } else {
                oldListener.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }
        }

        @Override
        public void onPageSelected(int position) {
            if (isCycle && adapter != null) {
                oldListener.onPageSelected(position % adapter.getReadDataSize());
            } else {
                oldListener.onPageSelected(position);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            oldListener.onPageScrollStateChanged(state);
        }
    }


    /**
     * 改变数据时记得调用
     */
    public void updateData() {
        if (adapter != null) {
            adapter.notifyDataSetChanged();
            int startSelectItem = getStartSelectItem(adapter.getReadDataSize());
            setCurrentItem(startSelectItem, false);
            if (indicator != null) {
                indicator.setTotalSize(adapter.getReadDataSize(), false);
                if (adapter.getReadDataSize() > 0) {
                    indicator.setSelect(0);
                }
            }
        }
    }

    public void startAnim() {
        if (isAutoLoop) {
            mHandler.removeMessages(LOOP_MSG);
            mHandler.sendEmptyMessageDelayed(LOOP_MSG, mLoopTime);
        }
    }


    /**
     * 获得起始位置
     *
     * @param readCount
     * @return
     */
    private int getStartSelectItem(int readCount) {
        if (readCount == 0) {
            return 0;
        }
        if (isCycle) {
            int count = LOOP_COUNT / 2;
            // 我们设置当前选中的位置为Integer.MAX_VALUE / 2,这样开始就能往左滑动
            // 但是要保证这个值与getRealPosition 的 余数为0，因为要从第一页开始显示
            int currentItem = count;
            if (count % readCount == 0) {
                return currentItem;
            }
            // 直到找到从0开始的位置
            while (currentItem % readCount != 0) {
                currentItem++;
            }
            return currentItem;
        } else {
            return 0;
        }

    }


    /**
     * 配置adapter
     *
     * @param <T>
     */
    class CusViewPagerAdapter<T> extends PagerAdapter {
        PageHelperListener listener;
        List<T> list;
        int layoutid;

        public int getReadDataSize() {
            return list.size();
        }

        public CusViewPagerAdapter(List<T> list, @Nullable int layoutId, PageHelperListener listener) {
            this.listener = listener;
            this.list = list;
            this.layoutid = layoutId;
        }

        //数据会刷新 每一个条目都会刷新
        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }

        @Override
        public int getCount() {
            if (isCycle && list.size() > 0) {
                return this.list.size() + LOOP_COUNT;
            } else {
                return this.list.size();
            }
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            mCurrentContent = mInflater.inflate(layoutid, BannerViewPager.this, false);
            int index = position % list.size();
            if (isCycle) {
                listener.getItemView(mCurrentContent, this.list.get(index), index);
            } else {
                position = position % list.size();
                listener.getItemView(mCurrentContent, this.list.get(position), index);
            }
            container.addView(mCurrentContent);
            return mCurrentContent;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

    }


    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        mHandler.removeMessages(LOOP_MSG);
        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                isDataConfigFinish = false;
                break;
            case MotionEvent.ACTION_UP:
                isDataConfigFinish = true;
                if (isAutoLoop) {
                    mHandler.sendEmptyMessageDelayed(LOOP_MSG, mLoopTime);
                }
                break;

            default:
                break;
        }
        return false;
    }

    /**
     * 手动停止
     */
    public void stopAnim() {
        if (isAutoLoop) {
            mHandler.removeMessages(LOOP_MSG);
        }
    }


    public interface BannerPageChangeListener {
        public void onPageSelected(int position);
    }

    public BannerViewPager setBannerPageChangeListener(BannerPageChangeListener bannerPageChangeListener) {
        this.bannerPageChangeListener = bannerPageChangeListener;
        return this;
    }


    /*indicate指示器
     * @param indicator
     */
    public BannerViewPager setIndicator(Indicator indicator) {
        this.indicator = indicator;
        if (adapter != null) {
            indicator.setTotalSize(adapter.getReadDataSize(), true);
        }
        return this;
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void ON_RESUME() {
        startAnim();
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void ON_STOP() {
        Log.d(TAG, "ON_STOP: ");
        stopAnim();
    }

    /**
     * 如果退出了，自动停止，进来则自动开始
     *
     * @param visibility
     */
    @Override
    protected void onWindowVisibilityChanged(int visibility) {
        super.onWindowVisibilityChanged(visibility);
        if (isAutoLoop) {
            if (visibility == View.VISIBLE) {
                startAnim();
            } else {
                stopAnim();
            }
        }
    }

    @Override
    protected void detachAllViewsFromParent() {
        super.detachAllViewsFromParent();
        mHandler.removeCallbacksAndMessages(null);
    }
}
