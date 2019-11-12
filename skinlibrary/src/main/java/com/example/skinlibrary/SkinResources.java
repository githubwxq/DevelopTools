package com.example.skinlibrary;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;

class SkinResources {
    private static SkinResources instance;

    private Resources mSkinResources;

    private String mSkinPkgName;
    private boolean isDefaultSkin = true;

    private Resources mAppResources;

    private SkinResources(Context context) {
        mAppResources = context.getResources();
    }
    public static void init(Context context) {
        if (instance == null) {
            synchronized (SkinResources.class) {
                if (instance == null) {
                    instance = new SkinResources(context);
                }
            }
        }
    }


    public static SkinResources getInstance() {
        return instance;
    }


    /**
     * 还原重置
     */
    public void reset() {
        mSkinResources = null;
        mSkinPkgName = "";
        isDefaultSkin = true;
    }


    /**
     * 设置外部资源以及外部包名
     * @param resources
     * @param pkgName
     */
    public void applySkin(Resources resources, String pkgName) {
        mSkinResources = resources;
        mSkinPkgName = pkgName;
        //是否使用默认皮肤
        isDefaultSkin = TextUtils.isEmpty(pkgName) || resources == null;
    }

   public int getIdentifier(int  resId){
       if (isDefaultSkin) {
           return resId;
       }
       //在皮肤包中不一定就是 当前程序的 id
       //获取对应id 在当前的名称 colorPrimary
       //R.drawable.ic_launcher
       //在皮肤包中不一定就是 当前程序的 id
       //获取对应id 在当前的名称 colorPrimary
       //R.drawable.ic_launcher
       //ic_launcher
       String resName=mAppResources.getResourceEntryName(resId);

       String resType = mAppResources.getResourceTypeName(resId);//drawable

       //最终对应的皮肤包里面的id   mSkinPkgName 皮肤包的包名
       int skinId = mSkinResources.getIdentifier(resName, resType, mSkinPkgName);
       return skinId;

   }



    public int getColor(int resId) {
        if (isDefaultSkin) {
            // 原项目上下文 getColor(R.color.....)
            return mAppResources.getColor(resId);
        }
        int skinId = getIdentifier(resId);
        if (skinId == 0) {
            return mAppResources.getColor(resId);
        }
        //插件项目上下文 getColor(R.color.....)
        return mSkinResources.getColor(skinId);
    }

    public ColorStateList getColorStateList(int resId) {
        if (isDefaultSkin) {
            return mAppResources.getColorStateList(resId);
        }
        int skinId = getIdentifier(resId);
        if (skinId == 0) {
            return mAppResources.getColorStateList(resId);
        }
        return mSkinResources.getColorStateList(skinId);
    }

    /**
     * resId 转换为对应的drawable 对象 更具不同的资源文件
     * @param resId
     * @return
     */
    public Drawable getDrawable(int resId) {
        //如果有皮肤  isDefaultSkin false 没有就是true
        if (isDefaultSkin) {
            return mAppResources.getDrawable(resId);
        }
        int skinId = getIdentifier(resId);
        if (skinId == 0) {
            return mAppResources.getDrawable(resId);
        }
        return mSkinResources.getDrawable(skinId);
    }


    /**
     * 可能是Color 也可能是drawable
     *
     * @return
     */
    public Object getBackground(int resId) {
        String resourceTypeName = mAppResources.getResourceTypeName(resId);

        if (resourceTypeName.equals("color")) {
            return getColor(resId);
        } else {
            // drawable
            return getDrawable(resId);
        }
    }

    public String getString(int resId) {
        try {
            if (isDefaultSkin) {
                return mAppResources.getString(resId);
            }
            int skinId = getIdentifier(resId);
            if (skinId == 0) {
                return mAppResources.getString(skinId);
            }
            return mSkinResources.getString(skinId);
        } catch (Resources.NotFoundException e) {

        }
        return null;
    }

    /**
     * 获得字体
     * @param resId
     * @return
     */
    public Typeface getTypeface(int resId) {
        String skinTypefacePath = getString(resId);
        if (TextUtils.isEmpty(skinTypefacePath)) {
            return Typeface.DEFAULT;
        }
        try {
            Typeface typeface;
            if (isDefaultSkin) {
                typeface = Typeface.createFromAsset(mAppResources.getAssets(), skinTypefacePath);
                return typeface;

            }
            typeface = Typeface.createFromAsset(mSkinResources.getAssets(), skinTypefacePath);
            return typeface;
        } catch (RuntimeException e) {
        }
        return Typeface.DEFAULT;
    }





}
