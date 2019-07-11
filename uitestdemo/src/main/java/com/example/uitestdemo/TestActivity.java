//package com.example.uitestdemo;
//
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.graphics.drawable.BitmapDrawable;
//import android.graphics.drawable.Drawable;
//import android.graphics.drawable.LevelListDrawable;
//import android.support.v4.content.ContextCompat;
//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
//import android.text.Editable;
//import android.text.Html;
//import android.text.SpannableStringBuilder;
//import android.text.Spanned;
//import android.text.TextUtils;
//import android.util.Log;
//import android.view.View;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.example.inject.compile.InjectView;
//import com.example.inject.runtime.InjectUtils;
//import com.example.inject.runtime.OnClick;
//import com.example.inject.runtime.OnLongClick;
//import com.example.inject.runtime.ViewInject;
//import com.example.inject_annotion.BindView;
//import com.juziwl.uilibrary.textview.stytle.DottedUnderlineSpan;
//import com.luck.picture.lib.utils.DisplayUtils;
//import com.wxq.commonlibrary.constant.GlobalContent;
//import com.wxq.commonlibrary.datacenter.AllDataCenterManager;
//import com.wxq.commonlibrary.glide.LoadingImgUtil;
//import com.wxq.commonlibrary.model.User;
//import com.wxq.commonlibrary.rxjavaframwork.Func1;
//import com.wxq.commonlibrary.rxjavaframwork.NewObserver;
//import com.wxq.commonlibrary.rxjavaframwork.Observable;
//import com.wxq.commonlibrary.rxjavaframwork.OnSubscrible;
//import com.wxq.commonlibrary.util.ToastUtils;
//
//import org.xml.sax.XMLReader;
//
//public class TestActivity extends AppCompatActivity {
//    private static final String TAG = "wxq";
//    @BindView(R.id.tv)
//    public TextView textView;
//
////    @OnClick({R.id.tv})
//    public void clickTv(View view) {
//        ToastUtils.showShort("clickTv 测试成功");
//    }
//
////    @OnClick({R.id.tv2})
//    public void clickTv2(View view) {
//        ToastUtils.showShort("clickTv2 测试成功");
//    }
//
////    @OnLongClick({R.id.tv2, R.id.tv})
//    public boolean clickLong(View view) {
//        ToastUtils.showShort("长按测试 测试成功");
//        return true;
//    }
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_test);
//        InjectView.bind(this);           // 动态文件生成
////        InjectUtils.inject(this);        //反射
//
////        for (int i = 0; i < 5; i++) {
////            AllDataCenterManager.getInstance().getDaoSession("wxq.db").insert(new User("wxq"));
////        }
//
//
////        System.out.print("size2"+size2);
//
//
////        String str = "1+2=<edit>2+3=<age>2+3=<name>" +
////                "<img class=\"wscnph\" src=\"http://test.img.juziwl.cn/exue/20181113/f5e7836f41cb4348aa236d2af3d62580.jpg\" data-mce-src=\"http://test.img.juziwl.cn/exue/20181113/f5e7836f41cb4348aa236d2af3d62580.jpg\">";
////        SpannableStringBuilder builder = (SpannableStringBuilder) Html.fromHtml(str, new Html.ImageGetter() {
////            @Override
////            public Drawable getDrawable(String source) {
////                LevelListDrawable drawable = new LevelListDrawable();
////                if (TextUtils.isDigitsOnly(source)) {
////                    int id = Integer.parseInt(source);
////                    Drawable drawable2 = ContextCompat.getDrawable(textView.getContext(), id);
////                    drawable2.setBounds(0, 0, drawable2.getIntrinsicWidth(), drawable2.getIntrinsicHeight());
////                    return drawable2;
////                } else {
////                    LoadingImgUtil.getCacheImageBitmap(source, null, null, new LoadingImgUtil.onLoadingImageListener() {
////                                @Override
////                                public void onLoadingComplete(Bitmap result) {
////                                    if (result != null) {
////                                        Drawable bitmapDrawable = new BitmapDrawable(textView.getResources(), result);
////                                        drawable.addLevel(1, 1, bitmapDrawable);
////                                        int screenWidth = DisplayUtils.getScreenWidth(TestActivity.this);
////                                        double radio = 1;
////                                        if (screenWidth >= GlobalContent.P1500) {
////                                            radio = 3.2;
////                                        } else if (screenWidth >= GlobalContent.P1080) {
////                                            radio = 2.5;
////                                        } else if (screenWidth >= GlobalContent.P720) {
////                                            radio = 1.8;
////                                        }
////                                        int width;
////                                        Object tag = textView.getTag(com.juziwl.uilibrary.R.id.common_id_width);
////                                        if (tag != null) {
////                                            width = (int) tag;
////                                        } else {
////                                            width = textView.getWidth();
////                                        }
////                                        double scaleWidth = 0d;
////                                        if (width > 0) {
////                                            scaleWidth = result.getWidth() * radio;
////                                            if (scaleWidth > width) {
////                                                radio = width * 1.0 / result.getWidth();
////                                            }
////                                        }
////                                        drawable.setBounds(0, 0, (int) (result.getWidth() * radio), (int) (result.getHeight() * radio));
////
////                                        drawable.setLevel(1);
////                                        textView.setText(textView.getText());
//////                                    textView.measure(0,0);
////                                        textView.requestLayout();
////                                        textView.invalidateDrawable(drawable);
////                                    }
////                                }
////
////                                @Override
////                                public void onLoadingFailed() {
////                                }
////                            }
////                    );
////                }
////                return drawable;
////            }
////        }, new Html.TagHandler() {
////            @Override
////            public void handleTag(boolean opening, String tag, Editable output, XMLReader xmlReader) {
////                if (tag.equalsIgnoreCase("edit") && opening) {
////                    Log.e("wxq", "++++++++++++++++++++++" + tag + output.length());
////                    output.setSpan( new DottedUnderlineSpan(R.color.green_800,"100"),output.length()-1,output.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
////                }
////
////                if (tag.equalsIgnoreCase("age") && opening) {
////                    output.setSpan( new DottedUnderlineSpan(R.color.green_800,"100"),output.length()-1,output.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
////                }
////
////                if (tag.equalsIgnoreCase("name") && opening) {
////                    Log.e("wxq", "++++++++++++++++++++++" + tag + output.length());
////                }
////
////
////            }
////        });
//////        textView.setText(str);
////        textView.setText(builder);
////
//////        String[] split = str.split("[space]");
//////        for (String s : split) {
//////            Log.e("wxq","++++++++++++++++++++++"+s);
//////        }
////
////                Observable.create(new OnSubscrible<String>() {
////            @Override
////            public void call(NewObserver<? super String> subscrible) {
////                Log.i(TAG,"1");//  1
////                subscrible.onNext("http://www.baidu.com");
////                Log.i(TAG,"5");//   调用  1  没调用  2
////                Log.i(TAG,Thread.currentThread().getName());
////            }
////            }).map(new Func1<String,Integer>() {
////            //具体的转换类型角色
////            @Override
////            public Integer call(String s) {
////                Log.i(TAG,"2");
////                Log.i(TAG,s);
////                Log.i(TAG,Thread.currentThread().getName());
////                return 110;
////            }
////            }).subscribleMain().subscrible(new NewObserver<Integer>() {
////            @Override
////            public void onNext(Integer bitmap) {
////                ToastUtils.showShort("currentInt"+bitmap);
////            }
////        });
//
//    }
//
//    private class TestThread extends Thread {
//
//        @Override
//        public void run() {
//            super.run();
//
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//
//
//        }
//    }
//}
