package com.example.interviewdemo.glide2.cache;

import java.security.MessageDigest;

/**
 * Created by Administrator on 2018/5/4.
 */
public interface Key {

    void updateDiskCacheKey(MessageDigest md);

    byte[] getKeyBytes();
}
