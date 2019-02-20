package com.juziwl.uilibrary.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.juziwl.uilibrary.R;
import com.wxq.commonlibrary.base.BaseActivity;
import com.wxq.commonlibrary.base.BasePresenter;
import com.xiao.nicevideoplayer.NiceVideoPlayer;
import com.xiao.nicevideoplayer.NiceVideoPlayerManager;
import com.xiao.nicevideoplayer.TxVideoPlayerController;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WatchVideoActivity extends BaseActivity {

    NiceVideoPlayer videoPlayer;



    public static void navToActivity(Activity activity) {
        Intent intent = new Intent(activity, WatchVideoActivity.class);
        activity.startActivity(intent);
    }


    @Override
    protected void initViews() {
        videoPlayer=findViewById(R.id.video_player);
        videoPlayer.setPlayerType(NiceVideoPlayer.TYPE_IJK);
        String videoUrl = "http://bmob-cdn-23627.b0.upaiyun.com/2019/02/17/75d757d89b8c4a63a8262210bd11d044.mp4";

        videoPlayer.setUp(videoUrl, null);
        TxVideoPlayerController controller = new TxVideoPlayerController(this);
        controller.setTitle("办公室小野开番外了，居然在办公室开澡堂！老板还点赞？");
        controller.setLenght(98000);
//        controller.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showToast("111111");
//            }
//        });
        videoPlayer.setController(controller);
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_watch_video;
    }

    @Override
    protected BasePresenter initPresent() {
        return null;
    }



    @Override
    public boolean isNeedHeardLayout() {
        return false;
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




}
