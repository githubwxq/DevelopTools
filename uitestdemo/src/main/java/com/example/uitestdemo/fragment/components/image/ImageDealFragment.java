package com.example.uitestdemo.fragment.components.image;

import android.Manifest;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.uitestdemo.R;
import com.juziwl.uilibrary.textview.SuperTextView;
import com.wxq.commonlibrary.base.BaseFragment;
import com.wxq.commonlibrary.base.BasePresenter;
import com.wxq.commonlibrary.http.common.LogUtil;
import com.wxq.commonlibrary.util.BitmapUtils;
import com.wxq.commonlibrary.util.DensityUtil;
import com.wxq.commonlibrary.util.ImageUtils;

import java.io.File;

import butterknife.BindView;
import io.reactivex.functions.Consumer;

import static mapsdkvi.com.gdi.bgl.android.java.EnvDrawText.bmp;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ImageDealFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ImageDealFragment extends BaseFragment {


    @BindView(R.id.dp_to_px)
    SuperTextView dp_to_px;

    @BindView(R.id.iv_change_image)
    ImageView iv_change_image;


    @BindView(R.id.iv_original)
    ImageView iv_original;

    @BindView(R.id.tv_image_detail)
    TextView tv_image_detail;


    @BindView(R.id.tv_image_change_detail)
    TextView tv_image_change_detail;


    @BindView(R.id.suoxiao)
    TextView suoxiao;
    @BindView(R.id.tv_rotate)
    TextView tv_rotate;


    @BindView(R.id.changBig)
    TextView changBig;


    @BindView(R.id.tv_clip)
    TextView tv_clip;

    @BindView(R.id.tv_round)
    TextView tv_round;

    @BindView(R.id.tv_add_text)
    TextView tv_add_text;


    @BindView(R.id.ll_layout)
    LinearLayout ll_layout;


    @BindView(R.id.copy_view_to_bitmap)
    TextView copy_view_to_bitmap;






    public static ImageDealFragment newInstance() {
        ImageDealFragment fragment = new ImageDealFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_image_deal;
    }

    @Override
    protected void initViews() {

        rxPermissions.request(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
        ).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Exception {
                String s = Environment.getExternalStorageDirectory().getAbsolutePath() + "/test550.png";
                Bitmap bitmap = BitmapFactory.decodeFile(s);
                iv_original.setImageBitmap(bitmap);
                int size = bitmap.getRowBytes() * bitmap.getHeight();
                File file = new File(s);
                tv_image_detail.setText("width" + bitmap.getWidth() + "height" + bitmap.getHeight() + "文件大小" + file.length() + "bitmap内存（1.15m）" + size + "B");
            }
        });

        suoxiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = Environment.getExternalStorageDirectory().getAbsolutePath() + "/test550.png";
                Bitmap changeBitmap = ImageUtils.getBitmap(new File(s), 275, 275);
                iv_change_image.setImageBitmap(changeBitmap);
                showChangeBitmapInfo(changeBitmap);
            }
        });


        changBig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = Environment.getExternalStorageDirectory().getAbsolutePath() + "/test550.png";
                // 创建操作图片用的matrix对象 进行硕放
                Matrix matrix = new Matrix();
                // 缩放图片动作
                matrix.postScale(2f, 2f);
                // 创建新的图片
                Bitmap resizedBitmap = Bitmap.createBitmap(ImageUtils.getBitmap(s), 0, 0,
                        550, 550, matrix, true);
                iv_change_image.setImageBitmap(resizedBitmap);
                showChangeBitmapInfo(resizedBitmap);
            }
        });


        tv_rotate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = Environment.getExternalStorageDirectory().getAbsolutePath() + "/test550.png";
////加载原图
                Bitmap bitmap = BitmapFactory.decodeFile(s);
////搞一个一样大小一样样式的复制图
//                Bitmap copybm = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), bitmap.getConfig());
////获取复制图的画布
//                Canvas canvas = new Canvas(copybm);
////获取一个画笔,设置颜色
//                Paint paint = new Paint();
//                paint.setColor(Color.RED);
////设置图片绘制角度——设置矩阵
//                Matrix matrix = new Matrix();
//                /**
//                 matrix.setValues(new float[]{//这是矩阵的默认值
//                 1.5f,0,0,
//                 0,1,0,
//                 0,0,1
//                 });
//                 而旋转其实是将每个点坐标和sinx  cosx进行计算...
//                 */
////安卓提供了便捷方法
//                matrix.setRotate(45,bitmap.getWidth()/2,bitmap.getHeight()/2);
////向画布绘制,绘制原图内容
//                canvas.drawBitmap(bitmap, matrix, paint);
////canvas.drawPoint(10, 10, paint); 向指定位置画一个点
//                iv_change_image.setImageBitmap(copybm);
//                showChangeBitmapInfo(copybm);


                //方法2
                Bitmap rotate = ImageUtils.rotate(bitmap, 45, bitmap.getWidth() / 2, bitmap.getHeight() / 2);
                iv_change_image.setImageBitmap(rotate);
                showChangeBitmapInfo(rotate);

            }
        });


        tv_clip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = Environment.getExternalStorageDirectory().getAbsolutePath() + "/test550.png";
                Bitmap bitmap = BitmapFactory.decodeFile(s);
                Bitmap rotate = ImageUtils.clip(bitmap, 0, 0, bitmap.getWidth() / 2, bitmap.getHeight() / 2);
                iv_change_image.setImageBitmap(rotate);
                showChangeBitmapInfo(rotate);
            }
        });


        tv_round.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = Environment.getExternalStorageDirectory().getAbsolutePath() + "/test550.png";
                Bitmap bitmap = BitmapFactory.decodeFile(s);
                Bitmap roundCorner = ImageUtils.toRoundCorner(bitmap, DensityUtil.dip2px(mContext, 100));
                iv_change_image.setImageBitmap(roundCorner);
                showChangeBitmapInfo(roundCorner);
            }
        });

        tv_add_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = Environment.getExternalStorageDirectory().getAbsolutePath() + "/test550.png";
                Bitmap bitmap = BitmapFactory.decodeFile(s);
                Bitmap roundCorner = ImageUtils.addTextWatermark(bitmap,  "水印",25,  Color.RED, 10,10f);
                iv_change_image.setImageBitmap(roundCorner);
                showChangeBitmapInfo(roundCorner);
            }
        });

        copy_view_to_bitmap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ll_layout.setDrawingCacheEnabled(true);
                Bitmap llbitmap = Bitmap.createBitmap(ll_layout.getDrawingCache());
                ll_layout.setDrawingCacheEnabled(false);
                iv_change_image.setImageBitmap(llbitmap);
                showChangeBitmapInfo(llbitmap);
            }
        });





    }

    private void showChangeBitmapInfo(Bitmap bitmap) {
        int size = bitmap.getRowBytes() * bitmap.getHeight();
        //内存缩小了四倍
        tv_image_change_detail.setText("width" + bitmap.getWidth() + "height" + bitmap.getHeight() + "bitmap内存" + size + "B");
    }


}