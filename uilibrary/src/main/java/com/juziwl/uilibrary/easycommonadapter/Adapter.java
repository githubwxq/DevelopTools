package com.juziwl.uilibrary.easycommonadapter;

import android.support.annotation.NonNull;

/**
 * 项目名称: CommonAdapter
 * 包 名 称: com.classic.adapter
 * @author null
 * @modify Neil
 * 类 描 述: Adapter全局配置
 * 创 建 人: 续写经典
 * 创建时间: 2016/11/27 17:54.
 */
@SuppressWarnings({ "WeakerAccess", "unused" }) public final class Adapter {
    static volatile Adapter singleton = null;

    private ImageLoad mImageLoadImpl;

    private Adapter(Builder builder) {
        mImageLoadImpl = builder.mImageLoadImpl;
    }

    public ImageLoad getImageLoad() {
        return mImageLoadImpl;
    }

    public static Adapter config(@NonNull Builder builder) {
        if (singleton == null) {
            synchronized (Adapter.class) {
                if (singleton == null) {
                    singleton = builder.build();
                }
            }
        }
        return singleton;
    }

    public static final class Builder {
        private ImageLoad mImageLoadImpl;

        public Builder() { }

        public Builder setImageLoad(ImageLoad imageLoad) {
            mImageLoadImpl = imageLoad;
            return this;
        }

        public Adapter build() {
            return new Adapter(this);
        }
    }
}
