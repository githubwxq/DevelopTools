package com.juziwl.uilibrary.vlayout;

import android.content.Context;

import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.juziwl.uilibrary.utils.ConvertUtils;

public class VLayoutManagerHelpUtils {

 public  static LinearLayoutHelper linearLayoutHelper(Context context){
     LinearLayoutHelper linearLayoutHelper=   new LinearLayoutHelper(ConvertUtils.dp2px(15,context));
//     linearLayoutHelper.setMarginLeft(DensityUtil.dip2px(mContext, 20));
//     linearLayoutHelper.setMarginRight(DensityUtil.dip2px(mContext, 20));
//     linearLayoutHelper.setMarginTop(DensityUtil.dip2px(mContext, 20));
//     //设置间隔高度
//     linearLayoutHelper.setDividerHeight(5);
//     //设置布局底部与下个布局的间隔
//     linearLayoutHelper.setMarginBottom(20);
     return linearLayoutHelper;

 }

//    public  static str get(Context context){






}
