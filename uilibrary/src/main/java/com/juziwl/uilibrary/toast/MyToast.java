package com.juziwl.uilibrary.toast;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.juziwl.uilibrary.R;

public class MyToast {
    private static TextView toastText;
    private static ImageView toastImage;
    private static LinearLayout linearLayout;

    /**
     * 这个是自定义Toast的函数
     * 可以自定义左边的图片，右边的文字，以及背景Style
     * @param context
     * @param text
     * @param image
     * @param toastStyle
     * @return
     */
    public static Toast makeMyToast(Context context, String text, int image, int toastStyle, int duration){
        //加载Toast布局
        View toastRoot = LayoutInflater.from(context).inflate(R.layout.toast_item, null);
        //初始化布局控件
        toastText = (TextView) toastRoot.findViewById(R.id.toast_text);
        toastImage = (ImageView) toastRoot.findViewById(R.id.toast_image);
        linearLayout = (LinearLayout) toastRoot.findViewById(R.id.toast_layout);

        //为控件设置属性
        toastText.setText(text);
        toastImage.setImageResource(image);

        //这里若是没有传入图片，则将其隐藏
        if(image==0){
            toastImage.setVisibility(View.GONE);
        }
        //若是没有传入要显示的文字，则将其填充空格（为了好看一点）
        if(text==null){
            toastText.setText("                ");
        }
        //如果传进来了toastStyle，那么就设置他
        if(toastStyle != 0){
            Drawable style = context.getDrawable(toastStyle);
            linearLayout.setBackground(style);
        }

        //Toast的初始化
        Toast toast = new Toast(context);
        toast.setDuration(duration);
        toast.setView(toastRoot);

        return toast;
    }

    //以下分别是内置的错误，完成，点赞，保存，删除的Toast 自己换上图片就行
//    public static Toast errorToast(Context context,String text,int duration){
//        return MyToast.makeMyToast(context,text,R.drawable.error_icon,R.drawable.error_style,duration);
//    }
//
//    public static Toast finishToast(Context context,String text,int duration){
//        return MyToast.makeMyToast(context,text,R.drawable.finish_icon,R.drawable.finish_style,duration);
//    }
//
//    public static Toast appreciateToast(Context context,String text,int duration){
//        return MyToast.makeMyToast(context,text,R.drawable.appreciate_icon,R.drawable.appreciate_style,duration);
//    }
//
//    public static Toast savedToast(Context context,String text,int duration){
//        return MyToast.makeMyToast(context,text,R.drawable.saved_icon,R.drawable.saved_style,duration);
//    }
//
//    public static Toast deleteToast(Context context,String text,int duration){
//        return MyToast.makeMyToast(context,text,R.drawable.delete_icon,R.drawable.delete_style,duration);
//    }

    public static Toast NormalToast(Context context,String text,int duration){
        return Toast.makeText(context,text,duration);
    }
}


//toastutils 也可以showCustomShort(布局id);