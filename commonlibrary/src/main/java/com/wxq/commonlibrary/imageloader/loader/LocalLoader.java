package com.wxq.commonlibrary.imageloader.loader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

import com.wxq.commonlibrary.imageloader.request.BitmapRequest;
import com.wxq.commonlibrary.imageloader.utils.BitmapDecoder;
import com.wxq.commonlibrary.imageloader.utils.ImageViewHelper;

import java.io.File;
import java.net.URI;

/**
 * author:wxq
 * email:805380422@qq.com
 * time:2018/11/07
 * desc:
 * version:1.0
 */
public class LocalLoader extends AbstractLoader{


    @Override
    protected Bitmap onLoad(BitmapRequest request) {
        //得到本地图片的路径
        String path= Uri.parse(request.getImageUrl()).getPath();
        File file=new File(path);

        if (!file.exists()) {
            return null;
        }

        BitmapDecoder decoder=new BitmapDecoder() {
            @Override
            public Bitmap decodeBitmapWithOption(BitmapFactory.Options options) {
                return BitmapFactory.decodeFile(path,options);
            }
        };
        //解析本地图片 根据宽高进行压缩
        return decoder.decodeBitmap(ImageViewHelper.getImageViewWidth(request.getImageView()),
                ImageViewHelper.getImageViewHeight(request.getImageView()));
    }


}
