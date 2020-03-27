package com.juziwl.uilibrary.media;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import com.juziwl.uilibrary.R;
import com.luck.picture.lib.decoration.GridSpacingItemDecoration;
import com.wxq.commonlibrary.base.BaseActivity;
import com.wxq.commonlibrary.base.BasePresenter;
import com.wxq.commonlibrary.util.ConvertUtils;
import com.wxq.commonlibrary.util.FileUtils;
import com.wxq.commonlibrary.util.ImageUtils;
import com.wxq.commonlibrary.util.UIHandler;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 选择相册视频activity
 */
public class ChooseMediaActivity extends BaseActivity {

    RecyclerView recyclerView;
    ChooseMediaAdapter mediaAdapter;

    List<LocalMediaEntity> localMediaEntityList = new ArrayList<>();

    TextView tvAddPic;

    TextView tvRemovePic;

    @Override
    protected void initViews() {
        recyclerView = findViewById(R.id.recyclerView);
        tvAddPic = findViewById(R.id.tv_add_pic);
        tvAddPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addPic();
            }
        });
        tvRemovePic = findViewById(R.id.tv_remove_pic);
        tvRemovePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removePic();
            }
        });
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(4,
                ConvertUtils.dp2px(2), false));
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 4));
        // 解决调用 notifyItemChanged 闪烁问题,取消默认动画
        ((SimpleItemAnimator) recyclerView.getItemAnimator())
                .setSupportsChangeAnimations(false);
        mediaAdapter = new ChooseMediaAdapter(localMediaEntityList);
        recyclerView.setAdapter(mediaAdapter);
        getLocalData();
    }

    private void removePic() {
        String filePath2 = Environment.getExternalStorageDirectory().getAbsolutePath() + "/copypic3.png";
        ImageUtils.deleteAlbumImage(filePath2);
        getLocalData();
    }

    private void addPic() {
        zoomImg();
    }

    private void getLocalData() {
        LocalMediaDataLoader localMediaDataLoader = new LocalMediaDataLoader(this);
        localMediaDataLoader.setCompleteListener(new LocalMediaDataLoader.LocalMediaLoadListener() {
            @Override
            public void loadComplete(List<LocalMediaEntity> folders) {
                localMediaEntityList.clear();
                localMediaEntityList.addAll(folders);
                mediaAdapter.notifyDataSetChanged();
            }

            @Override
            public void loadMediaDataError() {

            }
        });
        localMediaDataLoader.loadAllImageData();
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_choose_media;
    }

    @Override
    protected BasePresenter initPresent() {
        return null;
    }



    private void zoomImg() {
        String filePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/pic3.jpg";
        String filePath2 = Environment.getExternalStorageDirectory().getAbsolutePath() + "/copypic3.png";
        Bitmap resbitmap = BitmapFactory.decodeFile(filePath);
        Matrix matrix=new Matrix();
//        matrix.postScale(2, 2);
        matrix.postScale(2, 2);
        Bitmap change = Bitmap.createBitmap(resbitmap, 0, 0, resbitmap.getWidth() , resbitmap.getHeight(),matrix,true);
        ImageUtils.saveImageToGallery(change,filePath2);
        UIHandler.getInstance().postDelayed(new Runnable() {
            @Override
            public void run() {
                getLocalData();
            }
        },3000);
    }

}
