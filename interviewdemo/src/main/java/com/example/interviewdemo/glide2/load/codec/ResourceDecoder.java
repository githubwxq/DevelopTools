package com.example.interviewdemo.glide2.load.codec;

import android.graphics.Bitmap;

import java.io.IOException;

/**
 * @author Lance
 * @date 2018/4/21
 * <p>
 * Bitmap解码器
 */
public interface ResourceDecoder<T> {
    boolean handles(T source) throws IOException;
    Bitmap decode(T source, int width, int height)throws IOException;
}
