package com.wxq.developtools;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.juziwl.uilibrary.niceplayer.NiceVideoPlayer;
import com.juziwl.uilibrary.niceplayer.NiceVideoPlayerManager;
import com.juziwl.uilibrary.niceplayer.TxVideoPlayerController;
import com.wxq.commonlibrary.glide.LoadingImgUtil;
import com.wxq.commonlibrary.util.BuglyUtils;
import com.wxq.commonlibrary.util.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.iv_test_pic)
    ImageView ivTestPic;
    @BindView(R.id.player)
    NiceVideoPlayer player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        BuglyUtils.init(this,"bd7d7fa0c2",BuildConfig.DEBUG);
        ButterKnife.bind(this);
        LoadingImgUtil.loadimg("http://img17.3lian.com/201612/16/88dc7fcc74be4e24f1e0bacbd8bef48d.jpg", ivTestPic, false);
        init();

    }

    private void init() {

        player.setPlayerType(NiceVideoPlayer.TYPE_IJK); // IjkPlayer or MediaPlayer
        TxVideoPlayerController controller = new TxVideoPlayerController(this);
        controller.setTitle("Beautiful China...");
           String videoUrl = "http://tanzi27niu.cdsb.mobi/wps/wp-content/uploads/2017/05/2017-05-17_17-33-30.mp4";
        controller.setLenght(117000);
        player.setUp(videoUrl, null);
        LoadingImgUtil.loadimg("http://img17.3lian.com/201612/16/88dc7fcc74be4e24f1e0bacbd8bef48d.jpg",controller.imageView(),false);
        player.setController(controller);


        ivTestPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showShort("点图片");

                int b=10/0;


            }
        });

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
