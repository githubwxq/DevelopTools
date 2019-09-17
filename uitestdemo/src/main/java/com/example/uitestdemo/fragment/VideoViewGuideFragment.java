package com.example.uitestdemo.fragment;

import android.net.Uri;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.uitestdemo.R;
import com.juziwl.uilibrary.videoview.PreviewVideoView;
import com.juziwl.uilibrary.viewpage.adapter.BaseViewPagerAdapter;
import com.juziwl.uilibrary.viewpage.pageindicator.PreviewIndicator;
import com.wxq.commonlibrary.base.BaseFragment;
import com.wxq.commonlibrary.base.BasePresenter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

public class VideoViewGuideFragment extends BaseFragment {

    @BindView(R.id.vv_preview)
    PreviewVideoView mVideoView;
    @BindView(R.id.vp_image)
    ViewPager vpImage;
    @BindView(R.id.indicator)
    PreviewIndicator mIndicator;


    private int mCurrentPage = 0;
    Disposable mLoop;
    public List<String> mData=new ArrayList<>();

    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_video_view_guide;
    }

    @Override
    protected void initViews() {
        mData.add("我是第一页");
        mData.add("我是第二页");
        mData.add("我是第三页");
        mVideoView.setVideoURI(Uri.parse(getVideoPath()));
        
        vpImage.setAdapter(new BaseViewPagerAdapter<String>(mData) {
            @Override
            public View newView(int position) {
                View view = LayoutInflater.from(getActivity()).inflate(R.layout.preview_item, null, false);
                TextView introduce = view.findViewById(R.id.iv_intro_text);
                introduce.setText(mData.get(position));
                return view;
            }
        });

        vpImage.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mCurrentPage = position;
                mIndicator.setSelected(mCurrentPage);
//                startLoop();
                mVideoView.seekTo(mCurrentPage * 6 * 1000);
                if (!mVideoView.isPlaying()) {
                    mVideoView.start();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        startLoop();
        vpImage.setCurrentItem(mCurrentPage);
    }

    private String getVideoPath() {
        return "android.resource://" + getActivity().getPackageName() + "/" + R.raw.intro_video;
    }


    private void startLoop() {
        if (null != mLoop) {
            mLoop.dispose();
        }
        Observable.interval(5000, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe( Disposable disposable) {
                        mLoop=disposable;
                    }

                    @Override
                    public void onNext( Long number) {
                        mCurrentPage = (mCurrentPage % (3) + 1)%3;

                        vpImage.setCurrentItem(mCurrentPage);
                    }

                    @Override
                    public void onError( Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });


//         mLoop = Observable.interval(2000, 6000, TimeUnit.MILLISECONDS).subscribe(new Consumer<Long>() {
//            @Override
//            public void accept(Long aLong) throws Exception {
//                mCurrentPage = mCurrentPage % (3 + 1) + 1;
//                mVideoView.seekTo(mCurrentPage * 6 * 1000);
//
//                if (!mVideoView.isPlaying()) {
//                    mVideoView.start();
//                }
//                vpImage.setCurrentItem(mCurrentPage);
//            }
//        });
    }

    @Override
    protected BasePresenter initPresenter() {
        return null;
    }

    @Override
    public void onDestroy() {
        if (null != mLoop) {
            mLoop.dispose();
        }
        super.onDestroy();
    }
}
