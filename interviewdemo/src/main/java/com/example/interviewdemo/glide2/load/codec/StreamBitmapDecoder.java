package com.example.interviewdemo.glide2.load.codec;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.interviewdemo.glide2.cache.ArrayPool;
import com.example.interviewdemo.glide2.cache.recycle.BitmapPool;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author Lance
 * @date 2018/4/21
 */

public class StreamBitmapDecoder implements ResourceDecoder<InputStream> {

    private final BitmapPool bitmaPool;
    private final ArrayPool arrayPool;

    public StreamBitmapDecoder(BitmapPool bitmapPool, ArrayPool arrayPool) {
        this.bitmaPool = bitmapPool;
        this.arrayPool = arrayPool;
    }

    @Override
    public boolean handles(InputStream source) throws IOException {
        return true;
    }

    /**
     * 解码
     * @param source
     * @param width  希望图片的宽 不大于width
     * @param height 希望图片的高 不大于height
     * @return
     * @throws IOException
     */
    @Override
    public Bitmap decode(InputStream source, int width, int height) throws IOException {
//        Bitmap bitmap1 = BitmapFactory.decodeStream(source);
        //TODO MarkInputStream是什么
        MarkInputStream is;
        if (source instanceof MarkInputStream) {
            is = (MarkInputStream) source;
        } else {
            is = new MarkInputStream(source, arrayPool);
        }
        is.mark(0);
        //解码配置
        BitmapFactory.Options options = new BitmapFactory.Options();
        //只加载大小 skia引擎 只会去读取图片的outxx outWidth、outHeight等
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(is, null, options);
        options.inJustDecodeBounds = false;
        //原宽高 100x100
        int sourceWidth = options.outWidth;
        int sourceHeight = options.outHeight;
        //目标宽高  50x50
        int targetWidth = width < 0 ? sourceWidth : width;
        int targetHeight = height < 0 ? sourceHeight : height;
        //获得缩放因子 缩放最大比例  0.5f
        float widthFactor = targetWidth / (float) sourceWidth;
        float heightFactor = targetHeight / (float) sourceHeight;
        //获得最大的缩放因此
        float factor = Math.max(widthFactor, heightFactor);
        //获得目标宽、高  50x50
        int outWidth = Math.round(factor * sourceWidth);
        int outHeight = Math.round(factor * sourceHeight);

        //分别获得宽、高需要缩放多大
        // 宁愿小一点 不超过需要的宽、高
        int widthScaleFactor = sourceWidth % outWidth == 0 ? sourceWidth / outWidth : sourceWidth
                / outWidth + 1;
        int heightScaleFactor = sourceHeight % outHeight == 0 ? sourceHeight / outHeight :
                sourceHeight / outHeight + 1;
        // 2
        int sampleSize = Math.max(widthScaleFactor, heightScaleFactor);
        sampleSize = Math.max(1, sampleSize);

        // 缩放因子
        // 原大小100/缩放因子2 = 50
        options.inSampleSize = sampleSize;

        //Glide的实现是: 读取图片字节数据 任何格式都有一段数据表示这个格式是什么
        // 比如有一个magic=0 jpg 1=png
        // 1、jpg RGB_565
        // 2、png 所有的png都带alpha？ 按照png格式读取表示是否有透明度的字节 比如：0=没有 1=有
        // 不同的解码器
        // 3、webp
        // 4、gif
        // ....
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        //复用
        //不管alpha  使用不同的策略
        // 获得一个在复用池中的Bitmap 复用内存
        Bitmap bitmap = bitmaPool.get(outWidth, outHeight, Bitmap.Config.RGB_565);
        //null : 不复用
        options.inBitmap = bitmap;
        // 允许复用 必须设置inMutable为true
        options.inMutable = true;
        is.reset();
        Bitmap result = BitmapFactory.decodeStream(is, null, options);
        is.release();
        return result;
    }


}
