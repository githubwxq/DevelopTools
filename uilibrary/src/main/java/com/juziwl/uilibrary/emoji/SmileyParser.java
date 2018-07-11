package com.juziwl.uilibrary.emoji;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LevelListDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.util.ArrayMap;
import android.text.Html;
import android.text.TextUtils;
import android.widget.TextView;

//import com.juziwl.commonlibrary.config.Global;
//import com.juziwl.commonlibrary.config.GlobalContent;
//import com.juziwl.commonlibrary.utils.DisplayUtils;
//import com.juziwl.commonlibrary.utils.LoadingImgUtil;
import com.juziwl.uilibrary.R;
//import com.wxq.commonlibrary.glide.LoadingImgUtil;
//import com.wxq.commonlibrary.util.ScreenUtils;
//import com.wxq.commonlibrary.util.Utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * @author Army
 * @version V_5.0.0
 * @modify Neil
 * @date 2016年02月24日
 * @description 应用程序Activity的模板类
 */
public class SmileyParser {
    private String[] mSmileyTexts;
    private Pattern mPattern;
    private ArrayMap<String, Integer> mSmileyToRes;
    public static final int[] DEFAULT_SMILEY_RES_IDS = {
            R.mipmap.f001,
            R.mipmap.f002,
            R.mipmap.f003,
            R.mipmap.f004,
            R.mipmap.f005,
            R.mipmap.f102,
            R.mipmap.f027,
            R.mipmap.f014,
            R.mipmap.f024,
            R.mipmap.f026,
            R.mipmap.f013,
            R.mipmap.f018,
            R.mipmap.f032,
            R.mipmap.f103,
            R.mipmap.f016,
            R.mipmap.f037,
            R.mipmap.f104,
            R.mipmap.f038,
            R.mipmap.f021,
            R.mipmap.f105,
            R.mipmap.f039,
            R.mipmap.f031,
            R.mipmap.f033,
            R.mipmap.f022,
            R.mipmap.f006,
            R.mipmap.f007,
            R.mipmap.f106,
            R.mipmap.f107,
            R.mipmap.f034,
            R.mipmap.f043,
            R.mipmap.f011,
            R.mipmap.f046,
            R.mipmap.f030,
            R.mipmap.f084,
            R.mipmap.f083,
            R.mipmap.f108,
            R.mipmap.f036,
            R.mipmap.f012,
            R.mipmap.f109,
            R.mipmap.f110,
            R.mipmap.f023,
            R.mipmap.f111,
            R.mipmap.f112,
            R.mipmap.f044,
            R.mipmap.f035,
            R.mipmap.f041,
            R.mipmap.f010,
            R.mipmap.f008,
            R.mipmap.f042,
            R.mipmap.f025,
            R.mipmap.f017,
            R.mipmap.f015,
            R.mipmap.f020,
            R.mipmap.f029,
            R.mipmap.f090,
            R.mipmap.f082,
            R.mipmap.f085,
            R.mipmap.f092,
            R.mipmap.f081,
            R.mipmap.f088,
            R.mipmap.f093,
            R.mipmap.f096,
            R.mipmap.f087,
            R.mipmap.f086,
            R.mipmap.f071,
            R.mipmap.f101,
            R.mipmap.f089,
            R.mipmap.f069,
            R.mipmap.f070,
            R.mipmap.f113,
            R.mipmap.f068,
            R.mipmap.f062,
            R.mipmap.f065,
            R.mipmap.f066,
            R.mipmap.f067,
            R.mipmap.f114,
            R.mipmap.f115,
            R.mipmap.f064,
            R.mipmap.f116,
            R.mipmap.f117,
            R.mipmap.f118,
            R.mipmap.f119,
            R.mipmap.f120,
            R.mipmap.f121,
            R.mipmap.f122,
            R.mipmap.f123,
            R.mipmap.f077,
            R.mipmap.f076,
            R.mipmap.f047,
            R.mipmap.f048,
            R.mipmap.f097,
            R.mipmap.f058,
            R.mipmap.f098,
            R.mipmap.f050,
            R.mipmap.f091,
            R.mipmap.f080,
            R.mipmap.f095,
            R.mipmap.f074,
            R.mipmap.f061,
            R.mipmap.f049,
            R.mipmap.f059,
            R.mipmap.f060,
            R.mipmap.f052,
            R.mipmap.f053,
            R.mipmap.f051,
            R.mipmap.f073,
            R.mipmap.f075,
            R.mipmap.f057,
            R.mipmap.f078,
            R.mipmap.f079,
            R.mipmap.f094,
            R.mipmap.f099,
            R.mipmap.f072,
            R.mipmap.f100,
            R.mipmap.f056,
            R.mipmap.f055,
            R.mipmap.f054,
//            R.mipmap.f009,
//            R.mipmap.f019,
//            R.mipmap.f028,
//            R.mipmap.f045,
//            R.mipmap.f063,
    };

    private static class InstanceHolder {
        private static SmileyParser instance = new SmileyParser();
    }

    public  static Context mContext;
    public static SmileyParser getInstance(Context context) {
        mContext=context;
        return InstanceHolder.instance;
    }

    public SmileyParser() {
        mSmileyTexts = mContext.getResources().getStringArray(DEFAULT_SMILEY_TEXTS);
        mSmileyToRes = buildSmileyToRes();
        mPattern = buildPattern();
    }

    private static final int DEFAULT_SMILEY_TEXTS = R.array.default_smiley_texts;

    private ArrayMap<String, Integer> buildSmileyToRes() {
        if (DEFAULT_SMILEY_RES_IDS.length != mSmileyTexts.length) {
            //表情的数量需要和数组定义的长度一致！
            throw new IllegalStateException("Smiley resource ID/text mismatch");
        }

        ArrayMap<String, Integer> smileyToRes = new ArrayMap<>(mSmileyTexts.length);
        for (int i = 0; i < mSmileyTexts.length; i++) {
            smileyToRes.put(mSmileyTexts[i], DEFAULT_SMILEY_RES_IDS[i]);
        }
        return smileyToRes;
    }

    /**
     * 构建正则表达式
     *
     * @return
     */
    private Pattern buildPattern() {
        StringBuilder patternString = new StringBuilder(mSmileyTexts.length * 4);
        patternString.append('(');
        for (String s : mSmileyTexts) {
            patternString.append(Pattern.quote(s));
            patternString.append('|');
        }
        patternString.replace(patternString.length() - 1, patternString.length(), ")");
        return Pattern.compile(patternString.toString());
    }

    public CharSequence replace(CharSequence text, TextView textView) {
        if (TextUtils.isEmpty(text)) {
            return "";
        }
        //先用TextUtils.htmlEncode（）对<,>,&等这种符号进行转义
        String builder = TextUtils.htmlEncode(text.toString()).replaceAll(" ", "&nbsp;");
        Matcher matcher = mPattern.matcher(builder);
        while (matcher.find()) {
            String string = matcher.group();
            Integer resId = mSmileyToRes.get(string);
            if (resId != null) {
                if (builder.contains(string)) {
                    builder = builder.replaceAll(Pattern.quote(string), "<img src='" + resId + "'/>");
                }
            }
        }
        return Html.fromHtml(builder.replaceAll("[\\n]", "<br/>"), getImageGetterInstance(textView), null);
    }

    public CharSequence replace(String atName, CharSequence text, TextView textView) {
        if (TextUtils.isEmpty(text)) {
            return "";
        }
        //先用TextUtils.htmlEncode（）对<,>,&等这种符号进行转义
        String builder = TextUtils.htmlEncode(text.toString().trim());
        Matcher matcher = mPattern.matcher(builder);
        while (matcher.find()) {
            String string = matcher.group();
            Integer resId = mSmileyToRes.get(string);
            if (resId != null) {
                if (builder.contains(string)) {
                    builder = builder.replaceAll(Pattern.quote(string), "<img src='" + resId + "'/>");
                }
            }
        }
        return Html.fromHtml(atName + builder.replaceAll("[\\n]", "<br/>"), getImageGetterInstance(textView), null);
    }

    private Html.ImageGetter getImageGetterInstance(final TextView textView) {
        return source -> {
            int fontH = (int) (textView.getTextSize() * 1.2);
            int id = Integer.parseInt(source);
            Drawable d = ContextCompat.getDrawable(textView.getContext(), id);
            int width = (int) ((float) d.getIntrinsicWidth() / (float) d.getIntrinsicHeight()) * fontH;
            if (width == 0) {
                width = d.getIntrinsicWidth();
            }
            d.setBounds(0, 0, width, fontH);
            return d;
        };
    }

    public static Html.ImageGetter getHttpImageGetter(final TextView textView) {
        return source -> {
            LevelListDrawable drawable = new LevelListDrawable();
            if (TextUtils.isDigitsOnly(source)) {
                int id = Integer.parseInt(source);
                Drawable drawable2 = ContextCompat.getDrawable(textView.getContext(), id);
                drawable2.setBounds(0, 0, drawable2.getIntrinsicWidth(), drawable2.getIntrinsicHeight());
                return drawable2;
            } else {
                // TODO: 2018/7/12  不依赖具体的图片加载框架提供回调即可
                //图片框架加载图片 可以向外面暴露方法给外面实现

//                LoadingImgUtil.getCacheImageBitmap(source, null, null, new LoadingImgUtil.onLoadingImageListener() {
//                            @Override
//                            public void onLoadingComplete(Bitmap result) {
//                                if (result != null) {
//                                    Drawable bitmapDrawable = new BitmapDrawable(textView.getResources(), result);
//                                    drawable.addLevel(1, 1, bitmapDrawable);
//                                    int screenWidth = ScreenUtils.getScreenWidth();
//                                    double radio = 1;
//                                    if (screenWidth >= 1500) {
//                                        radio = 3.2;
//                                    } else if (screenWidth >= 1080) {
//                                        radio = 2.5;
//                                    } else if (screenWidth >= 720) {
//                                        radio = 1.8;
//                                    }
//                                    int width = textView.getWidth();
//                                    if (width == 0) {
//                                        Object tag = textView.getTag(R.id.common_id_width);
//                                        if (tag != null) {
//                                            width = (int) tag;
//                                        }
//                                    }
//                                    if (width > 0) {
//                                        double scaleWidth = result.getWidth() * radio;
//                                        if (scaleWidth > width) {
//                                            radio = width * 1.0 / result.getWidth();
//                                        }
//                                    }
//                                    drawable.setBounds(0, 0, (int) (result.getWidth() * radio), (int) (result.getHeight() * radio));
//                                    drawable.setLevel(1);
//                                    textView.setText(textView.getText());
//                                    textView.requestLayout();
//                                    textView.invalidateDrawable(drawable);
//                                }
//                            }
//
//                            @Override
//                            public void onLoadingFailed() {
//                            }
//                        }
//                );
            }
            return drawable;
        };
    }
}
