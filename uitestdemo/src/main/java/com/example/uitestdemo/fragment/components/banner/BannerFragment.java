package com.example.uitestdemo.fragment.components.banner;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.example.uitestdemo.R;
import com.juziwl.uilibrary.basebanner.adapter.BaseViewPagerAdapter;
import com.juziwl.uilibrary.basebanner.adapter.ViewPageHolder;
import com.juziwl.uilibrary.basebanner.anim.MzTransformer;
import com.juziwl.uilibrary.basebanner.callback.PageHelperListener;
import com.juziwl.uilibrary.basebanner.indicator.NormalIndicator;
import com.juziwl.uilibrary.basebanner.view.BannerViewPager;
import com.juziwl.uilibrary.basebanner.view.CyclePaiViewPager;
import com.wxq.commonlibrary.base.BaseFragment;
import com.wxq.commonlibrary.base.BasePresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


public class BannerFragment extends BaseFragment {


    @BindView(R.id.normal_view_pager)
    BannerViewPager normalViewPager;
    @BindView(R.id.normal_indicator)
    NormalIndicator normalIndicator;
    @BindView(R.id.mz_banner_view_pager)
    BannerViewPager mzBannerViewPager;
    @BindView(R.id.mz_indicator)
    NormalIndicator mzIndicator;
    @BindView(R.id.banner_no_cycle_no_auto)
    BannerViewPager bannerNoCycleNoAuto;
    @BindView(R.id.banner_no_auto_loop)
    BannerViewPager bannerNoAutoLoop;
    @BindView(R.id.cycleViewPage)
    CyclePaiViewPager cycleViewPage;

    public BannerFragment() {
        // Required empty public constructor
    }

    public static BannerFragment newInstance() {
        BannerFragment fragment = new BannerFragment();

        return fragment;
    }


    @Override
    protected BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_banner;
    }

    @Override
    protected void initViews() {
        createNormalViewpage();
        createMzViewpage();
        createNoAutoLoopViewpage();
        createNoCycleViewpage();


        createCycleViewPage();

    }

    private void createCycleViewPage() {
        List<String> data = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            data.add("cycleviewpage控件" + i);
        }
        cycleViewPage.setAdapter(new BaseViewPagerAdapter<String>(mContext, data, R.layout.banner_item) {
            @Override
            protected void convert(ViewPageHolder holder, String item) {
                ImageView imageView = holder.getView(R.id.iv_bg);
                TextView title = holder.getView(R.id.tv_title);
                title.setText(item);
                Glide.with(mContext).load("https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=3213207264,1556792350&fm=26&gp=0.jpg").into(imageView);
            }
        });
        cycleViewPage.startLoop();

    }

    private void createNoAutoLoopViewpage() {
        List<String> data = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            data.add("不自动但循环" + i);
        }
        bannerNoAutoLoop.setPageData(getLifecycle(), data, R.layout.banner_item, new PageHelperListener<String>() {
            @Override
            public void getItemView(View mCurrentContent, String item, int position) {
                TextView title = mCurrentContent.findViewById(R.id.tv_title);
                title.setText(item);
                ImageView imageView = mCurrentContent.findViewById(R.id.iv_bg);
                Glide.with(mContext).load("https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=3213207264,1556792350&fm=26&gp=0.jpg").into(imageView);
            }
        }).startAnim();
    }

    private void createNoCycleViewpage() {
        List<String> data = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            data.add("不循环不自动" + i);
        }
        bannerNoCycleNoAuto.setPageTransformer(false, new MzTransformer());
        bannerNoCycleNoAuto.setPageMargin(30);
        bannerNoCycleNoAuto.setPageData(getLifecycle(), data, R.layout.banner_item, new PageHelperListener<String>() {
            @Override
            public void getItemView(View mCurrentContent, String item, int position) {
                TextView title = mCurrentContent.findViewById(R.id.tv_title);
                title.setText(item);
                ImageView imageView = mCurrentContent.findViewById(R.id.iv_bg);
                Glide.with(mContext).load("https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=3213207264,1556792350&fm=26&gp=0.jpg").into(imageView);
            }
        }).startAnim();
    }

    private void createMzViewpage() {
        List<String> data = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            data.add("一页多屏" + i);
        }
        mzBannerViewPager.setPageTransformer(false, new MzTransformer());
        mzBannerViewPager.setPageMargin(30);
        mzBannerViewPager.setPageData(getLifecycle(), data, R.layout.banner_item, new PageHelperListener<String>() {
            @Override
            public void getItemView(View mCurrentContent, String item, int position) {
                TextView title = mCurrentContent.findViewById(R.id.tv_title);
                title.setText(item);
                ImageView imageView = mCurrentContent.findViewById(R.id.iv_bg);
                Glide.with(mContext).load("https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=3213207264,1556792350&fm=26&gp=0.jpg").into(imageView);
            }
        }).setIndicator(mzIndicator)
//                .setBannerPageChangeListener(position -> Toast.makeText(BannerStyleActivity.this, "位子" + position, Toast.LENGTH_SHORT).show())
                .startAnim();

    }


    private void createNormalViewpage() {
        List<String> data = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            data.add("自动循环" + i);
        }
        normalViewPager.setPageData(getLifecycle(), data, R.layout.banner_item, new PageHelperListener<String>() {
            @Override
            public void getItemView(View mCurrentContent, String item, int position) {
                TextView title = mCurrentContent.findViewById(R.id.tv_title);
                title.setText(item);
                ImageView imageView = mCurrentContent.findViewById(R.id.iv_bg);
                Glide.with(mContext).load("https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=3213207264,1556792350&fm=26&gp=0.jpg").into(imageView);
            }
        }).setIndicator(normalIndicator).startAnim();


        normalViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Toast.makeText(mContext, "位子" + position, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

}