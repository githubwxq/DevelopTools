package com.example.interviewdemo.glide2.load;

import com.example.interviewdemo.glide2.cache.Key;

import java.security.MessageDigest;

/**
 * @author Lance
 * @date 2018/4/21
 */

public class EngineKey implements Key {

    private final Object model;
    private final int width;
    private final int height;

    public EngineKey(Object model, int width, int height) {
        this.model = model;
        this.width = width;
        this.height = height;
    }

    @Override
    public void updateDiskCacheKey(MessageDigest messageDigest) {
        messageDigest.update(getKeyBytes());
    }

    @Override
    public byte[] getKeyBytes() {
        return toString().getBytes();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EngineKey engineKey = (EngineKey) o;

        if (width != engineKey.width) return false;
        if (height != engineKey.height) return false;
        return model != null ? model.equals(engineKey.model) : engineKey.model == null;
    }

    @Override
    public int hashCode() {
        int result = model != null ? model.hashCode() : 0;
        result = 31 * result + width;
        result = 31 * result + height;
        return result;
    }

    @Override
    public String toString() {
        return "EngineKey{" +
                "model=" + model +
                ", width=" + width +
                ", height=" + height +
                '}';
    }
}
