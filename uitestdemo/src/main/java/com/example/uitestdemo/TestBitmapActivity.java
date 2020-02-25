package com.example.uitestdemo;

import android.Manifest;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wxq.commonlibrary.base.BaseActivity;
import com.wxq.commonlibrary.base.BasePresenter;
import com.wxq.commonlibrary.http.common.LogUtil;
import com.wxq.commonlibrary.util.ConvertUtils;

import java.io.File;

import io.reactivex.functions.Consumer;
import uk.co.senab.photoview.PhotoView;

public class TestBitmapActivity extends BaseActivity {

    ImageView oldImage;
    ImageView nowImage;
    ImageView combine_image;
    TextView tv_change;
    LinearLayout ll_test;

    @Override
    protected void initViews() {
        oldImage = findViewById(R.id.oldImage);
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
//                oldImage.setImageURI(Uri.fromFile(new File("/sdcard/text.png")));
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

            }
        });
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
