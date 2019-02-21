package com.juziwl.uilibrary.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.juziwl.uilibrary.R;
import com.wxq.commonlibrary.base.BaseActivity;
import com.wxq.commonlibrary.base.BasePresenter;
import com.wxq.commonlibrary.glide.LoadingImgUtil;
import com.wxq.commonlibrary.util.BarUtils;
import com.wxq.commonlibrary.util.ScreenUtils;
import com.wxq.commonlibrary.util.ToastUtils;
import com.wxq.commonlibrary.util.UIHandler;
import com.xiao.nicevideoplayer.NiceVideoPlayer;
import com.xiao.nicevideoplayer.NiceVideoPlayerManager;
import com.xiao.nicevideoplayer.TxVideoPlayerController;

import java.util.ArrayList;
import java.util.List;

public class VideoListActivity extends BaseActivity {
    private RecyclerView mRecyclerView;
    private List<String> list=new ArrayList<>();


    public static void navToActivity(Activity activity) {
        Intent intent = new Intent(activity, VideoListActivity.class);
        activity.startActivity(intent);
    }


     int firstItem=0;
     int currentPlayPosition=-1;

    @Override
    protected void initViews() {
        list.add("1");
        list.add("1");
        list.add("1");
        list.add("1");
        list.add("1");
        list.add("1");
        list.add("1");
        list.add("1");
        list.add("1");
        mRecyclerView=findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(new BaseQuickAdapter<String,BaseViewHolder>(R.layout.item_nice_video,list) {
            @Override
            protected void convert(BaseViewHolder helper, String item) {
                NiceVideoPlayer videoPlayer= helper.getView(R.id.video_player);
                videoPlayer.setPlayerType(NiceVideoPlayer.TYPE_IJK);
                String videoUrl = "http://bmob-cdn-23627.b0.upaiyun.com/2019/02/17/75d757d89b8c4a63a8262210bd11d044.mp4";
                // String videoUrl = "http://bmob-cdn-23627.b0.upaiyun.com/2019/02/20/90706b3359c34ac4a887aff6a4c3aef0.mp4";
                videoPlayer.setUp(videoUrl, null);
                TxVideoPlayerController controller = new TxVideoPlayerController(mContext);
                controller.setTitle("办公室小野开番外了，居然在办公室开澡堂！老板还点赞？");
                controller.setLenght(98000);
                controller.setControlClickListener(new TxVideoPlayerController.ControlClickListener() {
                    @Override
                    public void clickCenterPlayIcon() {
                        currentPlayPosition=helper.getAdapterPosition();
                    }
                });
                ImageView image= controller.imageView();
                LoadingImgUtil.loadimg("http://bmob-cdn-23627.b0.upaiyun.com/2019/02/20/8e58dfa28a594ae9acdaca5c8edf8327.jpg",image,false);
                videoPlayer.setController(controller);
            }
        });
        mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
               LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();
                int firstItemPosition = layoutManager.findFirstVisibleItemPosition();
            }
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (currentPlayPosition!=-1) {
                    BaseViewHolder viewHolderForAdapterPosition = (BaseViewHolder) mRecyclerView.findViewHolderForAdapterPosition(currentPlayPosition);
                    if (viewHolderForAdapterPosition==null) {
                            NiceVideoPlayerManager.instance().releaseNiceVideoPlayer();
                    }
                }
            }
        });

        UIHandler.getInstance().postDelayed(new Runnable() {
            @Override
            public void run() {
                BaseViewHolder viewHolderForAdapterPosition = (BaseViewHolder) mRecyclerView.findViewHolderForAdapterPosition(2);
                int[] location=new int[2];
                if (viewHolderForAdapterPosition!=null) {
                    viewHolderForAdapterPosition.getConvertView().getLocationOnScreen(location);
                    Log.e("wxq","location"+location[0]+"+++++++++++++"+location[1]);

//                判断在不在屏幕内
                    Rect ivRect=new Rect(viewHolderForAdapterPosition.getConvertView().getLeft(),viewHolderForAdapterPosition.getConvertView().getTop(),viewHolderForAdapterPosition.getConvertView().getRight(),viewHolderForAdapterPosition.getConvertView().getBottom());
                    Rect  rect=new Rect(0,0, ScreenUtils.getScreenWidth(),ScreenUtils.getScreenHeight() );
                    if (rect.contains(ivRect)) {
                        Log.e("wxq","在屏幕范围内");
                    }else {
                        Log.e("wxq","不在屏幕");
                    }

                }else {
                    Log.e("wxq","viewHolderForAdapterPosition 为null");

                }
                ;
                Log.e("wxq","getActionBarHeight"+BarUtils.getActionBarHeight());
                Log.e("wxq","getStatusBarHeight"+BarUtils.getStatusBarHeight());
            }
        },3000);




//        mRecyclerView.setRecyclerListener(new RecyclerView.RecyclerListener() {
//            @Override
//            public void onViewRecycled(RecyclerView.ViewHolder holder) {
//                NiceVideoPlayer niceVideoPlayer = ((BaseViewHolder) holder).getView(R.id.video_player);
//                if (niceVideoPlayer == NiceVideoPlayerManager.instance().getCurrentNiceVideoPlayer()) {
//                    NiceVideoPlayerManager.instance().releaseNiceVideoPlayer();
//                }
//            }
//        });


    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_video_list;
    }

    @Override
    protected BasePresenter initPresent() {
        return null;
    }



    @Override
    protected void onStop() {
        super.onStop();
        NiceVideoPlayerManager.instance().releaseNiceVideoPlayer();
    }

    @Override
    public void onBackPressed() {
        if (NiceVideoPlayerManager.instance().onBackPressd()) return;
        super.onBackPressed();
    }


    @Override
    public boolean isNeedHeardLayout() {
        return false;
    }
}
