package com.example.uitestdemo.activity;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.uitestdemo.R;
import com.juziwl.uilibrary.media.ChooseMediaActivity;
import com.juziwl.uilibrary.media.LocalMediaEntity;
import com.juziwl.uilibrary.media.LocalMediaDataLoader;
import com.wxq.commonlibrary.base.BaseActivity;
import com.wxq.commonlibrary.base.BasePresenter;
import com.wxq.commonlibrary.http.common.LogUtil;
import com.wxq.commonlibrary.util.ConvertUtils;
import com.wxq.commonlibrary.util.ToastUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import io.reactivex.functions.Consumer;

public class TestBitmapActivity extends BaseActivity {

    ImageView oldImage;
    ImageView iv_caijian;
    ImageView nowImage;
    ImageView combine_image;
    TextView tv_change;
    LinearLayout ll_test;

    @Override
    protected void initViews() {
        oldImage = findViewById(R.id.oldImage);
        iv_caijian = findViewById(R.id.iv_caijian);
        nowImage = findViewById(R.id.nowImage);
        combine_image = findViewById(R.id.combine_image);
        tv_change = findViewById(R.id.tv_change);
        ll_test=findViewById(R.id.ll_test);
        int px = ConvertUtils.dp2px(100);
        LogUtil.e("100dp对应的宽度" + px);

        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(getResources(), R.mipmap.text, opts);
        int width = opts.outWidth;
        int height = opts.outHeight;

        LogUtil.e("outWidth" + width + "outHeight" + height);

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.text);
        int bitmapHeight = bitmap.getHeight();
        int bitmapWidth = bitmap.getWidth();
        LogUtil.e("bitmapHeight" + bitmapHeight + "bitmapWidth" + bitmapWidth);
///sdcard/text.png
//        MOUNT_UNMOUNT_FILESYSTEMS

        rxPermissions.request(Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS

                , Manifest.permission.WRITE_SETTINGS,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.CAMERA
        ).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Exception {
                oldImage.setImageURI(Uri.fromFile(new File("/sdcard/550ps.png")));
            }
        });

        //确保已经绘制上了
//        oldImage.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
//        oldImage.layout(0, 0, oldImage.getMeasuredWidth(), oldImage.getMeasuredHeight());
//        oldImage.buildDrawingCache();
//
//

        String filePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/550ps.png";
        LogUtil.e("filePath" + filePath );
        oldImage.setImageURI(Uri.fromFile(new File(filePath)));
        tv_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                oldImage.setDrawingCacheEnabled(true);
//                Bitmap bitt = Bitmap.createBitmap(oldImage.getDrawingCache());
//                Bitmap bitt2 = Bitmap.createBitmap(oldImage.getDrawingCache());
//                Bitmap bitt3 = Bitmap.createBitmap(oldImage.getDrawingCache());
//                oldImage.setDrawingCacheEnabled(false);
//                int bittWidth = bitt.getWidth();
//                LogUtil.e("bittWidth" + bittWidth + "bittWidth" + bittWidth);
//                nowImage.setImageBitmap(bitt);
//
//                Bitmap combineImage = combineImage(bitt,bitt2,bitt3);

//                combine_image.setImageBitmap(combineImage);
                ll_test.setDrawingCacheEnabled(true);
                Bitmap llbitmap = Bitmap.createBitmap(ll_test.getDrawingCache());
                ll_test.setDrawingCacheEnabled(false);
                combine_image.setImageBitmap(llbitmap);

//                caijianBitmap();
//                zoomImg();

                //获取相册数据

//                getAllImage();

                //前往图片列表页面

                Intent intent=new Intent(TestBitmapActivity.this, ChooseMediaActivity.class);
                startActivity(intent);

            }
        });




    }

    private void getAllImage() {
        LocalMediaDataLoader localMediaDataLoader =new LocalMediaDataLoader(this);
        localMediaDataLoader.setCompleteListener(new LocalMediaDataLoader.LocalMediaLoadListener() {
            @Override
            public void loadComplete(List<LocalMediaEntity> folders) {
                ToastUtils.showShort(folders.size()+"changdu ");
                oldImage.setImageURI(Uri.fromFile(new File(folders.get(0).getPath())));
            }

            @Override
            public void loadMediaDataError() {

            }
        });

        localMediaDataLoader.loadAllImageData();
    }


    private void zoomImg() {
        String filePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/550ps.png";
        String filePath2 = Environment.getExternalStorageDirectory().getAbsolutePath() + "/small550ps.png";
        Bitmap resbitmap = BitmapFactory.decodeFile(filePath);
        Matrix matrix=new Matrix();
//        matrix.postScale(2, 2);
        matrix.postScale(0.5f, 0.5f);
        Bitmap change = Bitmap.createBitmap(resbitmap, 0, 0, resbitmap.getWidth() , resbitmap.getHeight(),matrix,true);
        iv_caijian.setImageBitmap(change);
        saveBitmap(change,filePath2);
    }

    public static void saveBitmap(Bitmap bitmap,String path) {
        String savePath;
        File filePic;
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            savePath = path;
        } else {
            return;
        }
        try {
            filePic = new File(savePath);
            if (!filePic.exists()) {
                filePic.getParentFile().mkdirs();
                filePic.createNewFile();
            }
            FileOutputStream fos = new FileOutputStream(filePic);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            return;
        }

    }





    private void caijianBitmap() {
        String filePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/550ps.png";
        Bitmap resbitmap = BitmapFactory.decodeFile(filePath);
        Bitmap change = Bitmap.createBitmap(resbitmap, 0, 0, resbitmap.getWidth()/2 , resbitmap.getHeight()/2);
        iv_caijian.setImageBitmap(change);
    }

    private Bitmap combineImage(Bitmap... bitmaps) {
        int width = 0;
        int height = 0;

        //获取最大宽度
        for (Bitmap bitmap : bitmaps) {
            height = height + bitmap.getHeight();
            if (width < bitmap.getWidth()) {
                width = bitmap.getWidth();
            }
        }

        Bitmap newBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(newBitmap);
        int tempHeight = 0;
        //画图
        for (int i = 0; i < bitmaps.length; i++) {
            canvas.drawBitmap(bitmaps[i], 0, tempHeight, null);
            tempHeight = bitmaps[i].getHeight() + tempHeight;
//            bitmaps[i].recycle();
        }
        return newBitmap;

    }


    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_test_bitmap;
    }

    @Override
    protected BasePresenter initPresent() {
        return null;
    }
}
