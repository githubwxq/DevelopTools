package com.example.trackpoint.aspect;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.example.trackpoint.AopUtil;
import com.example.trackpoint.annotation.NeedLogin;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * author:wxq
 * email:805380422@qq.com
 * time:2019/04/03
 * desc:需要登入的切面
 * version:1.0
 */
@Aspect
public class NeedLoginAspect {

    @Pointcut("execution(" +//执行语句
            "@com.example.trackpoint.annotation.NeedLogin" +//注解筛选
            " * " + //类路径,*为任意路径
            "*" +   //方法名,*为任意方法名
            "(..)" +//方法参数,'..'为任意个任意类型参数
            ")" +
            " && " +//并集
            "@annotation(needLogin)"//注解筛选,这里主要用于下面方法的'NeedLogin'参数获取
    )
    public void pointcutNeedLogin(NeedLogin needLogin) {

    }


    @Around("pointcutNeedLogin(needLogin)")
    public void aroundNeedLogin(ProceedingJoinPoint joinPoint,final NeedLogin needLogin) throws Throwable{
        if (AopUtil.getInstance().isLogin) {
            //原先的方法执行
            joinPoint.proceed();
        }else {
            final Context context=AopUtil.getInstance().getContext();
            switch (needLogin.tipeType()){
                case NeedLogin.SHOW_TOAST:
                    Toast.makeText(context,needLogin.tipToast(),Toast.LENGTH_LONG).show();
                    break;
                case NeedLogin.SHOW_DIALOG:
                    if (context instanceof Activity) {
                        showConfirmDialog(needLogin, context);
                    }else {
                        naveToLoginActivity(needLogin, context);
                    }
                    break;
                //无响应类型
                case NeedLogin.NO_RESPONSE:
                default:
                    break;
            }
        }
    }

    private void naveToLoginActivity(NeedLogin needLogin, Context context) {
        Toast.makeText(context, needLogin.tipToast(), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(context, needLogin.loginActivity());
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    private void showConfirmDialog(NeedLogin needLogin, Context context) {
        new AlertDialog.Builder(context)
                .setTitle("登录提示")
                .setMessage(needLogin.tipDialog())
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setPositiveButton("前往登录", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        Intent intent = new Intent(context, needLogin.loginActivity());
                        context.startActivity(intent);
                    }
                }).show();
    }
}
