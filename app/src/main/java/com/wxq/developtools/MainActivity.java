package com.wxq.developtools;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.ImageView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.juziwl.uilibrary.niceplayer.NiceVideoPlayer;
import com.juziwl.uilibrary.niceplayer.NiceVideoPlayerManager;
import com.juziwl.uilibrary.niceplayer.TxVideoPlayerController;
import com.wxq.commonlibrary.glide.LoadingImgUtil;
import com.wxq.commonlibrary.util.ToastUtils;
import com.wxq.commonlibrary.base.BaseActivity;
import com.wxq.commonlibrary.base.BasePresenter;
import com.wxq.commonlibrary.router.RouterContent;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

@Route(path = "/main/main")
public class MainActivity extends BaseActivity {

    @BindView(R.id.iv_test_pic)
    ImageView ivTestPic;
    @BindView(R.id.player)
    NiceVideoPlayer player;


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void initViews() {
        LoadingImgUtil.loadimg("http://img17.3lian.com/201612/16/88dc7fcc74be4e24f1e0bacbd8bef48d.jpg", ivTestPic, false);
//        init();
        com.orhanobut.logger.Logger.e("text","text");

    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_main;
    }

    @Override
    protected BasePresenter initPresent() {
        return null;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
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


        List<String> list=new ArrayList<>();

//        list.forEach(s -> {
//
//        });

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
